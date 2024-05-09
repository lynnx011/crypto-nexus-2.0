package com.example.cryptocurrency.data.repository_impl

import com.example.cryptocurrency.data.remote.api_services.api_service.CryptoNewsApi
import com.example.cryptocurrency.data.mapper.toNftNews
import com.example.cryptocurrency.domain.model.NftArticle
import com.example.cryptocurrency.domain.repositories.CryptoNewsRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoNewsRepositoryImpl @Inject constructor(private val cryptoNewsApi: CryptoNewsApi): CryptoNewsRepository {

    override fun getCryptoNews(q: String): Flow<ApiResponse<List<NftArticle?>?>> =
        flow {
            try {
                emit(ApiResponse.Loading())
                val cryptoNews = cryptoNewsApi.getCryptoNews(q).toNftNews().articles
                emit(ApiResponse.Success(data = cryptoNews))
            }catch (e: Exception){
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)


}