package com.example.s_gym.database.repository

import com.example.s_gym.database.dao.UserDao
import com.example.s_gym.database.entity.User

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun updateUserCurrentWeightAndHeight(gender: Boolean,weight: Double, height: Double) {
        userDao.updateUserCurrentWeightAndHeight(gender, weight, height)
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