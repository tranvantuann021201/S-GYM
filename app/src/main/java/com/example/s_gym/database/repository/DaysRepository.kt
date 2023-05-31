package com.example.s_gym.database.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.FitnessAdvance
import java.text.SimpleDateFormat
import java.util.*

class DaysRepository(context: Context) {
    private val daysDao: DaysDao
    private val readAllDaysData: LiveData<List<Days>>
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        daysDao = appDatabase.daysDao()
        readAllDaysData = daysDao.readAllData()
    }

    fun getAllDays(): LiveData<List<Days>> {
        return daysDao.readAllData()
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

    suspend fun deleteAllFromDays() {
        daysDao.deleteAllFromDays()
    }

    suspend fun updateWeight(newWeight: Double) {
        daysDao.updateWeight(newWeight)
    }

    suspend fun updateBMI(newWeight: Double, newHeight: Double) {
        daysDao.updateBMI(newWeight, newHeight)
    }

    suspend fun getLastDay(): Days {
        return daysDao.getLastDay()
    }

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
                height = 170.0,
                kcalConsumed = 0.0
            )
            daysDao.insertDay(newDay)
        }
        else {
            val newDay = Days(
                id = 0,
                name = currentDate,
                completedExerciseInBasicMode = 0,
                completedExercise = 0,
                drunk = 0,
                weight = lastDay.weight,
                height = lastDay.height,
                kcalConsumed = 0.0
            )
            daysDao.insertDay(newDay)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DaysRepository? = null

        fun getInstance(context: Context): DaysRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DaysRepository(context).also { INSTANCE = it }
            }
        }
    }
}