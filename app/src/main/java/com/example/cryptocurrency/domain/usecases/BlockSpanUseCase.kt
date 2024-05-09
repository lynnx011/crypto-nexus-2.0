package com.example.cryptocurrency.domain.usecases

import com.example.cryptocurrency.domain.model.BlockSpan
import com.example.cryptocurrency.domain.repositories.BlockSpanRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BlockSpanUseCase  {

    operator fun invoke(
        chain: String,
        exchange: String,
        pageSize: String
    ): Flow<ApiResponse<BlockSpan>>
}