package com.example.s_gym.database

import com.google.gson.annotations.SerializedName

data class Exercise(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("url_video_guide")
    val urlVideoGuide: String,
    @SerializedName("is_complete")
    val isComplete: Boolean,
    @SerializedName("kcal_calories_consumed")
    val kcalCaloriesConsumed: Double
)