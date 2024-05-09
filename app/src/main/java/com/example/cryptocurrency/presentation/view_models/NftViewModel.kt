package com.example.cryptocurrency.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.domain.model.NftItem
import com.example.cryptocurrency.domain.usecases.NftUseCase
import com.example.cryptocurrency.presentation.ui_states.CryptoUiState
import com.example.cryptocurrency.presentation.ui_states.NftUiState
import com.example.cryptocurrency.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NftViewModel @Inject constructor(private val useCase: NftUseCase): ViewModel() {

    private val _nft = MutableStateFlow(NftUiState())
    val nft: StateFlow<NftUiState> get() = _nft

    val nftList = MutableStateFlow<List<NftItem?>?>(emptyList())

    fun getNft() = viewModelScope.launch {
        useCase().collectLatest {
           when(it){
               is ApiResponse.Loading -> {
                   _nft.value = nft.value.copy(
                       loading = true,
                       nft = it.data ?: emptyList()
                   )
               }
               is ApiResponse.Success -> {
                   _nft.value = nft.value.copy(
                       loading = false,
                       nft = it.data ?: emptyList()
                   )
               }
               is ApiResponse.Error -> {
                   _nft.value = nft.value.copy(
                       loading = false,
                       error = it.message.orEmpty()
                   )
               }
           }
        }
    }
}