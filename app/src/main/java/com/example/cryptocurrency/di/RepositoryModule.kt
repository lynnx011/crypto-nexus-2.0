package com.example.cryptocurrency.di

import com.example.cryptocurrency.data.remote.api_services.api_service.CryptoApi
import com.example.cryptocurrency.data.remote.api_services.api_service.CryptoNewsApi
import com.example.cryptocurrency.data.remote.api_services.api_service.BlockSpanApi
import com.example.cryptocurrency.data.remote.api_services.api_service.GeckoApi
import com.example.cryptocurrency.data.repository_impl.BlockSpanRepositoryImpl
import com.example.cryptocurrency.data.repository_impl.CryptoNewsRepositoryImpl
import com.example.cryptocurrency.data.repository_impl.CryptoRepositoryImpl
import com.example.cryptocurrency.data.repository_impl.NftRepositoryImpl
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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(cryptoApi: CryptoApi): CryptoRepository =
        CryptoRepositoryImpl(cryptoApi = cryptoApi)

    @Provides
    @Singleton
    fun provideBlockSpanRepository(blockSpanApi: BlockSpanApi): BlockSpanRepository =
        BlockSpanRepositoryImpl(blockSpanApi = blockSpanApi)

    @Provides
    @Singleton
    fun provideCryptoNewsRepository(cryptoNewsApi: CryptoNewsApi): CryptoNewsRepository =
        CryptoNewsRepositoryImpl(cryptoNewsApi = cryptoNewsApi)

    @Provides
    @Singleton
    fun provideNftRepository(nftApi: GeckoApi): NftRepository =
        NftRepositoryImpl(nftApi = nftApi)
}