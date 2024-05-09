package com.example.cryptocurrency.data.remote.api_services.api_service

import com.example.cryptocurrency.data.remote.dtos.ConversionDto
import com.example.cryptocurrency.data.remote.dtos.CryptoDetailsDto
import com.example.cryptocurrency.data.remote.dtos.CryptoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptos(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): CryptoDto

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getTopGainersLosers(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): CryptoDto

    @GET("v1/cryptocurrency/info")
    suspend fun getCryptoInfo(
        @Query("symbol") symbol: String
    ): CryptoDetailsDto

    @GET("v2/tools/price-conversion")
    suspend fun getConversion(
        @Query("amount") amount: Double,
        @Query("symbol") symbol: String,
        @Query("convert") convert: String
    ): ConversionDto

}