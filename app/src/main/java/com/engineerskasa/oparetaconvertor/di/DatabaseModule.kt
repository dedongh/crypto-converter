package com.engineerskasa.oparetaconvertor.di

import android.content.Context
import com.engineerskasa.oparetaconvertor.data.database.OparetaDB
import com.engineerskasa.oparetaconvertor.data.database.dao.CurrencyDao
import com.engineerskasa.oparetaconvertor.data.database.dao.LastSyncDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideOparetaDB(@ApplicationContext application: Context): OparetaDB = OparetaDB.getInstance(application)


    @Singleton
    @Provides
    fun provideCurrencyDao(database: OparetaDB): CurrencyDao = database.currencyDao()

    @Singleton
    @Provides
    fun providesLastSyncDao(database: OparetaDB): LastSyncDao = database.lastSyncDao()
}