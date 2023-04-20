package com.example.cryptocurrency.model.model3

data class Data(
    val amount: Int,
    val id: Int,
    val last_updated: String,
    val name: String,
    val quote: Quote,
    val symbol: String
)