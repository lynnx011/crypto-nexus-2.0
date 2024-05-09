package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.remote.dtos.BlockSpanContractDto
import com.example.cryptocurrency.data.remote.dtos.BlockSpanDto
import com.example.cryptocurrency.data.remote.dtos.BlockSpanResultDto
import com.example.cryptocurrency.data.remote.dtos.BlockSpanStatsDto
import com.example.cryptocurrency.domain.model.BlockSpan
import com.example.cryptocurrency.domain.model.BlockSpanContract
import com.example.cryptocurrency.domain.model.BlockSpanResult
import com.example.cryptocurrency.domain.model.BlockSpanStats

fun BlockSpanDto.toBlockSpan(): BlockSpan =
    BlockSpan(
        chain = chain,
        cursor = cursor,
        exchange = exchange,
        perPage = perPage,
        results = results?.map { it?.toBlockSpanResult() },
        total = total
    )
fun BlockSpanResultDto.toBlockSpanResult(): BlockSpanResult {
    return BlockSpanResult(
        bannerImageUrl = bannerImageUrl,
        chatUrl = chatUrl,
        contracts = contracts?.map { it?.toBlockSpanContract() },
        description = description,
        discordUrl = discordUrl,
        exchange = exchange,
        exchangeUrl = exchangeUrl,
        externalUrl = externalUrl,
        featuredImageUrl = featuredImageUrl,
        imageUrl = imageUrl,
        instagramUsername = instagramUsername,
        key = key,
        largeImageUrl = largeImageUrl,
        name = name,
        stats = stats?.toBlockSpanStats(),
        telegramUrl = telegramUrl,
        twitterUsername = twitterUsername,
        updateAt = updateAt,
        wikiUrl = wikiUrl
    )
}

fun BlockSpanContractDto.toBlockSpanContract(): BlockSpanContract {
    return BlockSpanContract(contractAddress = contractAddress)
}

fun BlockSpanStatsDto.toBlockSpanStats(): BlockSpanStats {
    return BlockSpanStats(
        marketCap,
        numOwners,
        oneDayAveragePrice,
        oneDaySales,
        oneDayVolume,
        oneDayVolumeChange,
        sevenDayAveragePrice,
        sevenDaySales,
        sevenDayVolume,
        sevenDayVolumeChange,
        thirtyDayAveragePrice,
        thirtyDaySales,
        thirtyDayVolume,
        thirtyDayVolumeChange,
        totalAveragePrice,
        totalMinted,
        totalSales,
        totalSupply,
        totalVolume
    )
}