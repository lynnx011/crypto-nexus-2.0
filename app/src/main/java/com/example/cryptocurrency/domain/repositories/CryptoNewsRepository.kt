package com.example.cryptocurrency.domain.repositories

import com.example.cryptocurrency.domain.model.NftArticle
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface CryptoNewsRepository {

    fun getCryptoNews(q: String): Flow<ApiResponse<List<NftArticle?>?>>
}