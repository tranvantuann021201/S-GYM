package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.Exercises

@Dao
interface ExercisesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(exercise: Exercises)

    @Update
    suspend fun updateExercise(exercise: Exercises)

    @Delete
    suspend fun deleteExercise(exercise: Exercises)

    @Query("SELECT * FROM exercises_roomdb_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Exercises>>
}
