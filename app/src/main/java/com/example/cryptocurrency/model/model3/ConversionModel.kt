package com.example.cryptocurrency.model.model3

data class ConversionModel(
    val data: List<Data>,
    val status: Status
)

data class Status(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: Any,
    val notice: String,
    val timestamp: String
)
data class Data(
    val amount: Int,
    val id: Int,
    val last_updated: String,
    val name: String,
    val quote: Quote,
    val symbol: String
)

data class Quote(
    val USD: USD
)

data class USD(
    val last_updated: String,
    val price: String
)