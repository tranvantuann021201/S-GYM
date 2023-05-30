package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "days_roomdb_table")
@Parcelize
data class Days(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val completedExerciseInBasicMode: Int,
    val completedExercise: Int,
    val drunk: Int,
    val weight: Double,
    val height: Double,
    val kcalConsumed: Double
): Parcelable
