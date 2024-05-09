package com.example.cryptocurrency.data.remote.api_services.api_service

import com.example.cryptocurrency.data.remote.dtos.NftsNewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoNewsApi {

    @GET("everything")
    suspend fun getCryptoNews(
        @Query("q") q: String,
    ) : NftsNewsDto
}