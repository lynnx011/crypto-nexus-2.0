package com.example.cryptocurrency.model.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_model")
data class TransactionModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val current_price: Double,
    val usd_amount: Double,
    val token_amount: Double,
    val percent_change: Double,
)
