package com.engineerskasa.oparetaconvertor.data.repository

import com.engineerskasa.oparetaconvertor.data.database.dao.CurrencyDao
import com.engineerskasa.oparetaconvertor.data.database.dao.LastSyncDao
import com.engineerskasa.oparetaconvertor.model.LastSync
import com.engineerskasa.oparetaconvertor.model.OfflineCurrency
import javax.inject.Inject

class OfflineCurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val lastSyncDao: LastSyncDao
){
    suspend fun insertCurrency(currency: OfflineCurrency) = currencyDao.saveCurrency(currency)

    suspend fun insertListOfCurrencies(currency: List<OfflineCurrency>) = currencyDao.saveListOfCurrency(currency)

    suspend fun getCountOfCurrencies(): Int = currencyDao.getCountOfCurrencies()

    suspend fun fetchCurrenciesFromDB(): List<OfflineCurrency> = currencyDao.getOfflineCurrencies()

    suspend fun saveLastSync(lastSync: LastSync) = lastSyncDao.saveLastSync(lastSync)

    suspend fun getLastSync(): LastSync = lastSyncDao.getLastSync()

    suspend fun getCountOfSync():Int = lastSyncDao.getCountOfSync()
}