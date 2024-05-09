package com.example.cryptocurrency.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.domain.model.BlockSpan
import com.example.cryptocurrency.domain.model.BlockSpanResult
import com.example.cryptocurrency.domain.model.BlockSpanStats
import com.example.cryptocurrency.domain.usecases.BlockSpanUseCase
import com.example.cryptocurrency.presentation.ui_states.BlockSpanDetailsUiState
import com.example.cryptocurrency.presentation.ui_states.BlockSpanUiState
import com.example.cryptocurrency.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockSpanViewModel @Inject constructor(private val useCase: BlockSpanUseCase): ViewModel() {

    private val _blockSpan = MutableStateFlow(BlockSpanUiState())
    val blockSpan: StateFlow<BlockSpanUiState> get() = _blockSpan

    val blockSpanNft = MutableStateFlow<BlockSpan?>(null)
    val blockSpanResult = MutableStateFlow<BlockSpanResult?>(null)
    val blockSpanStats = MutableStateFlow<BlockSpanStats?>(null)

    val blockSpanUiState = combine(blockSpanResult, blockSpanStats){ result ,stats ->
        BlockSpanDetailsUiState(
            studioName = result?.key.orEmpty(),
            project = result?.name.orEmpty(),
            desc = result?.description.orEmpty(),
            imageUrl = result?.imageUrl.orEmpty(),
            featuredUrl = result?.featuredImageUrl.orEmpty(),
            supply = stats?.totalSupply ?: 0,
            owners = stats?.numOwners ?: 0,
            volume = stats?.totalVolume ?: 0.0,
            exchangeUrl = result?.exchangeUrl.orEmpty()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(300),
        initialValue = BlockSpanDetailsUiState()
    )

    fun getBlockSpanNft() = viewModelScope.launch {
        useCase("eth-main", "opensea", "100").collectLatest {
            when(it){
                is ApiResponse.Loading -> {
                    _blockSpan.value = blockSpan.value.copy(
                        loading = true,
                        blockSpan = it.data
                    )
                }
                is ApiResponse.Success -> {
                    _blockSpan.value = blockSpan.value.copy(
                        loading = false,
                        blockSpan = it.data
                    )
                }
                is ApiResponse.Error -> {
                    _blockSpan.value = blockSpan.value.copy(
                        loading = false,
                        error = it.message.orEmpty()
                    )
                }
            }
        }
    }
}