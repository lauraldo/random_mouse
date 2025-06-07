package com.niolasdev.network

import okhttp3.Interceptor
import okhttp3.Response

@Suppress("PrivatePropertyName")
class CatApiKeyInterceptor: Interceptor {

    private val X_ORIGIN_HEADER_KEY = "x-api-key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader(X_ORIGIN_HEADER_KEY, API_KEY)
        return chain.proceed(builder.build())
    }
}