package com.example.cryptocurrency.repositories

import androidx.lifecycle.LiveData
import com.example.cryptocurrency.api_service.CryptoApi
import com.example.cryptocurrency.model.CryptoModel
import com.example.cryptocurrency.model.model2.CryptoInfoModel
import com.example.cryptocurrency.utils.ApiResponse
import retrofit2.Response
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val cryptoApi: CryptoApi,
) {

    suspend fun getTopCryptos(): Response<CryptoModel> {
        return cryptoApi.getCryptos(1, 100)
    }

    suspend fun getTopGainersLosers(): Response<CryptoModel> {
        return cryptoApi.getTopGainersLosers(1, 700)
    }

    suspend fun getCryptoInfo(symbol: String): Response<CryptoInfoModel> {
        return cryptoApi.getCryptoInfo(symbol)
    }
}