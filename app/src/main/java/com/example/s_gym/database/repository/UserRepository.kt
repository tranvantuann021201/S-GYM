package com.example.s_gym.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.UserDao
import com.example.s_gym.database.entity.User

class UserRepository(application: Application) {
    private val userDao: UserDao
    private val readAllUserData: LiveData<List<User>>
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
        userDao = appDatabase.userDao()
        readAllUserData  = userDao.readAllData()
    }
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}