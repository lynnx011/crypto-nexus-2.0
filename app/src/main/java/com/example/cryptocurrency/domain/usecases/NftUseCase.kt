package com.example.cryptocurrency.domain.usecases

import com.example.cryptocurrency.domain.model.NftItem
import com.example.cryptocurrency.domain.repositories.NftRepository
import com.example.cryptocurrency.domain.usecase_impl.NftUseCaseImpl
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NftUseCase {

    operator fun invoke(): Flow<ApiResponse<List<NftItem?>>>

}