package com.example.cryptocurrency.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.usecases.CryptoUseCase
import com.example.cryptocurrency.presentation.ui_states.CryptoUiState
import com.example.cryptocurrency.utils.ApiResponse
import com.example.cryptocurrency.utils.CRYPTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val useCase: CryptoUseCase): ViewModel() {

    private val _top = MutableStateFlow(CryptoUiState())
    val top: StateFlow<CryptoUiState> = _top

    val topCryptos = MutableStateFlow<List<CryptoDetails?>?>(emptyList())

    private val _gainers = MutableStateFlow(CryptoUiState())
    val gainers: StateFlow<CryptoUiState> = _gainers

    val topGainers = MutableStateFlow<List<CryptoDetails?>?>(emptyList())

    private val _losers = MutableStateFlow(CryptoUiState())
    val losers: StateFlow<CryptoUiState> = _losers

    val topLosers = MutableStateFlow<List<CryptoDetails?>?>(emptyList())

    private val _market = MutableStateFlow(CryptoUiState())
    val market: StateFlow<CryptoUiState> = _market

    val marketList = MutableStateFlow<List<CryptoDetails?>?>(emptyList())

    val cryptoDetails = MutableStateFlow<CryptoDetails?>(null)

    fun getCryptos(start: Int, limit: Int, type: String) = viewModelScope.launch {
        useCase(start = start, limit = limit).collectLatest {
            when(it){
                is ApiResponse.Loading -> {
                    when (type) {
                        CRYPTO.TOP_CRYPTO.type -> {
                            _top.value = top.value.copy(
                                loading = true,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                        CRYPTO.GAINER.type -> {
                            _gainers.value = gainers.value.copy(
                                loading = true,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                        CRYPTO.LOSER.type -> {
                            _losers.value = losers.value.copy(
                                loading = true,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                        else -> {
                            _market.value = market.value.copy(
                                loading = true,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                    }
                }
                is ApiResponse.Success -> {
                    when (type) {
                        CRYPTO.TOP_CRYPTO.type -> {
                            _top.value = top.value.copy(
                                loading = false,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                        CRYPTO.GAINER.type -> {
                            _gainers.value = gainers.value.copy(
                                loading = false,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                        CRYPTO.LOSER.type -> {
                            _losers.value = losers.value.copy(
                                loading = false,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                        else -> {
                            _market.value = market.value.copy(
                                loading = false,
                                cryptos = it.data ?: emptyList()
                            )
                        }
                    }
                }
                is ApiResponse.Error -> {
                    when (type) {
                        CRYPTO.TOP_CRYPTO.type -> {
                            _top.value = top.value.copy(
                                loading = false,
                                error = it.message.toString()
                            )
                        }
                        CRYPTO.GAINER.type -> {
                            _gainers.value = gainers.value.copy(
                                loading = false,
                                error = it.message.toString()
                            )
                        }
                        CRYPTO.LOSER.type -> {
                            _losers.value = losers.value.copy(
                                loading = false,
                                error = it.message.toString()
                            )
                        }
                        else -> {
                            _market.value = market.value.copy(
                                loading = false,
                                error = it.message.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}