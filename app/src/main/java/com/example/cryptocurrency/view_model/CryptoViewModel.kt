package com.example.cryptocurrency.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.model2.Details
import com.example.cryptocurrency.repositories.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.cryptocurrency.repositories.PortfolioRepository
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository, private val portfolio: PortfolioRepository) : ViewModel() {

    val topCryptosLiveData = MutableLiveData<List<CryptoDetails>>()
    val topGainersLosersLiveData = MutableLiveData<List<CryptoDetails>>()
    val marketLiveData = MutableLiveData<List<CryptoDetails>>()
    private val cryptoInfoLiveData = MutableLiveData<List<Details>>()
    val cryptoDetails = MutableLiveData<CryptoDetails?>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.localizedMessage?.let { Log.d("exception", it) }
    }
    private val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)


    fun getTopCryptos() = coroutineScope.launch {
        val response = repository.getTopCryptos()
        if (response.isSuccessful){
            withContext(Dispatchers.Main) {
                topCryptosLiveData.value = response.body()?.data
            }
        }
    }

    fun getTopGainersLosers() = coroutineScope.launch {
        val response = repository.getTopGainersLosers()
        if (response.isSuccessful){
            withContext(Dispatchers.Main) {
                topGainersLosersLiveData.value = response.body()?.data
            }
        }
    }

    fun getMarketData() = coroutineScope.launch {
        val response = repository.getTopGainersLosers()
        if (response.isSuccessful){
            withContext(Dispatchers.Main) {
                marketLiveData.value = response.body()?.data?.take(700)
                portfolio.insertCrypto(response.body()?.data!!.take(700))
            }
        }
    }

    fun getCryptoInfoData() = coroutineScope.launch {
        val response = repository.getCryptoInfo("ETH")
        if (response.isSuccessful){
            withContext(Dispatchers.Main) {
                cryptoInfoLiveData.value = listOf(response.body()!!.data)
            }
        }
    }

    fun onClear() {
        topCryptosLiveData.value = emptyList()
        topGainersLosersLiveData.value = emptyList()
        topGainersLosersLiveData.value = emptyList()
        marketLiveData.value = emptyList()
        cryptoInfoLiveData.value = emptyList()
        cryptoDetails.value = null
        coroutineScope.cancel()
    }
}