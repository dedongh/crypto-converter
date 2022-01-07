package com.engineerskasa.oparetaconvertor.data.repository

import com.engineerskasa.oparetaconvertor.data.remote.service.CurrencyService
import com.engineerskasa.oparetaconvertor.model.Currency
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyService: CurrencyService
) {
    fun getAllListingsFromCoinMarket(convert: String = "USD"): Single<Response<Currency>> = currencyService.getAllListingsFromCoinMarket("1","20", convert)
}