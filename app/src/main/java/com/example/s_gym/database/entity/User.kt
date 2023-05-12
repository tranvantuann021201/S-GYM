package com.example.s_gym.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_roomdb_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val gender: Boolean,
    val birthDays: Days,
    val originalWeight: Double,
    val originalHeight: Double,
    val currentWeight: Double,
    val currentHeight: Double
)
