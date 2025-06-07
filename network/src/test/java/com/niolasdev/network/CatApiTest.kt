package com.niolasdev.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class CatApiTest {

    private var mockWebServer: MockWebServer? = null
    private var catsApi: CatApiService? = null

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val json = Json(Json.Default) {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
        catsApi = Retrofit.Builder()
            .baseUrl(mockWebServer?.url("/"))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
            .create(CatApiService::class.java)
    }

    fun readJsonFromFile(filePath: String): String {
        val file = File(filePath)
        return file.readText()
    }

    @After
    fun tearDown() {
        mockWebServer?.shutdown()
    }

    @Test
    fun testCatList() = runTest {
        val mockResponse = readJsonFromFile("src/test/resources/cat_mock.json")
        mockWebServer?.enqueue(
            MockResponse()
                .setBody(mockResponse)
                .setResponseCode(200)
        )
        val result = catsApi?.searchCats()
        assertNotNull(result)
        assertTrue(result?.isSuccess == true)
        assertTrue(result?.getOrThrow()?.size == 2)
        result?.getOrThrow()?.get(0)?.let { cat ->
            assertEquals("Hylo4Snaf", cat.id)
            assertEquals("https://cdn.thedogapi.com/images/Hylo4Snaf.jpeg", cat.url)
            assertEquals(1, cat.breeds?.size)
            cat.breeds?.get(0)?.let { breed ->
                assertEquals("235", breed.id)
                assertEquals("Spanish Water Dog", breed.name)
                assertEquals("5 - 10", breed.weight?.imperial)
                assertEquals("2 - 5", breed.weight?.metric)
                assertNull(breed.height)
                assertEquals("12 to 15 years", breed.life_span)
                assertNull(breed.temperament)
            }
        }
    }
}