package com.example.s_gym.database

import com.google.gson.annotations.SerializedName

data class FitnessDay(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_rest_day")
    val isRestDay: Boolean,
    @SerializedName("total_exercise")
    val totalExercise: Int,
    @SerializedName("exercise_complete")
    val exerciseComplete: Boolean,
    @SerializedName("exercise")
    val exercise: List<Exercise>
)
