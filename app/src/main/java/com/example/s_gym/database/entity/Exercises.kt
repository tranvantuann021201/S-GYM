package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "exercises_roomdb_table")
@Parcelize
data class Exercises(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val urlVideoGuide: String,
    val isComplete: Boolean,
    val kcalCaloriesConsumed: Double,
    val animationMount: Int
): Parcelable
