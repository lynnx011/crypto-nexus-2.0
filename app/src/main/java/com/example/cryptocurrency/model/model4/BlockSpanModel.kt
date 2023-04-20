package com.example.cryptocurrency.model.model4

data class BlockSpanModel(
    val chain: String,
    val cursor: String,
    val exchange: String,
    val per_page: String,
    val results: List<Result>,
    val total: Int
)