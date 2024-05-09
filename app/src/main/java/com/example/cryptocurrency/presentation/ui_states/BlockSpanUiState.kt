package com.example.cryptocurrency.presentation.ui_states
import com.example.cryptocurrency.domain.model.BlockSpan

data class BlockSpanUiState(
    val loading: Boolean = false,
    val blockSpan: BlockSpan? = null,
    val error: String = ""
)

data class BlockSpanDetailsUiState(
    val studioName: String = "",
    val project: String = "",
    val desc: String = "",
    val imageUrl: String = "",
    val featuredUrl: String = "",
    val supply: Int = 0,
    val owners: Int = 0,
    val volume: Double = 0.0,
    val exchangeUrl: String = ""
)
