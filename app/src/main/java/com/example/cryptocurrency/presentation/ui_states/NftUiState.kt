package com.example.cryptocurrency.presentation.ui_states

import com.example.cryptocurrency.domain.model.NftItem

data class NftUiState(
    val loading: Boolean = false,
    val nft: List<NftItem?>? = emptyList(),
    val error: String = ""
)
