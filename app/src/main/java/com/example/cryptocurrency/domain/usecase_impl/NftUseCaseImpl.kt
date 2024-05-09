package com.example.cryptocurrency.domain.usecase_impl

import com.example.cryptocurrency.domain.model.NftItem
import com.example.cryptocurrency.domain.repositories.NftRepository
import com.example.cryptocurrency.domain.usecases.NftUseCase
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NftUseCaseImpl @Inject constructor(private val nftRepository: NftRepository) : NftUseCase {

    override fun invoke(): Flow<ApiResponse<List<NftItem?>>> = nftRepository.getNft()

}