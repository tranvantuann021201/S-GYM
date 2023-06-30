package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "days_roomdb_table",
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
data class Days(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var completedExerciseInBasicMode: Int,
    var completedExercise: Int,
    var drunk: Int,
    var weight: Double,
    var height: Double,
    var kcalConsumed: Double,
    var currentBMI: Double,
    var userId: String // thêm trường mới
): Parcelable{
    // Hàm tạo không tham số
    constructor(): this(0,"",0,0,0,0.0,0.0,0.0,0.0,"default")

}

