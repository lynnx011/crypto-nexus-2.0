package com.example.cryptocurrency.domain.repositories

import com.example.cryptocurrency.domain.model.BlockSpan
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface BlockSpanRepository {

    fun getBlockSpanNft(
        chain: String,
        exchange: String,
        pageSize: String
    ): Flow<ApiResponse<BlockSpan>>
}