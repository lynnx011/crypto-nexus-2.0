package com.example.cryptocurrency.model.model4

data class BlockSpanModel(
    val chain: String,
    val cursor: String,
    val exchange: String,
    val per_page: String,
    val results: List<Result>,
    val total: Int
)

data class Result(
    val banner_image_url: String,
    val chat_url: Any,
    val contracts: List<Contract>,
    val description: String,
    val discord_url: String,
    val exchange: String,
    val exchange_url: String,
    val external_url: String,
    val featured_image_url: String,
    val image_url: String,
    val instagram_username: String,
    val key: String,
    val large_image_url: String,
    val name: String,
    val stats: Stats,
    val telegram_url: String,
    val twitter_username: String,
    val update_at: String,
    val wiki_url: Any
)

data class Contract(
    val contract_address: String
)

data class Stats(
    val market_cap: Double,
    val num_owners: Int,
    val one_day_average_price: Double,
    val one_day_sales: Int,
    val one_day_volume: Double,
    val one_day_volume_change: Double,
    val seven_day_average_price: Double,
    val seven_day_sales: Int,
    val seven_day_volume: Double,
    val seven_day_volume_change: Double,
    val thirty_day_average_price: Double,
    val thirty_day_sales: Int,
    val thirty_day_volume: Double,
    val thirty_day_volume_change: Double,
    val total_average_price: Double,
    val total_minted: Int,
    val total_sales: String,
    val total_supply: Int,
    val total_volume: Double
)