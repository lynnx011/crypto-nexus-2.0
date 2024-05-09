package com.example.cryptocurrency.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Crypto(
    val data: List<CryptoDetails?>?,
    val status: CryptoStatus?
)

@Entity(tableName = "crypto_details")
data class CryptoDetails(
    @PrimaryKey
    val id: Int?,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double?,
    @SerializedName("cmc_rank")
    val cmcRank: Int?,
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("max_supply")
//    val maxSupply: Long?,
    val name: String?,
    @SerializedName("num_market_pairs")
    val numMarketPairs: Int?,
    val platform: CryptoPlatform?,
    val quote: CryptoQuote?,
    @SerializedName("self_reported_circulating_supply")
    val reportedCirculatingSupplies: Double?,
    @SerializedName("self_reported_market_cap")
    val marketCap: Double?,
    val slug: String?,
    val symbol: String?,
    val tags: List<String?>?,
    @SerializedName("total_supply")
    val totalSupply: Double?,
    @SerializedName("tvl_ratio")
    val tvlRatio: Double?
)

data class CryptoQuote(
    @SerializedName("USD")
    val usd: CryptoUsd?
)

data class CryptoUsd(
    @SerializedName("fully_diluted_market_cap")
    val dilutedMarketCap: Double?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("market_cap")
    val marketCap: Double?,
    @SerializedName("market_cap_dominance")
    val marketCapDominance: Double?,
    @SerializedName("percent_change_1h")
    val percentChange1h: Double?,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double?,
    @SerializedName("percent_change_30d")
    val percentChange30d: Double?,
    @SerializedName("percent_change_60d")
    val percentChange60d: Double?,
    @SerializedName("percent_change_7d")
    val percentChange7d: Double?,
    @SerializedName("percent_change_90d")
    val percentChange90d: Double?,
    val price: Double?,
    val tvl: Double?,
    @SerializedName("volume_24h")
    val volume24h: Double?,
    @SerializedName("volume_change_24h")
    val volumeChange24h: Double?
)

data class CryptoPlatform(
    val id: Int?,
    val name: String?,
    val slug: String?,
    val symbol: String?,
    @SerializedName("token_address")
    val tokenAddress: String?
)

data class CryptoStatus(
    @SerializedName("credit_count")
    val creditCount: Int?,
    val elapsed: Int?,
    @SerializedName("error_code")
    val errorCode: Int?,
    @SerializedName("error_message")
    val errorMessage: Any?,
    val notice: Any?,
    val timestamp: String?,
    @SerializedName("total_count")
    val totalCount: Int?
)