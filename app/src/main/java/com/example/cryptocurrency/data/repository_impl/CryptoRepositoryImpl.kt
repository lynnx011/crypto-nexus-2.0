package com.example.cryptocurrency.data.repository_impl

import com.example.cryptocurrency.data.remote.api_services.api_service.CryptoApi
import com.example.cryptocurrency.data.mapper.toConversionDto
import com.example.cryptocurrency.data.mapper.toCrypto
import com.example.cryptocurrency.data.mapper.toCryptoDetails
import com.example.cryptocurrency.domain.model.Conversion
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.repositories.CryptoRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(private val cryptoApi: CryptoApi): CryptoRepository{

    override fun getCryptos(start: Int, limit: Int): Flow<ApiResponse<List<CryptoDetails?>?>> =
        flow {
            try {
                emit(ApiResponse.Loading())
                val cryptos = cryptoApi.getCryptos(start = start, limit = limit).toCrypto().data
                emit(ApiResponse.Success(data = cryptos))
            }catch (e: Exception){
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    override fun getTopGainersAndLosers(
        start: Int,
        limit: Int
    ): Flow<ApiResponse<List<CryptoDetails?>?>> =
        flow {
            try {
                emit(ApiResponse.Loading())
                val cryptos = cryptoApi.getCryptos(start = start, limit = limit).toCrypto().data
                emit(ApiResponse.Success(data = cryptos))
            }catch (e: Exception){
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    override fun getCryptoInfo(symbol: String): Flow<ApiResponse<CryptoDetails?>> =
        flow {
            try {
                emit(ApiResponse.Loading())
                val cryptoInfo = cryptoApi.getCryptoInfo(symbol).toCryptoDetails()
                emit(ApiResponse.Success(data = cryptoInfo))
            }catch (e: Exception){
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }

    override fun getConversion(
        amount: Double,
        symbol: String,
        convert: String
    ): Flow<ApiResponse<Conversion?>> =
        flow {
            try {
                emit(ApiResponse.Loading())
                val cryptoConversion = cryptoApi.getConversion(amount = amount, symbol = symbol, convert = convert).toConversionDto()
                emit(ApiResponse.Success(data = cryptoConversion))
            }catch (e: Exception){
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }
}