package com.example.s_gym.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FitnessDay(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_day")
    val nameDay: String,
    @SerializedName("is_rest_day")
    val isRestDay: Boolean,
    @SerializedName("total_exercise")
    val totalExercise: Int,
    @SerializedName("exercise_complete")
    val exerciseCompleted: Int,
    @SerializedName("exercise")
    val exercise: List<Exercise>
): Parcelable
