package com.example.cryptocurrency.domain.usecases

import com.example.cryptocurrency.domain.model.NftArticle
import com.example.cryptocurrency.domain.repositories.CryptoNewsRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CryptoNewsUseCase {
    operator fun invoke(q: String): Flow<ApiResponse<List<NftArticle?>?>>
}