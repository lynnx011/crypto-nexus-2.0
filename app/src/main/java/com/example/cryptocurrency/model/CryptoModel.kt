package com.example.cryptocurrency.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CryptoModel(
    val data: List<CryptoDetails>,
    val status: Status
)

@Entity(tableName = "crypto_details")
data class CryptoDetails(
    @PrimaryKey
    val id: Int,
    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    val last_updated: String,
//    val max_supply: Long,
    val name: String,
    val num_market_pairs: Int,
    val platform: Platform?,
    val quote: Quote,
    val self_reported_circulating_supply: Double,
    val self_reported_market_cap: Double,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double,
    val tvl_ratio: Double
)

data class Quote(
    val USD: USD
)

data class USD(
    val fully_diluted_market_cap: Double,
    val last_updated: String,
    val market_cap: Double,
    val market_cap_dominance: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_30d: Double,
    val percent_change_60d: Double,
    val percent_change_7d: Double,
    val percent_change_90d: Double,
    val price: Double,
    val tvl: Double,
    val volume_24h: Double,
    val volume_change_24h: Double
)

data class Platform(
    val id: Int,
    val name: String,
    val slug: String,
    val symbol: String,
    val token_address: String
)

data class Status(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: Any,
    val notice: Any,
    val timestamp: String,
    val total_count: Int
)