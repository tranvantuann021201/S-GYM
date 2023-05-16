package com.example.s_gym.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.ExercisesDao
import com.example.s_gym.database.entity.Exercises

class ExercisesRepository(application: Application) {
    private val exercisesDao: ExercisesDao
    private val readAllExercisesData: LiveData<List<Exercises>>

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
        exercisesDao = appDatabase.exercisesDao()
        readAllExercisesData = exercisesDao.readAllData()
    }

    suspend fun insertExercise(exercise: Exercises) {
        exercisesDao.insertExercise(exercise)
    }

    suspend fun updateExercise(exercise: Exercises) {
        exercisesDao.updateExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercises) {
        exercisesDao.deleteExercise(exercise)
    }
}