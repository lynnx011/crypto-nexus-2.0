package com.example.cryptocurrency.domain.model

import com.google.gson.annotations.SerializedName

class Nfts : ArrayList<NftItem>()

data class NftItem(
    @SerializedName("asset_platform_id")
    val assetPlatformId: String?,
    @SerializedName("contract_address")
    val contractAddress: String?,
    val id: String?,
    val name: String?,
    val symbol: String?
)