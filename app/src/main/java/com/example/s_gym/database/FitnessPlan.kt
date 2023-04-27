package com.example.s_gym.database

import com.google.gson.annotations.SerializedName

data class FitnessPlan(
    @SerializedName("fitness_plan")
    val fitnessPlan: List<FitnessDay>
)
