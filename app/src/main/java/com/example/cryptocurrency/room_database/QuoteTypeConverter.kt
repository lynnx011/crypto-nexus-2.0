package com.example.cryptocurrency.room_database
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.cryptocurrency.model.Quote
import com.google.gson.Gson

@TypeConverters
class QuoteTypeConverter {
    @TypeConverter
    fun fromJson(value: String): Quote {
        return Gson().fromJson(value, Quote::class.java)
    }

    @TypeConverter
    fun toJson(quote: Quote): String {
        return Gson().toJson(quote)
    }
}