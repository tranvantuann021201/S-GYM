package com.example.s_gym.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fitness_advanced_roomdb_table")
data class FitnessAdvance(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val isRestDay: Boolean,
    val exerciseCompleted: Int,
    val exercise: List<Exercises>
)
