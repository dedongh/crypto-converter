package com.engineerskasa.oparetaconvertor.di

import com.engineerskasa.oparetaconvertor.BuildConfig
import com.engineerskasa.oparetaconvertor.util.API_KEY
import com.engineerskasa.oparetaconvertor.util.BASE_URL
import com.engineerskasa.oparetaconvertor.util.READ_TIMEOUT
import com.engineerskasa.oparetaconvertor.util.REQUEST_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun providesGsonParser(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun providesRetrofit(httpClient: OkHttpClient, parser: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(parser))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(httpClient)
        .build()

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)

        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("X-CMC_PRO_API_KEY", API_KEY)
            val request = requestBuilder.build()
            chain.proceed(request)
        })

        return httpClient.build()
    }
}