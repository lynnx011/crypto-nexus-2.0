package com.example.cryptocurrency.domain.repositories

import com.example.cryptocurrency.domain.model.Conversion
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    fun getCryptos(start: Int, limit: Int): Flow<ApiResponse<List<CryptoDetails?>?>>

    fun getTopGainersAndLosers(start: Int, limit: Int): Flow<ApiResponse<List<CryptoDetails?>?>>

    fun getCryptoInfo(symbol: String): Flow<ApiResponse<CryptoDetails?>>

    fun getConversion(
        amount: Double,
        symbol: String,
        convert: String
    ): Flow<ApiResponse<Conversion?>>
}