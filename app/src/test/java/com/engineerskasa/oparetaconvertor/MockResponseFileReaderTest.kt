package com.engineerskasa.oparetaconvertor

import com.google.gson.Gson
import com.engineerskasa.oparetaconvertor.MockResponseFileReader
import junit.framework.Assert.assertEquals
import org.junit.Test

class MockResponseFileReaderTest {

    @Test
    fun readSimpleFile(){
        val reader = MockResponseFileReader("test_reader.json")
        val gson = Gson()
        assertEquals(reader.content, gson.toJson("success"))
    }
}