package com.niolasdev.storage.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BreedConverter {
    @TypeConverter
    fun fromBreedList(value: List<BreedDbo>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toBreedList(value: String): List<BreedDbo>? {
        val listType = object : TypeToken<List<BreedDbo>>() {}.type
        return Gson().fromJson(value, listType)
    }
}