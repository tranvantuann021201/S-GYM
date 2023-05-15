package com.example.s_gym.database

import androidx.room.TypeConverter
import com.example.s_gym.database.entity.Exercises
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromExercisesList(value: List<Exercises>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toExercisesList(value: String): List<Exercises> {
        val gson = Gson()
        val type = object : TypeToken<List<Exercises>>() {}.type
        return gson.fromJson(value, type)
    }
}
