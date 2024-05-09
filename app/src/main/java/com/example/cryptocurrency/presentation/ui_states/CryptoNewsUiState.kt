package com.example.cryptocurrency.presentation.ui_states

import com.example.cryptocurrency.domain.model.NftArticle

data class CryptoNewsUiState(
    val loading: Boolean = false,
    val cryptoNews: List<NftArticle?>? = emptyList(),
    val error: String = ""
)
