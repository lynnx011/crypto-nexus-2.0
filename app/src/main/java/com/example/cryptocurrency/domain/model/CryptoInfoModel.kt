package com.example.cryptocurrency.domain.model

import com.google.gson.annotations.SerializedName

data class CryptoInfo(
    val data: InfoDetails?,
    val status: InfoStatus?
)

data class InfoStatus(
    @SerializedName("credit_count")
    val creditCount: Int?,
    val elapsed: Int?,
    @SerializedName("error_code")
    val errorCode: Int?,
    @SerializedName("error_message")
    val errorMessage: Any?,
    val notice: Any?,
    val timestamp: String?
)

data class InfoDetails(
    val category: String?,
    @SerializedName("contract_address")
    val contractAddress: List<Any?>?,
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("date_launched")
    val dateLaunched: Any?,
    val description: String?,
    val id: Int?,
    @SerializedName("is_hidden")
    val isHidden: Int?,
    val logo: String?,
    val name: String?,
    val notice: String?,
    val platform: Any?,
    @SerializedName("self_reported_circulating_supply")
    val circulatingSupply: Any?,
    @SerializedName("self_reported_market_cap")
    val marketCap: Any?,
    @SerializedName("self_reported_tags")
    val reportedTags: Any?,
    val slug: String?,
    val subreddit: String?,
    val symbol: String?,
    val tags: List<String?>?,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    val urls: InfoUrls?
)

data class InfoUrls(
    val announcement: List<Any?>?,
    val chat: List<Any?>?,
    val explorer: List<String?>?,
    val facebook: List<Any?>?,
    @SerializedName("message_board")
    val messageBoard: List<String?>?,
    val reddit: List<String?>?,
    @SerializedName("source_code")
    val sourceCode: List<String?>?,
    @SerializedName("technical_doc")
    val technicalDoc: List<String?>?,
    val twitter: List<Any?>?,
    val website: List<String?>?
)