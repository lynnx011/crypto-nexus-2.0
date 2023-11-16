package com.example.cryptocurrency.model.model6

class NftsModel : ArrayList<NftsModelItem>()

data class NftsModelItem(
    val asset_platform_id: String,
    val contract_address: String,
    val id: String,
    val name: String,
    val symbol: String
)