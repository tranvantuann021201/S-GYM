package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.FitnessAdvance

@Dao
interface DaysDao {
    suspend fun addDays(days: Days)

    @Query("SELECT * FROM days_roomdb_table ORDER BY id ASC")
    fun readAllDays(): LiveData<List<Days>>
}