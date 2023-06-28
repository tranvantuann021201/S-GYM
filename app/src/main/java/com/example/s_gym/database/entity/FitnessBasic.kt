package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(
    tableName = "fitness_basic_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class FitnessBasic(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nameDay: String,
    val isRestDay: Boolean,
    val totalExercise: Int,
    var exerciseCompleted: Int,
    val exercise: @RawValue List<Exercises>,
    val userId: String
) : Parcelable{
    // Hàm tạo không tham số
    constructor() : this(0, "", false, 0, 0, listOf(), "")
}


