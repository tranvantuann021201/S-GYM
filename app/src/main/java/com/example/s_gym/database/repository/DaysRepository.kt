package com.example.s_gym.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.entity.Days

class DaysRepository(application: Application) {
    private val daysDao: DaysDao
    private val readAllDaysData: LiveData<List<Days>>
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
        daysDao = appDatabase.daysDao()
        readAllDaysData = daysDao.readAllData()
    }
    suspend fun insertDay(day: Days) {
        daysDao.insertDay(day)
    }

    suspend fun updateDay(day: Days) {
        daysDao.updateDay(day)
    }

    suspend fun deleteDay(day: Days) {
        daysDao.deleteDay(day)
    }
}