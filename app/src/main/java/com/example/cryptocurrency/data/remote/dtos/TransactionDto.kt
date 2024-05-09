package com.example.cryptocurrency.data.remote.dtos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "transaction_model")
data class TransactionDto(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    @SerializedName("current_price")
    var currentPrice: Double,
    @SerializedName("usd_amount")
    var usdAmount: Double,
    @SerializedName("token_amount")
    var tokenAmount: Double,
    @SerializedName("percent_change")
    val percentChange: Double,
)