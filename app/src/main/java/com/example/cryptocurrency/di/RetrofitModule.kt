package com.example.cryptocurrency.di
import com.example.cryptocurrency.api_service.BlockSpanApi
import com.example.cryptocurrency.api_service.CryptoApi
import com.example.cryptocurrency.api_service.CryptoNewsApi
import com.example.cryptocurrency.api_service.GeckoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://pro-api.coinmarketcap.com/"
    private const val BASE_URL1 = "https://newsapi.org/v2/"
    private const val BASE_URL3 = "https://api.coingecko.com/api/v3/"
    private const val BASE_URL2 = "https://api.blockspan.com/v1/"
    private const val COIN_MARKET_CAP_API_KEY = "d1847b16-720d-4e8f-b02e-00c5d5c6d8cc"
    private const val BLOCK_API_KEY = "sUnkWjd1DZ0vFcNQ6fy8ujbHg1TwR2qH"

    @Provides
    @Singleton
    @Named("CoinMarketCap")
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("X-CMC_PRO_API_KEY", COIN_MARKET_CAP_API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitCoinMarketCap(@Named("CoinMarketCap") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi = retrofit.create(CryptoApi::class.java)

    @Provides
    @Singleton
    @Named("CryptoNews")
    fun provideOkHttpClient1(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitCryptoNews(@Named("CryptoNews") okHttpClient: OkHttpClient): CryptoNewsApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CryptoNewsApi::class.java)

    @Provides
    @Singleton
    @Named("CoinGecko")
    fun provideOkHttpClient2(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitCoinGecko(@Named("CoinGecko") okHttpClient: OkHttpClient): GeckoApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL3)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GeckoApi::class.java)

    @Provides
    @Singleton
    @Named("BlockSpan")
    fun provideOkHttpClient3(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("x-api-key", BLOCK_API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitBlockSpan(@Named("BlockSpan") okHttpClient: OkHttpClient): BlockSpanApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BlockSpanApi::class.java)

}