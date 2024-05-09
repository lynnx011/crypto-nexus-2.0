package com.example.cryptocurrency.data.remote.api_services.api_service
import com.example.cryptocurrency.data.remote.dtos.NftsDto
import retrofit2.http.GET

interface GeckoApi {

    @GET("nfts/list")
    suspend fun getNft() : NftsDto
}