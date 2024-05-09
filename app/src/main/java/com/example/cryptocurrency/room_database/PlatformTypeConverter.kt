package com.example.cryptocurrency.room_database
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.cryptocurrency.domain.model.CryptoPlatform
import com.google.gson.Gson

@TypeConverters
class PlatformTypeConverter {
    @TypeConverter
    fun fromJson(json: String?): CryptoPlatform? {
        return if (json != null) {
            Gson().fromJson(json, CryptoPlatform::class.java)
        } else {
            null
        }
    }

    @TypeConverter
    fun toJson(platform: CryptoPlatform?): String? {
        return platform?.let {
            Gson().toJson(it)
        }
    }
}