package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.FitnessAdvance

@Dao
interface DaysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(day: Days)

    @Update
    suspend fun update(day: Days)

    @Delete
    suspend fun delete(day: Days)

    @Query("SELECT * FROM days_roomdb_table WHERE id = :id")
    suspend fun getDayById(id: Int): Days

    @Query("SELECT * FROM days_roomdb_table")
    suspend fun getAllDays(): List<Days>

    @Query("SELECT * FROM days_roomdb_table ORDER BY id ASC")
    fun readAllDays(): LiveData<List<Days>>

    @Query("UPDATE days_roomdb_table SET weight = :weight, height = :height WHERE id = :id")
    suspend fun updateWeightAndHeight(id: Int, weight: Double, height: Double)

    @Query("UPDATE days_roomdb_table SET completedExercise = completedExercise + 1 WHERE id = :id")
    suspend fun updateCompletedExercise(id: Int)

    @Query("UPDATE days_roomdb_table SET drunk = drunk + 1 WHERE id = :id")
    suspend fun updateDrunk(id: Int)
}