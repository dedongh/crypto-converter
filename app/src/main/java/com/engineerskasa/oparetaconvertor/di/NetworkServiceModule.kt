package com.engineerskasa.oparetaconvertor.di

import com.engineerskasa.oparetaconvertor.BuildConfig
import com.engineerskasa.oparetaconvertor.data.remote.service.CurrencyService
import com.engineerskasa.oparetaconvertor.util.READ_TIMEOUT
import com.engineerskasa.oparetaconvertor.util.REQUEST_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkServiceModule {
    private fun <T> buildService(
        service: Class<T>,
        retrofit: Retrofit,
        okHttpClient: OkHttpClient
    ): T {
        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val client = okHttpClient.newBuilder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()

        val newRetrofit = retrofit.newBuilder().client(client).build()
        return newRetrofit.create(service)
    }

    @Singleton
    @Provides
    fun providesCurrencyService(
        retrofit : Retrofit,
        okHttpClient : OkHttpClient,
    ): CurrencyService = buildService(
        CurrencyService::class.java,
        retrofit,
        okHttpClient
    )
}