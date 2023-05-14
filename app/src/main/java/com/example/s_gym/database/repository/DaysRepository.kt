package com.example.s_gym.database.repository

import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.entity.Days

class DaysRepository(
    private val daysDao: DaysDao,
) {
    // Các phương thức liên quan đến bảng days_roomdb_table
    suspend fun updateWeightAndHeight(id: Int, weight: Double, height: Double) {
        daysDao.updateWeightAndHeight(id, weight, height)
    }

    suspend fun updateCompletedExercise(id: Int) {
        daysDao.updateCompletedExercise(id)
    }

    suspend fun updateDrunk(id: Int) {
        daysDao.updateDrunk(id)
    }

    suspend fun insertDay(day: Days) {
        daysDao.insert(day)
    }

    suspend fun updateDay(day: Days) {
        daysDao.update(day)
    }

    suspend fun deleteDay(day: Days) {
        daysDao.delete(day)
    }

    suspend fun getDayById(id: Int): Days {
        return daysDao.getDayById(id)
    }

    suspend fun getAllDays(): List<Days> {
        return daysDao.getAllDays()
    }


}