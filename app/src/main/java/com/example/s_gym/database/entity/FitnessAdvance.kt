package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "fitness_advanced_roomdb_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
@Parcelize
data class FitnessAdvance(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var exercisesList: @RawValue List<Exercises>,
    var userId: String
) : Parcelable{
    // Hàm tạo không tham số
    constructor() : this(0, "", listOf(), "")
}



