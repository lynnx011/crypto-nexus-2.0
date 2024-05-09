package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.remote.dtos.NftItemDto
import com.example.cryptocurrency.data.remote.dtos.NftsDto
import com.example.cryptocurrency.domain.model.NftItem
import com.example.cryptocurrency.domain.model.Nfts

fun NftItemDto.toNftItem(): NftItem {
    return NftItem(
        assetPlatformId, contractAddress, id, name, symbol
    )
}