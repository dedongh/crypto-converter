package com.engineerskasa.oparetaconvertor.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.engineerskasa.oparetaconvertor.data.database.dao.CurrencyDao
import com.engineerskasa.oparetaconvertor.data.database.dao.LastSyncDao
import com.engineerskasa.oparetaconvertor.model.LastSync
import com.engineerskasa.oparetaconvertor.model.OfflineCurrency
import com.engineerskasa.oparetaconvertor.util.DATABASE_NAME

@Database(entities = [OfflineCurrency::class, LastSync::class], version = 1, exportSchema = false)
abstract class OparetaDB: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun lastSyncDao(): LastSyncDao
    companion object{
        @Volatile
        private var INSTANCE: OparetaDB? =null
        private var LOCK = Any()

        fun getInstance(application: Context) = INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
        }
        private fun buildDatabase(application: Context) = Room.databaseBuilder(application, OparetaDB::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}