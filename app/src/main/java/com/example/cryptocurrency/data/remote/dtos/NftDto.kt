package com.example.cryptocurrency.data.remote.dtos

import com.google.gson.annotations.SerializedName

class NftsDto : ArrayList<NftItemDto?>()

data class NftItemDto(
    @SerializedName("asset_platform_id")
    val assetPlatformId: String?,
    @SerializedName("contract_address")
    val contractAddress: String?,
    val id: String?,
    val name: String?,
    val symbol: String?
)