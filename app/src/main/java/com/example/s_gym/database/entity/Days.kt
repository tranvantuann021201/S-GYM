package com.example.s_gym.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days_roomdb_table")
data class Days(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val completedExercise: Int,
    val drunk: Int,
    val weight: Double,
    val height: Double
)
