package com.example.cryptocurrency.domain.model

data class NftsNews(
    val articles: List<NftArticle?>?,
    val status: String?,
    val totalResults: Int?
)

data class NftArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: NftSource?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

data class NftSource(
    val id: String?,
    val name: String?
)