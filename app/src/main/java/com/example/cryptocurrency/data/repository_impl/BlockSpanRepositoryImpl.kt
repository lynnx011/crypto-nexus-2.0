package com.example.cryptocurrency.data.repository_impl

import com.example.cryptocurrency.data.mapper.toBlockSpan
import com.example.cryptocurrency.data.remote.api_services.api_service.BlockSpanApi
import com.example.cryptocurrency.domain.model.BlockSpan
import com.example.cryptocurrency.domain.repositories.BlockSpanRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BlockSpanRepositoryImpl @Inject constructor(private val blockSpanApi: BlockSpanApi): BlockSpanRepository {

    override fun getBlockSpanNft(
        chain: String,
        exchange: String,
        pageSize: String
    ): Flow<ApiResponse<BlockSpan>> =
        flow {
            try {
                emit(ApiResponse.Loading())
                val blockSpanNft = blockSpanApi.getBlockSpanNft(chain = chain, exchange = exchange, pageSize = pageSize).toBlockSpan()
                emit(ApiResponse.Success(data = blockSpanNft))
            }catch (e: Exception){
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

}