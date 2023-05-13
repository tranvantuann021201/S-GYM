package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.FitnessAdvance

@Dao
interface FitnessAdvanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fitnessAdvance: FitnessAdvance)

    @Update
    suspend fun update(fitnessAdvance: FitnessAdvance)

    @Delete
    suspend fun delete(fitnessAdvance: FitnessAdvance)

    @Query("SELECT * FROM fitness_advanced_roomdb_table WHERE id = :id")
    suspend fun getFitnessAdvanceById(id: Int): FitnessAdvance

    @Query("SELECT * FROM fitness_advanced_roomdb_table")
    suspend fun getAllFitnessAdvances(): List<FitnessAdvance>

    @Query("UPDATE fitness_advanced_roomdb_table SET exerciseComplete = exerciseComplete + 1 WHERE id = :id")
    suspend fun updateExerciseCompleted(id: Int)
}