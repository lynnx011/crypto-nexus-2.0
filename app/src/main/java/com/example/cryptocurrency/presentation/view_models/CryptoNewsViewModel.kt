package com.example.cryptocurrency.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.domain.model.NftArticle
import com.example.cryptocurrency.domain.usecases.CryptoNewsUseCase
import com.example.cryptocurrency.presentation.ui_states.CryptoNewsUiState
import com.example.cryptocurrency.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoNewsViewModel @Inject constructor(private val useCase: CryptoNewsUseCase): ViewModel() {

    private val _news = MutableStateFlow(CryptoNewsUiState())
    val news: StateFlow<CryptoNewsUiState> get() = _news

    val cryptoNews = MutableStateFlow<List<NftArticle?>?>(emptyList())

    fun getCryptoNews(q: String) = viewModelScope.launch {
        useCase(q = q).collectLatest {
            when(it){
                is ApiResponse.Loading -> {
                    _news.value = news.value.copy(
                        loading = true,
                        cryptoNews = it.data ?: emptyList()
                    )
                }
                is ApiResponse.Success -> {
                    _news.value = news.value.copy(
                        loading = false,
                        cryptoNews = it.data ?: emptyList()
                    )
                }
                is ApiResponse.Error -> {
                    _news.value = news.value.copy(
                        loading = false,
                        error = it.message.orEmpty()
                    )
                }
            }
        }
    }
}