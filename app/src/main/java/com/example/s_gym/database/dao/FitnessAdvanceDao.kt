package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance

@Dao
interface FitnessAdvanceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFitnessAdvance(fitnessAdvance: FitnessAdvance): Long

    @Update
    suspend fun updateFitnessAdvance(fitnessAdvance: FitnessAdvance)

    @Delete
    suspend fun deleteFitnessAdvance(fitnessAdvance: FitnessAdvance)

    @Query("DELETE FROM fitness_advanced_roomdb_table WHERE name='name'")
    suspend fun deleteFitnessAdvanceWithWhere()

    @Query("SELECT * FROM fitness_advanced_roomdb_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FitnessAdvance>>

    @Transaction
    suspend fun addExerciseToFitnessAdvance(fitnessAdvanceId: Int, exercise: Exercises) {
        val fitnessAdvance = getFitnessAdvanceById(fitnessAdvanceId)
        fitnessAdvance?.let {
            val exercisesList = it.exercisesList.toMutableList()
            exercisesList.add(exercise)
            it.exercisesList = exercisesList
            updateFitnessAdvance(it)
        }
    }

    @Query("SELECT * FROM fitness_advanced_roomdb_table WHERE id = :id")
    suspend fun getFitnessAdvanceById(id: Int): FitnessAdvance?

    @Query("SELECT COUNT(*) FROM fitness_advanced_roomdb_table")
    suspend fun getRowCount(): Int

}
