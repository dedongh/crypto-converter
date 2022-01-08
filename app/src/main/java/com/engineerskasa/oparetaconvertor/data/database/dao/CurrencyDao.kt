package com.engineerskasa.oparetaconvertor.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.engineerskasa.oparetaconvertor.model.OfflineCurrency

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrency(offlineCurrency: OfflineCurrency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveListOfCurrency(offlineCurrency: List<OfflineCurrency>)

    @Query("select * from currency where currency = :currency order by symbol asc")
    suspend fun getOfflineCurrencies(currency: String): List<OfflineCurrency>

    @Query("select count(*) from currency where currency = :currency")
    suspend fun getCountOfCurrencies(currency: String): Int

    @Query("delete from currency where currency = :currency")
    suspend fun deleteCurrencies(currency: String)
}