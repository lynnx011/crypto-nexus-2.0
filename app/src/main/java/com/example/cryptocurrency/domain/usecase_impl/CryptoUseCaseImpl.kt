package com.example.cryptocurrency.domain.usecase_impl

import com.example.cryptocurrency.domain.model.Conversion
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.repositories.CryptoRepository
import com.example.cryptocurrency.domain.usecases.CryptoUseCase
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoUseCaseImpl @Inject constructor(private val repository: CryptoRepository) :
    CryptoUseCase {
    override fun invoke(start: Int, limit: Int): Flow<ApiResponse<List<CryptoDetails?>?>> =
        repository.getCryptos(start = start, limit = limit)

    override fun invoke(symbol: String): Flow<ApiResponse<CryptoDetails?>> =
        repository.getCryptoInfo(symbol = symbol)

    override fun invoke(
        amount: Double,
        symbol: String,
        convert: String
    ): Flow<ApiResponse<Conversion?>> =
        repository.getConversion(amount = amount, symbol = symbol, convert = convert)

}