package com.example.cryptocurrency.model.model2

data class CryptoInfoModel(
    val data: Details,
    val status: Status
)

data class Status(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: Any,
    val notice: Any,
    val timestamp: String
)

data class Details(
    val category: String,
    val contract_address: List<Any>,
    val date_added: String,
    val date_launched: Any,
    val description: String,
    val id: Int,
    val is_hidden: Int,
    val logo: String,
    val name: String,
    val notice: String,
    val platform: Any,
    val self_reported_circulating_supply: Any,
    val self_reported_market_cap: Any,
    val self_reported_tags: Any,
    val slug: String,
    val subreddit: String,
    val symbol: String,
    val tags: List<String>,
    val twitter_username: String,
    val urls: Urls
)

data class Urls(
    val announcement: List<Any>,
    val chat: List<Any>,
    val explorer: List<String>,
    val facebook: List<Any>,
    val message_board: List<String>,
    val reddit: List<String>,
    val source_code: List<String>,
    val technical_doc: List<String>,
    val twitter: List<Any>,
    val website: List<String>
)