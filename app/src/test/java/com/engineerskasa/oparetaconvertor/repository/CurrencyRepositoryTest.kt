package com.engineerskasa.oparetaconvertor.repository

import com.engineerskasa.oparetaconvertor.data.repository.CurrencyRepository
import com.engineerskasa.oparetaconvertor.mocks.CurrencyServiceMock
import com.engineerskasa.oparetaconvertor.model.Currency
import junit.framework.TestCase
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryTest: TestCase() {

    @Mock
    lateinit var sut: CurrencyRepository


    @Before
    fun start() {
        val service: CurrencyServiceMock = CurrencyServiceMock()
        sut = CurrencyRepository(service)
    }

    @After
    fun end() {
    }

    @Test
    fun `Get all currencies`() {
        //given
        val mockResponse = sut.getAllListingsFromCoinMarket()
                              .blockingGet()

        //when currency = USD

        //then
        assertEquals("BTC", mockResponse.body()?.data?.get(0)?.symbol)
    }
}