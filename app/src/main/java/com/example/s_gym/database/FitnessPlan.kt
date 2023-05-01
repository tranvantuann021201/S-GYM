package com.example.s_gym.database

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FitnessPlan(
    @SerializedName("fitness_plan")
    val fitnessPlan: List<FitnessDay>
): Parcelable
