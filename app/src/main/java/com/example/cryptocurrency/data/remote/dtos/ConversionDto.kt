package com.example.cryptocurrency.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class ConversionDto(
    val data: List<ConversionDataDto?>?,
    val status: ConversionStatusDto?
)

data class ConversionStatusDto(
    @SerializedName("credit_count")
    val creditCount: Int?,
    val elapsed: Int?,
    @SerializedName("error_code")
    val errorCode: Int?,
    @SerializedName("error_message")
    val errorMessage: Any?,
    val notice: String?,
    val timestamp: String?
)

data class ConversionDataDto(
    val amount: Int?,
    val id: Int?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    val name: String?,
    val quote: ConversionQuoteDto?,
    val symbol: String?
)

data class ConversionQuoteDto(
    @SerializedName("USD")
    val usd: ConversionUsdDto?
)

data class ConversionUsdDto(
    @SerializedName("last_updated")
    val lastUpdated: String?,
    val price: String?
)