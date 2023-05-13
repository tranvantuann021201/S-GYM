package com.example.s_gym.database.entity

import android.os.Parcelable
import com.example.s_gym.api.Exercise
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
    val exerciseComplete: Int,
    @SerializedName("exercise")
    val exercise: List<Exercise>
): Parcelable
