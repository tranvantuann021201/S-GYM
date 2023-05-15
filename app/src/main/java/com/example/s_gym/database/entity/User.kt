package com.example.s_gym.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "user_roomdb_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val gender: Boolean,
    val birthDays: String,
    val originalWeight: Double,
    val originalHeight: Double,
    val currentWeight: Double,
    val currentHeight: Double
)
