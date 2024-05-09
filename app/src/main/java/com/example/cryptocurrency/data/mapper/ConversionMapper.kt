package com.example.cryptocurrency.data.mapper

import com.example.cryptocurrency.data.remote.dtos.ConversionDataDto
import com.example.cryptocurrency.data.remote.dtos.ConversionDto
import com.example.cryptocurrency.data.remote.dtos.ConversionQuoteDto
import com.example.cryptocurrency.data.remote.dtos.ConversionStatusDto
import com.example.cryptocurrency.data.remote.dtos.ConversionUsdDto
import com.example.cryptocurrency.domain.model.Conversion
import com.example.cryptocurrency.domain.model.ConversionData
import com.example.cryptocurrency.domain.model.ConversionQuote
import com.example.cryptocurrency.domain.model.ConversionStatus
import com.example.cryptocurrency.domain.model.ConversionUsd

fun ConversionDto.toConversionDto(): Conversion =
    Conversion(
        data = data?.map { it?.toConversionData() },
        status = status?.toConversionStatus()
    )

fun ConversionStatusDto.toConversionStatus(): ConversionStatus =
    ConversionStatus(
        creditCount, elapsed, errorCode, errorMessage, notice, timestamp
    )

fun ConversionDataDto.toConversionData(): ConversionData = ConversionData(
        amount, id, lastUpdated, name, quote?.toConversionQuote(), symbol
    )

fun ConversionQuoteDto.toConversionQuote(): ConversionQuote = ConversionQuote(
        usd = usd?.toConversionUsd()
    )

fun ConversionUsdDto.toConversionUsd(): ConversionUsd = ConversionUsd(
        lastUpdated, price
    )