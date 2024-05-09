package com.example.cryptocurrency.room_database
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.cryptocurrency.domain.model.CryptoQuote
import com.google.gson.Gson

@TypeConverters
class QuoteTypeConverter {
    @TypeConverter
    fun fromJson(value: String): CryptoQuote {
        return Gson().fromJson(value, CryptoQuote::class.java)
    }

    @TypeConverter
    fun toJson(quote: CryptoQuote): String {
        return Gson().toJson(quote)
    }
}