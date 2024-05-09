package com.example.cryptocurrency.di

import com.example.cryptocurrency.domain.usecase_impl.BlockSpanUseCaseImpl
import com.example.cryptocurrency.domain.usecase_impl.CryptoNewsUseCaseImpl
import com.example.cryptocurrency.domain.usecase_impl.CryptoUseCaseImpl
import com.example.cryptocurrency.domain.usecase_impl.NftUseCaseImpl
import com.example.cryptocurrency.domain.usecases.BlockSpanUseCase
import com.example.cryptocurrency.domain.usecases.CryptoNewsUseCase
import com.example.cryptocurrency.domain.usecases.CryptoUseCase
import com.example.cryptocurrency.domain.usecases.NftUseCase
import com.example.cryptocurrency.domain.repositories.BlockSpanRepository
import com.example.cryptocurrency.domain.repositories.CryptoNewsRepository
import com.example.cryptocurrency.domain.repositories.CryptoRepository
import com.example.cryptocurrency.domain.repositories.NftRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCryptoUseCase(cryptoRepository: CryptoRepository): CryptoUseCase =
        CryptoUseCaseImpl(repository = cryptoRepository)

    @Provides
    @Singleton
    fun provideBlockSpanUseCase(blockSpanRepository: BlockSpanRepository): BlockSpanUseCase =
        BlockSpanUseCaseImpl(blockSpanRepository = blockSpanRepository)

    @Provides
    @Singleton
    fun provideCryptoNewsUseCase(cryptoNewsRepository: CryptoNewsRepository): CryptoNewsUseCase =
        CryptoNewsUseCaseImpl(cryptoNewsRepository = cryptoNewsRepository)

    @Provides
    @Singleton
    fun provideNftUseCase(nftRepository: NftRepository): NftUseCase =
        NftUseCaseImpl(nftRepository = nftRepository)
}