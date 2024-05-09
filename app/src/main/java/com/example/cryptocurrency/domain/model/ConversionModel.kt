package com.example.cryptocurrency.domain.model

import com.google.gson.annotations.SerializedName

data class Conversion(
    val data: List<ConversionData?>?,
    val status: ConversionStatus?
)

data class ConversionStatus(
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
data class ConversionData(
    val amount: Int?,
    val id: Int?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    val name: String?,
    val quote: ConversionQuote?,
    val symbol: String?
)

data class ConversionQuote(
    @SerializedName("USD")
    val usd: ConversionUsd?
)

data class ConversionUsd(
    @SerializedName("last_updated")
    val lastUpdated: String?,
    val price: String?
)