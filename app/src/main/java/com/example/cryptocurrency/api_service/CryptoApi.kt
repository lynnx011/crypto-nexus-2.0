package com.example.cryptocurrency.api_service
import androidx.lifecycle.LiveData
import com.example.cryptocurrency.model.CryptoModel
import com.example.cryptocurrency.model.model2.CryptoInfoModel
import com.example.cryptocurrency.model.model3.ConversionModel
import com.example.cryptocurrency.utils.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptos(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): Response<CryptoModel>

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getTopGainersLosers(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): Response<CryptoModel>

    @GET("v1/cryptocurrency/info")
    suspend fun getCryptoInfo(
        @Query("symbol") symbol: String
    ): Response<CryptoInfoModel>

    @GET("v2/tools/price-conversion")
    suspend fun getConversion(
        @Query("amount") amount: Double,
        @Query("symbol") symbol: String,
        @Query("convert") convert: String
    ): ConversionModel

}