package com.example.cryptocurrency.domain.repositories

import com.example.cryptocurrency.domain.model.NftItem
import com.example.cryptocurrency.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface NftRepository {

    fun getNft(): Flow<ApiResponse<List<NftItem?>>>
}