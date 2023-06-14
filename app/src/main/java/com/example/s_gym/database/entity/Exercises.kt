package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "exercises_roomdb_table")
@Parcelize
data class Exercises(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val urlVideoGuide: String = "",
    @get:PropertyName("complete") @set:PropertyName("complete") var isComplete: Boolean = true,
    val kcalCaloriesConsumed: Double = 0.0,
    val animationMount: Int = 0
): Parcelable
