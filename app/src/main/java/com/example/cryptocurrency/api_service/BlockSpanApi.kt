package com.example.cryptocurrency.api_service

import com.example.cryptocurrency.model.model4.BlockSpanModel
import com.example.cryptocurrency.utils.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockSpanApi {
    @GET("exchanges/collections")
    suspend fun getBlockSpanNft(
        @Query("chain") chain: String,
        @Query("exchange") exchange: String,
        @Query("page_size") pageSize: String
    ): Response<BlockSpanModel>

}