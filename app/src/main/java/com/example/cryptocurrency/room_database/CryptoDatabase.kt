package com.example.cryptocurrency.room_database
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cryptocurrency.domain.model.CryptoDetails
import com.example.cryptocurrency.domain.model.Transaction

@Database(entities = [CryptoDetails::class, Transaction::class], version = 1, exportSchema = false)
@TypeConverters(PlatformTypeConverter::class,QuoteTypeConverter::class,TagTypeConverter::class)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDao() : CryptoDao

}