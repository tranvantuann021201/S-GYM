package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.FitnessAdvance

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDay(day: Days)

    @Update
    suspend fun updateDay(day: Days)

    @Delete
    suspend fun deleteDay(day: Days)

    @Query("SELECT * FROM days_roomdb_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastDay(): Days

    @Query("DELETE FROM days_roomdb_table")
    suspend fun deleteAllFromDays()

    @Query("SELECT * FROM days_roomdb_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Days>>
}




