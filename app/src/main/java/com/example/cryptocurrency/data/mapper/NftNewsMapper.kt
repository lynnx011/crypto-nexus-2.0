package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.remote.dtos.NftArticleDto
import com.example.cryptocurrency.data.remote.dtos.NftSourceDto
import com.example.cryptocurrency.data.remote.dtos.NftsNewsDto
import com.example.cryptocurrency.domain.model.NftArticle
import com.example.cryptocurrency.domain.model.NftSource
import com.example.cryptocurrency.domain.model.NftsNews

fun NftsNewsDto.toNftNews(): NftsNews {
    return NftsNews(
        articles.map { it.toNftArticle() }, status, totalResults
    )
}

fun NftArticleDto.toNftArticle(): NftArticle {
    return NftArticle(
        author, content, description, publishedAt, source.toNftSource(), title, url, urlToImage
    )
}

fun NftSourceDto.toNftSource(): NftSource {
    return NftSource(
        id, name
    )
}