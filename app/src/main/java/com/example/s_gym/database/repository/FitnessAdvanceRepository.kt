package com.example.s_gym.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.FitnessAdvanceDao
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance

class FitnessAdvanceRepository(application: Application) {

    private val fitnessAdvanceDao: FitnessAdvanceDao
    private val readAllFitnessAdvanceData: LiveData<List<FitnessAdvance>>

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
        fitnessAdvanceDao = appDatabase.fitnessAdvanceDao()
        readAllFitnessAdvanceData = fitnessAdvanceDao.readAllData()
    }

    suspend fun addFitnessAdvance(fitnessAdvance: FitnessAdvance): Long {
        return fitnessAdvanceDao.addFitnessAdvance(fitnessAdvance)
    }


    suspend fun updateFitnessAdvance(fitnessAdvance: FitnessAdvance) {
        fitnessAdvanceDao.updateFitnessAdvance(fitnessAdvance)
    }

    suspend fun deleteFitnessAdvance(fitnessAdvance: FitnessAdvance) {
        fitnessAdvanceDao.deleteFitnessAdvance(fitnessAdvance)
    }

    suspend fun addExerciseToFitnessAdvance(fitnessAdvanceId: Int, exercise: Exercises) {
        fitnessAdvanceDao.addExerciseToFitnessAdvance(fitnessAdvanceId, exercise)
    }

    suspend fun updateFitnessAdvanceName(id: Int, newName: String) {
        fitnessAdvanceDao.updateNameExercisesById(id, newName)
    }

    suspend fun getRowCount(): Int {
        return fitnessAdvanceDao.getRowCount()
    }

    fun readAllData(): LiveData<List<FitnessAdvance>> {
        return fitnessAdvanceDao.readAllData()
    }

    suspend fun deleteEmptyFitnessAdvance() {
        return fitnessAdvanceDao.deleteFitnessAdvanceWithWhere()
    }
}
