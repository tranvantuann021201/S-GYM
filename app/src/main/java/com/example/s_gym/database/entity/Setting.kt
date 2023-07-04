package com.example.s_gym.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Entity(tableName = "setting_table",
    indices = [Index(value = ["userId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
@Parcelize
data class Setting(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: String,
    val restTime: Int,
    var drinkMind: Boolean,
    var fitnessMind: Boolean,
    var fitnessMindTime: String
): Parcelable{
    constructor(): this(0,"default",0,false,false,"")
}
