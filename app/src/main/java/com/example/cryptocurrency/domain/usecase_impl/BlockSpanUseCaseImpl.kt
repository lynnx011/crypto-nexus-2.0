package com.example.cryptocurrency.domain.usecase_impl

import com.example.cryptocurrency.domain.model.BlockSpan
import com.example.cryptocurrency.domain.repositories.BlockSpanRepository
import com.example.cryptocurrency.domain.usecases.BlockSpanUseCase
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlockSpanUseCaseImpl @Inject constructor(private val blockSpanRepository: BlockSpanRepository) :
    BlockSpanUseCase {

    override fun invoke(
        chain: String,
        exchange: String,
        pageSize: String
    ): Flow<ApiResponse<BlockSpan>> =
        blockSpanRepository.getBlockSpanNft(chain = chain, exchange = exchange, pageSize = pageSize)

}