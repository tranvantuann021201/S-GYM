package com.example.s_gym.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fitness_advanced_roomdb_table")
data class FitnessAdvance(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var isRestDay: Boolean,
    var exerciseCompleted: Int,
    var exercisesList: List<Exercises>
)
