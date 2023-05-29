package com.example.s_gym.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.entity.Days
import java.text.SimpleDateFormat
import java.util.*

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

    //TODO: auto generate new Days object with WorkManager
    suspend fun addNewDay() {
        val lastDay = daysDao.getLastDay()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        if (lastDay == null) {
            val newDay = Days(
                id = 0,
                name = currentDate,
                completedExerciseInBasicMode = 0,
                completedExercise = 0,
                drunk = 0,
                weight = 50.0,
                height = 170.0
            )
            daysDao.insertDay(newDay)
        }
        else {
            val newDay = Days(
                id = 0,
                name = lastDay.name,
                completedExerciseInBasicMode = lastDay.completedExerciseInBasicMode,
                completedExercise = lastDay.completedExercise,
                drunk = lastDay.drunk,
                weight = lastDay.weight,
                height = lastDay.height
            )
            daysDao.insertDay(newDay)
        }
    }
}