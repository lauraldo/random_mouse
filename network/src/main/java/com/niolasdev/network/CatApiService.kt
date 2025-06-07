package com.niolasdev.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("images/search")
    suspend fun searchCats(
        @Query("size") size: String = "med",
        @Query("mime_types") mimeTypes: String = "jpg",
        @Query("format") format: String = "json",
        @Query("has_breeds") hasBreeds: Boolean = true,
        @Query("order") order: String = "RANDOM",
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 10
    ): Result<List<CatDto>>
}