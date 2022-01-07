package com.engineerskasa.oparetaconvertor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engineerskasa.oparetaconvertor.data.repository.CurrencyRepository
import com.engineerskasa.oparetaconvertor.data.repository.OfflineCurrencyRepository
import com.engineerskasa.oparetaconvertor.model.LastSync
import com.engineerskasa.oparetaconvertor.model.OfflineCurrency
import com.engineerskasa.oparetaconvertor.util.getTimeStamp
import com.engineerskasa.oparetaconvertor.util.getTimeToSync
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val offlineCurrencyRepository: OfflineCurrencyRepository
) : ViewModel() {
    private val _userErrors by lazy { MutableLiveData<Throwable>() }
    val userErrors: LiveData<Throwable>
        get() = _userErrors
    private val _userLoader by lazy { MutableLiveData<Boolean>() }
    val userLoader: LiveData<Boolean>
        get() = _userLoader
    private val _responses by lazy { MutableLiveData<String>() }
    val responses: LiveData<String>
        get() = _responses

    private val _currencies by lazy { MutableLiveData<List<OfflineCurrency>>() }
    val currencies: LiveData<List<OfflineCurrency>>
        get() = _currencies

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        getCurrencyDataSource("USD")
    }

    fun getCurrencyDataSource(currency: String) {
        viewModelScope.launch {
            val count = offlineCurrencyRepository.getCountOfCurrencies()
            Timber.e("COUNT ==> $count")
            if (count == 0) {
                getOnlineCurrencies(currency)
            } else {
                getOfflineCurrencies(currency)
            }
        }
    }

    fun getOfflineCurrencies(currency: String) {
        var hours = 0L
        var minutes = 0L
        viewModelScope.launch {
            val count = offlineCurrencyRepository.getCountOfSync()
            if (count != 0) {
                val syncedTime = offlineCurrencyRepository.getLastSync().lastUpdated
                val syncInterval = getTimeToSync(syncedTime!!, getTimeStamp())
                hours = syncInterval.first
                minutes = syncInterval.second
            }
            if (count == 0 || hours > 0 || minutes > 1) {
                getOnlineCurrencies(currency)
            } else {
                val currencies = offlineCurrencyRepository.fetchCurrenciesFromDB()
                _currencies.postValue(currencies)
            }
        }
    }

    fun getOnlineCurrencies(convert: String = "USD") {
        _userLoader.postValue(true)
        compositeDisposable.add(
            currencyRepository.getAllListingsFromCoinMarket(convert)
                .filter {
                    if (it.isSuccessful) {
                        true
                    } else {
                        throw HttpException(it)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _userLoader.postValue(false)
                    val list = ArrayList<OfflineCurrency>()
                    val currencies = it.body()?.data
                    for (currency in currencies!!) {
                        val price: Double = when (convert) {
                            "USD" -> currency.quote?.usd?.price!!
                            "EUR" -> currency.quote?.eur?.price!!
                            else -> currency.quote?.ugx?.price!!
                        }
                        val offlineCurrency = OfflineCurrency()
                        offlineCurrency.name = currency.name
                        offlineCurrency.symbol = currency.symbol
                        offlineCurrency.price = price
                        offlineCurrency.slug = currency.slug
                        offlineCurrency.lastUpdated = currency.lastUpdated
                        list.add(offlineCurrency)
                    }
                    _responses.postValue("Currencies updated")
                    viewModelScope.launch {
                        offlineCurrencyRepository.insertListOfCurrencies(list)
                        offlineCurrencyRepository.saveLastSync(LastSync(1, getTimeStamp()))
                        val allCurrencies = offlineCurrencyRepository.fetchCurrenciesFromDB()
                        _currencies.postValue(allCurrencies)
                    }

                }, {
                    _userLoader.postValue(false)
                    _userErrors.postValue(it)
                    Timber.e("ERROR ===> ${it.message}")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}