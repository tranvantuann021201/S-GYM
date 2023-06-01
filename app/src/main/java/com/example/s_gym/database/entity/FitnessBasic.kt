package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "fitness_basic_table")
@Parcelize
data class FitnessBasic(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nameDay: String,
    val isRestDay: Boolean,
    val totalExercise: Int,
    val exerciseCompleted: Int,
    val exercise: @RawValue List<Exercises>
): Parcelable

