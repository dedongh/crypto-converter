package com.engineerskasa.oparetaconvertor.mocks

import com.engineerskasa.oparetaconvertor.MockResponseFileReader
import com.engineerskasa.oparetaconvertor.data.remote.service.CurrencyService
import com.engineerskasa.oparetaconvertor.model.Currency
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.core.SingleOnSubscribe
import retrofit2.Response
import timber.log.Timber

class CurrencyServiceMock: CurrencyService {
    override fun getAllListingsFromCoinMarket(
        start: String,
        limit: String,
        convert: String
    ): Single<Response<Currency>> {
        val gson = Gson()
        val tt = MockResponseFileReader("response.json").content
        val response = gson.fromJson(tt, Currency::class.java)
        val server = Response.success(response)
        Timber.e("RESSSSS")
        return Single.create(object: SingleOnSubscribe<Response<Currency>>{
            override fun subscribe(emitter: SingleEmitter<Response<Currency>>) {
                emitter.onSuccess(server)
            }
        } )
    }
}