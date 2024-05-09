package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.remote.dtos.CryptoDetailsDto
import com.example.cryptocurrency.data.remote.dtos.CryptoDto
import com.example.cryptocurrency.data.remote.dtos.CryptoPlatformDto
import com.example.cryptocurrency.data.remote.dtos.CryptoQuoteDto
import com.example.cryptocurrency.data.remote.dtos.CryptoStatusDto
import com.example.cryptocurrency.data.remote.dtos.CryptoUsdDto
import com.example.cryptocurrency.domain.model.Crypto
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.model.CryptoPlatform
import com.example.cryptocurrency.domain.model.CryptoQuote
import com.example.cryptocurrency.domain.model.CryptoStatus
import com.example.cryptocurrency.domain.model.CryptoUsd

fun CryptoDto.toCrypto(): Crypto {
    return Crypto(
        data = data?.map { it?.toCryptoDetails() },
        status = status?.toCryptoStatus()
    )
}

fun CryptoDetailsDto.toCryptoDetails(): CryptoDetails {
    return CryptoDetails(
        id,
        circulatingSupply,
        cmcRank,
        dateAdded,
        lastUpdated,
//        maxSupply,
        name,
        numMarketPairs,
        platform?.toCryptoPlatform(),
        quote?.toCryptoQuote(),
        reportedCirculatingSupplies,
        marketCap,
        slug,
        symbol,
        tags,
        totalSupply,
        tvlRatio
    )
}

fun CryptoPlatformDto.toCryptoPlatform(): CryptoPlatform {
    return CryptoPlatform(
        id, name, slug, symbol, tokenAddress
    )
}

fun CryptoQuoteDto.toCryptoQuote(): CryptoQuote {
    return CryptoQuote(
        usd = usd.toCryptoUsd()
    )
}

fun CryptoUsdDto.toCryptoUsd(): CryptoUsd {
    return CryptoUsd(
        dilutedMarketCap,
        lastUpdated,
        marketCap,
        marketCapDominance,
        percentChange1h,
        percentChange24h,
        percentChange30d,
        percentChange60d,
        percentChange7d,
        percentChange90d,
        price,
        tvl,
        volume24h,
        volumeChange24h
    )
}

fun CryptoStatusDto.toCryptoStatus(): CryptoStatus {
    return CryptoStatus(
        creditCount, elapsed, errorCode, errorMessage, notice, timestamp, totalCount
    )
}