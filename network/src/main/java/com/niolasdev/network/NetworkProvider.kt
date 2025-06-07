package com.niolasdev.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class NetworkProvider() {

    private val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
//            .connectionPool(connectionPool)
            .connectTimeout(TIMEOUT_DEFAULT_CONNECT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_DEFAULT_READ_IN_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_DEFAULT_WRITE_IN_SECONDS, TimeUnit.SECONDS)
        builder.build()
    }

    private val catApiService: CatApiService by lazy {
        CatApi(
            baseUrl = BuildConfig.CAT_API_URL,
            okHttpClient
        )
    }

    fun provideCatApi(): CatApiService = catApiService

    companion object {
        const val TIMEOUT_DEFAULT_CONNECT_IN_SECONDS = 20L
        const val TIMEOUT_DEFAULT_READ_IN_SECONDS = 10L
        const val TIMEOUT_DEFAULT_WRITE_IN_SECONDS = 10L
    }
}