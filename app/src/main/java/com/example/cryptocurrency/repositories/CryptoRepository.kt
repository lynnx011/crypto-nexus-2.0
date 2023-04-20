package com.example.cryptocurrency.repositories

import com.example.cryptocurrency.api_service.BlockSpanApi
import com.example.cryptocurrency.api_service.CryptoApi
import com.example.cryptocurrency.api_service.CryptoNewsApi
import com.example.cryptocurrency.api_service.GeckoApi
import com.example.cryptocurrency.model.CryptoDetails
import com.example.cryptocurrency.model.transaction.TransactionModel
import com.example.cryptocurrency.room_database.CryptoDatabase
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val cryptoApi: CryptoApi,private val cryptoNewsApi: CryptoNewsApi,private val blockSpanApi: BlockSpanApi,private val geckoApi: GeckoApi,private val db: CryptoDatabase) {

    suspend fun getTopCryptos() = cryptoApi.getCryptos(1,100)

    suspend fun getTopGainersLosers() = cryptoApi.getTopGainersLosers(1,700)

    suspend fun getCryptoInfo(symbol: String) = cryptoApi.getCryptoInfo(symbol)

    suspend fun getConversion(amount: Int,symbol: String,convert: String) = cryptoApi.getConversion(amount,symbol,convert)

    // Room Database
    fun getRoomCrypto() = db.cryptoDao().getAllCryptos()

    suspend fun insertCrypto(cryptos: List<CryptoDetails>) = db.cryptoDao().insertCryptos(cryptos)

    suspend fun deleteCrypto() = db.cryptoDao().deleteCryptos()

    suspend fun getCryptoNews(q: String,apiKey: String) = cryptoNewsApi.getCryptoNews(q,apiKey)

    suspend fun getNfts() = geckoApi.getNfts()

    suspend fun getBlockSpanNfts(chain: String, exchange: String, pageSize: String) = blockSpanApi.getBlockSpanNfts(chain, exchange, pageSize)

    fun getTransaction() = db.cryptoDao().getTransaction()

    suspend fun insertTransaction(transaction: TransactionModel) = db.cryptoDao().insertTransaction(transaction)

    fun deleteTransaction(transaction: TransactionModel) = db.cryptoDao().deleteTransaction(transaction)

}