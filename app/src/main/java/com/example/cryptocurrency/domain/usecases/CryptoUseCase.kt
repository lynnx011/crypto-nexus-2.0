package com.example.cryptocurrency.domain.usecases

import com.example.cryptocurrency.domain.model.Conversion
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.repositories.CryptoRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CryptoUseCase {

    operator fun invoke(start: Int, limit: Int): Flow<ApiResponse<List<CryptoDetails?>?>>

    //    operator fun invoke(start: Int, limit: Int): Flow<ApiResponse<List<CryptoDetails>>> =
//        repository.getTopGainersAndLosers(start, limit)
    operator fun invoke(symbol: String): Flow<ApiResponse<CryptoDetails?>>

    operator fun invoke(amount: Double, symbol: String, convert: String): Flow<ApiResponse<Conversion?>>
}