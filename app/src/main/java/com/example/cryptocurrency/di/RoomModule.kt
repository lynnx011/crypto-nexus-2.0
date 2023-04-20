package com.example.cryptocurrency.di
import android.content.Context
import androidx.room.Room
import com.example.cryptocurrency.room_database.CryptoDao
import com.example.cryptocurrency.room_database.CryptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(@ApplicationContext context: Context) : CryptoDatabase =
        Room.databaseBuilder(context.applicationContext,CryptoDatabase::class.java,"cryptos.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun provideCryptoDao(db: CryptoDatabase) : CryptoDao = db.cryptoDao()
}