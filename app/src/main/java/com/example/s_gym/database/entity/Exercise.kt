package com.example.s_gym.database.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val kcalCaloriesConsumed: Double,
    @SerializedName("animation_mount")
    val animationMount: Int
) : Parcelable