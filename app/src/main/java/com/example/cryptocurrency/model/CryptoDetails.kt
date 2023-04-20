package com.example.cryptocurrency.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_details")
data class CryptoDetails(
    @PrimaryKey
    val id: Int,
    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    val last_updated: String,
    val max_supply: Long,
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