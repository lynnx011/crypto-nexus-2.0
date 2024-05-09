package com.example.cryptocurrency.domain.usecase_impl

import com.example.cryptocurrency.domain.model.NftArticle
import com.example.cryptocurrency.domain.repositories.CryptoNewsRepository
import com.example.cryptocurrency.domain.usecases.CryptoNewsUseCase
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoNewsUseCaseImpl @Inject constructor(private val cryptoNewsRepository: CryptoNewsRepository) :
    CryptoNewsUseCase {
    override fun invoke(q: String): Flow<ApiResponse<List<NftArticle?>?>> =
        cryptoNewsRepository.getCryptoNews(q = q)
}