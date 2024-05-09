package com.example.cryptocurrency.presentation.ui_states

import com.example.cryptocurrency.domain.model.Conversion
import com.example.cryptocurrency.domain.model.CryptoDetails

data class CryptoUiState(
    val loading: Boolean = false,
    val cryptos: List<CryptoDetails?>? = emptyList(),
    val error: String = ""
)

data class CryptoDetailsUiState(
    val loading: Boolean = false,
    val cryptoDetails: CryptoDetails? = null,
    val error: String = ""
)

data class ConversionUiState(
    val loading: Boolean = false,
    val conversion: Conversion? = null,
    val error: String = ""
)