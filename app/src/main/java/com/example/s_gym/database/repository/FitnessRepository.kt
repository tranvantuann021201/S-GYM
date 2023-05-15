package com.example.s_gym.database.repository

import android.app.Application
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.FitnessAdvanceDao
import com.example.s_gym.database.entity.FitnessAdvance

class FitnessRepository(application: Application) {
    private val fitnessAdvanceDao: FitnessAdvanceDao
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
        fitnessAdvanceDao = appDatabase.fitnessAdvanceDao()
    }

    // Các phương thức liên quan đến bảng fitness_advanced_roomdb_table
    suspend fun updateExerciseCompleted(id: Int) {
        fitnessAdvanceDao.updateExerciseCompleted(id)
    }

    suspend fun insertFitnessAdvance(fitnessAdvance: FitnessAdvance) {
        fitnessAdvanceDao.insert(fitnessAdvance)
    }

    suspend fun updateFitnessAdvance(fitnessAdvance: FitnessAdvance) {
        fitnessAdvanceDao.update(fitnessAdvance)
    }

    suspend fun deleteFitnessAdvance(fitnessAdvance: FitnessAdvance) {
        fitnessAdvanceDao.delete(fitnessAdvance)
    }

    suspend fun getFitnessAdvanceById(id: Int): FitnessAdvance {
        return fitnessAdvanceDao.getFitnessAdvanceById(id)
    }

    suspend fun getAllFitnessAdvances(): List<FitnessAdvance> {
        return fitnessAdvanceDao.getAllFitnessAdvances()
    }

    suspend fun getFitnessAdvance(): FitnessAdvance {
        return fitnessAdvanceDao.getFitnessAdvance()
    }
}