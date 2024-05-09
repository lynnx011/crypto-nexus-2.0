package com.example.cryptocurrency.data.remote.dtos

data class NftsNewsDto(
    val articles: List<NftArticleDto>,
    val status: String,
    val totalResults: Int
)

data class NftArticleDto(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: NftSourceDto,
    val title: String,
    val url: String,
    val urlToImage: String
)

data class NftSourceDto(
    val id: String,
    val name: String
)