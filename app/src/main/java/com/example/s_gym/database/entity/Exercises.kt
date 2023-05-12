package com.example.s_gym.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises_roomdb_table")
data class Exercises(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val urlVideoGuide: String,
    val isComplete: Boolean,
    val kcalCaloriesConsumed: Double,
    val animationMount: Int
)
