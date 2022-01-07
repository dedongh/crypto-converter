package com.engineerskasa.oparetaconvertor.data.remote.service

import com.engineerskasa.oparetaconvertor.model.Currency
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("latest")
    fun getAllListingsFromCoinMarket(
        @Query("start") start: String,
        @Query("limit") limit: String,
        @Query("convert") convert: String
    ): Single<Response<Currency>>
}