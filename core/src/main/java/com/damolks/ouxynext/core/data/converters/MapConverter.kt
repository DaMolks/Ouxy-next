package com.damolks.ouxynext.core.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapConverter {
    @TypeConverter
    fun fromString(value: String?): Map<String, Any>? {
        if (value == null) return null
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun toString(map: Map<String, Any>?): String? {
        if (map == null) return null
        return Gson().toJson(map)
    }
}