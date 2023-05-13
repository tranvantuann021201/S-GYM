package com.example.s_gym.database.repository

import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.dao.ExercisesDao
import com.example.s_gym.database.dao.FitnessAdvanceDao
import com.example.s_gym.database.dao.UserDao
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.entity.User

class FitnessRepository(
    private val daysDao: DaysDao,
    private val exercisesDao: ExercisesDao,
    private val fitnessAdvanceDao: FitnessAdvanceDao,
    private val userDao: UserDao
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

    // Các phương thức liên quan đến bảng user_roomdb_table
    suspend fun updateUserCurrentWeightAndHeight(weight: Double, height: Double) {
        userDao.updateUserCurrentWeightAndHeight(weight, height)
    }

    suspend fun insertUser(user: User) {
        userDao.insertIfEmpty(user)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }
}