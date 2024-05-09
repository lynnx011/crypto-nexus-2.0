package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.remote.dtos.InfoDetailsDto
import com.example.cryptocurrency.data.remote.dtos.InfoStatusDto
import com.example.cryptocurrency.data.remote.dtos.InfoUrlsDto
import com.example.cryptocurrency.domain.model.InfoDetails
import com.example.cryptocurrency.domain.model.InfoStatus
import com.example.cryptocurrency.domain.model.InfoUrls

fun InfoStatusDto.toInfoStatus(): InfoStatus {
    return InfoStatus(
        creditCount, elapsed, errorCode, errorMessage, notice, timestamp
    )
}

fun InfoDetailsDto.toInfoDetails(): InfoDetails {
    return InfoDetails(
        category,
        contractAddress,
        dateAdded,
        dateLaunched,
        description,
        id,
        isHidden,
        logo,
        name,
        notice,
        platform,
        circulatingSupply,
        marketCap,
        reportedTags,
        slug,
        subreddit,
        symbol,
        tags,
        twitterUsername,
        urls?.toInfoUrls()
    )
}

fun InfoUrlsDto.toInfoUrls(): InfoUrls {
    return InfoUrls(
        announcement,
        chat,
        explorer,
        facebook,
        messageBoard,
        reddit,
        sourceCode,
        technicalDoc,
        twitter,
        website
    )
}