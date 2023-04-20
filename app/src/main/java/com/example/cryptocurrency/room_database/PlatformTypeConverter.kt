package com.example.cryptocurrency.room_database
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.cryptocurrency.model.Platform
import com.google.gson.Gson

@TypeConverters
class PlatformTypeConverter {
    @TypeConverter
    fun fromJson(json: String?): Platform? {
        return if (json != null) {
            Gson().fromJson(json, Platform::class.java)
        } else {
            null
        }
    }

    @TypeConverter
    fun toJson(platform: Platform?): String? {
        return platform?.let {
            Gson().toJson(it)
        }
    }
}