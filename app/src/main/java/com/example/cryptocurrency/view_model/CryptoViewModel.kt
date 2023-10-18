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
    val cryptoDetails = MutableLiveData<CryptoDetails?>()
    val nftResult = MutableLiveData<Result?>()
    val nftStats = MutableLiveData<Stats?>()
    val totalLiveData = MutableLiveData<String>()
    val nullValueLiveData = MutableLiveData("0")
    val cryptoNewsLiveData = MutableLiveData<List<Article>>()
    private val nftsListLiveData = MutableLiveData<List<NftsModelItem>?>()
    val blockSpanNftLiveData = MutableLiveData<List<Result>>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.localizedMessage?.let { Log.d("exception", it) }
    }

    fun getTopCryptos() = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = repository.getTopCryptos().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                topCryptosLiveData.value = response.data
            }
        }
    }

    fun getTopGainersLosers() = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = repository.getTopGainersLosers().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                topGainersLosersLiveData.value = response.data
            }
        }
    }

    fun getMarketData() = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = repository.getTopGainersLosers().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                marketLiveData.value = response.data.take(700)
                repository.insertCrypto(response.data.take(700))
            }
        }
    }

    fun getRoomCryptos() = repository.getRoomCrypto()

    fun getCryptoConversion(amount: Int, symbol: String, convert: String) =
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            withContext(Dispatchers.Main) {
                val response = repository.getConversion(amount, symbol, convert)
                conversionLiveData.postValue(response)
            }
        }

    fun getCryptoInfoData() = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = repository.getCryptoInfo("ETH").body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                cryptoInfoLiveData.value = listOf(response.data)
            }
        }
    }

    fun getCryptoNews() = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        try {
            val response =
                repository.getCryptoNews("nfts", "d35eefdb54434e38877107bb4991bef9").body()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    cryptoNewsLiveData.value = response.articles
                }
            }
        } catch (e: HttpException) {
            Log.d("exception", e.toString())
        }
    }

    fun getNfts() = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        val response = repository.getNfts().body()
        withContext(Dispatchers.Main) {
            if (response != null) {
                nftsListLiveData.value = response
            }
        }
    }

    fun getBlockSpanNfts() = CoroutineScope(Dispatchers.IO).launch {

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

    fun insertTransaction(model: TransactionModel) = viewModelScope.launch {
        repository.insertTransaction(model)
    }

    fun roomTransaction(): LiveData<List<TransactionModel>> = repository.getTransaction()

    fun deleteTransaction(model: TransactionModel) = repository.deleteTransaction(model)

    fun onClear() {
        topGainersLosersLiveData.value = emptyList()
        topGainersLosersLiveData.value = emptyList()
        marketLiveData.value = emptyList()
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