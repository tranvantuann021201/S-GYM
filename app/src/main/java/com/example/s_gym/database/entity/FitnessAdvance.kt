package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "fitness_advanced_roomdb_table")
@Parcelize
data class FitnessAdvance(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var isRestDay: Boolean,
    var exerciseCompleted: Int,
    var exercisesList: @RawValue List<Exercises>
) : Parcelable


