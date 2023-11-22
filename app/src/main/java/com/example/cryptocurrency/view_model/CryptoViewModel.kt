package com.example.cryptocurrency.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.model2.Details
import com.example.cryptocurrency.model.model3.ConversionModel
import com.example.cryptocurrency.model.model5.Article
import com.example.cryptocurrency.model.model6.NftsModelItem
import com.example.cryptocurrency.model.transaction.TransactionModel
import com.example.cryptocurrency.repositories.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.cryptocurrency.model.model4.Result
import com.example.cryptocurrency.model.model4.Stats
import kotlinx.coroutines.*
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    val topCryptosLiveData = MutableLiveData<List<CryptoDetails>>()
    val topGainersLosersLiveData = MutableLiveData<List<CryptoDetails>>()
    val marketLiveData = MutableLiveData<List<CryptoDetails>>()
    private val cryptoInfoLiveData = MutableLiveData<List<Details>>()
    val conversionLiveData = MutableLiveData<ConversionModel?>()
    val convertAmount = MutableLiveData("")
    val convertResult = MutableLiveData("0.0")
    var isTransBtnValid = MutableLiveData(false)
    val totalHoldingValue = MutableLiveData("")
    val cryptoDetails = MutableLiveData<CryptoDetails?>()
    val nftResult = MutableLiveData<Result?>()
    val nftStats = MutableLiveData<Stats?>()
    val cryptoNewsLiveData = MutableLiveData<List<Article>>()
    private val nftsListLiveData = MutableLiveData<List<NftsModelItem>?>()
    val blockSpanNftLiveData = MutableLiveData<List<Result>>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.localizedMessage?.let { Log.d("exception", it) }
    }
    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)


    fun getTopCryptos() = coroutineScope.launch {
        val response = repository.getTopCryptos().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                topCryptosLiveData.value = response.data
            }
        }
    }

    fun getTopGainersLosers() = coroutineScope.launch {
        val response = repository.getTopGainersLosers().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                topGainersLosersLiveData.value = response.data
            }
        }
    }

    fun getMarketData() = coroutineScope.launch {
        val response = repository.getTopGainersLosers().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                marketLiveData.value = response.data.take(700)
                repository.insertCrypto(response.data.take(700))
            }
        }
    }

    fun getRoomCryptos() = repository.getRoomCrypto()

    fun getCryptoConversion(symbol: String) =
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            withContext(Dispatchers.Main) {
                val response = convertAmount.value?.let {
                    repository.getConversion(
                        it.toDouble(),
                        symbol,
                        "USD"
                    )
                }
                conversionLiveData.postValue(response)
                val price = response?.data?.map {
                    it.quote.USD.price
                }
                convertResult.value = price?.get(0)
            }
        }

    fun getCryptoInfoData() = coroutineScope.launch {
        val response = repository.getCryptoInfo("ETH").body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                cryptoInfoLiveData.value = listOf(response.data)
            }
        }
    }

    fun getCryptoNews() = coroutineScope.launch {
        try {
            val response =
                repository.getCryptoNews("nfts").body()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    cryptoNewsLiveData.value = response.articles
                }
            }
        } catch (e: HttpException) {
            Log.d("exception", e.toString())
        }
    }

    fun getNfts() = coroutineScope.launch {
        val response = repository.getNfts().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                nftsListLiveData.value = response
            }
        }
    }

    fun getBlockSpanNfts() = coroutineScope.launch {

        try {
            val response = repository.getBlockSpanNfts("eth-main", "opensea", "100").body()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    blockSpanNftLiveData.value = response.results
                    Log.d("span_exc", response.toString())
                }
            }
        } catch (e: Exception) {
            Log.d("span_exc", e.toString())
        }
    }

    fun transBtnValid() {
        isTransBtnValid.value =
            !convertAmount.value.isNullOrBlank() && convertResult.value?.toDouble() != 0.0
    }

    fun insertTransaction(model: TransactionModel) = viewModelScope.launch {
        repository.insertTransaction(model)
    }

    fun roomTransaction(): LiveData<List<TransactionModel>> = repository.getTransaction()

    fun deleteTransaction(model: TransactionModel) = repository.deleteTransaction(model)

    fun onClear() {
        topGainersLosersLiveData.value = emptyList()
        topGainersLosersLiveData.value = emptyList()
//        marketLiveData.value = emptyList()
        cryptoInfoLiveData.value = emptyList()
        cryptoNewsLiveData.value = emptyList()
        nftsListLiveData.value = emptyList()
        blockSpanNftLiveData.value = emptyList()
        conversionLiveData.value = null
        cryptoDetails.value = null
        nftResult.value = null
        nftStats.value = null
    }
}