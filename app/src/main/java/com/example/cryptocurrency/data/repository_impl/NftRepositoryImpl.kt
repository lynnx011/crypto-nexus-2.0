package com.example.cryptocurrency.data.repository_impl

import com.example.cryptocurrency.data.mapper.toNftItem
import com.example.cryptocurrency.data.remote.api_services.api_service.GeckoApi
import com.example.cryptocurrency.domain.model.NftItem
import com.example.cryptocurrency.domain.repositories.NftRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NftRepositoryImpl @Inject constructor(private val nftApi: GeckoApi): NftRepository{

    override fun getNft(): Flow<ApiResponse<List<NftItem?>>> =
        flow {
            try {
                emit(ApiResponse.Loading())
                val nft = nftApi.getNft().map { it?.toNftItem() }
                emit(ApiResponse.Success(data = nft))
            }catch (e: Exception){
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

}