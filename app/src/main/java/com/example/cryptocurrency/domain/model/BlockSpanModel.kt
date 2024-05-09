package com.example.cryptocurrency.domain.model

import com.google.gson.annotations.SerializedName

data class BlockSpan(
    val chain: String?,
    val cursor: String?,
    val exchange: String?,
    @SerializedName("per_page")
    val perPage: String?,
    val results: List<BlockSpanResult?>?,
    val total: Int?
)

data class BlockSpanResult(
    @SerializedName("banner_image_url")
    val bannerImageUrl: String?,
    @SerializedName("chat_url")
    val chatUrl: Any?,
    val contracts: List<BlockSpanContract?>?,
    val description: String?,
    @SerializedName("discord_url")
    val discordUrl: String?,
    val exchange: String?,
    @SerializedName("exchange_url")
    val exchangeUrl: String?,
    @SerializedName("external_url")
    val externalUrl: String?,
    @SerializedName("featured_image_url")
    val featuredImageUrl: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    val key: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?,
    val name: String?,
    val stats: BlockSpanStats?,
    @SerializedName("telegram_url")
    val telegramUrl: String?,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    @SerializedName("update_at")
    val updateAt: String?,
    @SerializedName("wiki_url")
    val wikiUrl: Any?
)

data class BlockSpanContract(
    @SerializedName("contract_address")
    val contractAddress: String?
)

data class BlockSpanStats(
    @SerializedName("market_cap")
    val marketCap: Double?,
    @SerializedName("num_owners")
    val numOwners: Int?,
    @SerializedName("one_day_average_price")
    val oneDayAveragePrice: Double?,
    @SerializedName("one_day_sales")
    val oneDaySales: Int?,
    @SerializedName("one_day_volume")
    val oneDayVolume: Double?,
    @SerializedName("one_day_volume_change")
    val oneDayVolumeChange: Double?,
    @SerializedName("seven_day_average_price")
    val sevenDayAveragePrice: Double?,
    @SerializedName("seven_day_sales")
    val sevenDaySales: Int?,
    @SerializedName("seven_day_volume")
    val sevenDayVolume: Double?,
    @SerializedName("seven_day_volume_change")
    val sevenDayVolumeChange: Double?,
    @SerializedName("thirty_day_average_price")
    val thirtyDayAveragePrice: Double?,
    @SerializedName("thirty_day_sales")
    val thirtyDaySales: Int?,
    @SerializedName("thirty_day_volume")
    val thirtyDayVolume: Double?,
    @SerializedName("thirty_day_volume_change")
    val thirtyDayVolumeChange: Double?,
    @SerializedName("total_average_price")
    val totalAveragePrice: Double?,
    @SerializedName("total_minted")
    val totalMinted: Int?,
    @SerializedName("total_sales")
    val totalSales: String?,
    @SerializedName("total_supply")
    val totalSupply: Int?,
    @SerializedName("total_volume")
    val totalVolume: Double?
)