package com.example.cryptocurrency.model.model2

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