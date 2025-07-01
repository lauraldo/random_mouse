package com.niolasdev.storage.model

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlin.getValue

class BreedConverter {

    private val json by lazy {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    @TypeConverter
    fun fromBreedList(value: List<BreedDbo>?): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toBreedList(value: String): List<BreedDbo>? {
        return json.decodeFromString<List<BreedDbo>?>(value)

        /*val listType = object : TypeToken<List<BreedDbo>>() {}.type
        return Gson().fromJson(value, listType)*/
    }
}