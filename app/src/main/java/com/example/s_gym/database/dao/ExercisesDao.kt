package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.Exercises

@Dao
interface ExercisesDao {
    @Query("SELECT * FROM exercises_roomdb_table ORDER BY id ASC")
    fun readAllExercise(): LiveData<List<Exercises>>
}