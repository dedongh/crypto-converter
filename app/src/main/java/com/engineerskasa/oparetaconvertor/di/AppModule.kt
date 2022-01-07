package com.engineerskasa.oparetaconvertor.di

import android.content.Context
import com.engineerskasa.oparetaconvertor.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideUtils(@ApplicationContext application: Context): Utils = Utils(application)
}