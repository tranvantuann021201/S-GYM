package com.example.s_gym.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.dao.FitnessAdvanceDao
import com.example.s_gym.database.dao.FitnessBasicDao
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.entity.FitnessBasic

class FitnessBasicRepository(application: Application) {
    private val fitnessBasicDao: FitnessBasicDao

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
        fitnessBasicDao = appDatabase.fitnessBasicModeDao()
    }

    suspend fun allFitnessBasics(userId: String): LiveData<List<FitnessBasic>> {
        return fitnessBasicDao.getAll(userId)
    }

    suspend fun insert(fitnessBasic: FitnessBasic) {
        fitnessBasicDao.insert(fitnessBasic)
    }

    fun getById(id: Int): LiveData<FitnessBasic> {
        return fitnessBasicDao.getById(id)
    }

    fun getLast(): LiveData<FitnessBasic> {
        return fitnessBasicDao.getLatest()
    }

    suspend fun updateFitnessBasic(fitnessBasic: FitnessBasic) {
        fitnessBasicDao.update(fitnessBasic)
    }

    suspend fun delete(fitnessBasic: FitnessBasic) {
        fitnessBasicDao.delete(fitnessBasic)
    }

    suspend fun deleteAll() {
        fitnessBasicDao.deleteAllFromFitnessBasic()
    }
}
