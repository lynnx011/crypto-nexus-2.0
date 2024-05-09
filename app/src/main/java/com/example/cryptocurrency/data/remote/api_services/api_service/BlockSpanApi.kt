package com.example.cryptocurrency.data.remote.api_services.api_service
import com.example.cryptocurrency.data.remote.dtos.BlockSpanDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockSpanApi {
    @GET("exchanges/collections")
    suspend fun getBlockSpanNft(
        @Query("chain") chain: String,
        @Query("exchange") exchange: String,
        @Query("page_size") pageSize: String
    ): BlockSpanDto

}