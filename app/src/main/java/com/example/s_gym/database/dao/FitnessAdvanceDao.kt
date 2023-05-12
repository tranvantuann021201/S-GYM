package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.s_gym.database.entity.FitnessAdvance

@Dao
interface FitnessAdvanceDao {
    suspend fun addFitnessAdvance(fitnessAdvance: FitnessAdvance)

    @Query("SELECT * FROM fitness_advanced_roomdb_table ORDER BY id ASC")
    fun readAllFitnessAdvance(): LiveData<List<FitnessAdvance>>
}