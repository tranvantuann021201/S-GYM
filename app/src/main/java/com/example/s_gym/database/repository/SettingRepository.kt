package com.example.s_gym.database.repository

import android.content.Context
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.dao.SettingDao
import com.example.s_gym.database.entity.Setting

class SettingRepository(context: Context) {
    private val settingDao: SettingDao
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        settingDao = appDatabase.settingDao()
    }

    suspend fun insert(setting: Setting) {
        settingDao.insert(setting)
    }

    suspend fun update(setting: Setting) {
        settingDao.update(setting)
    }

    suspend fun delete(setting: Setting) {
        settingDao.delete(setting)
    }

    fun getSettingsByUserId(userId: Int): List<Setting> {
        return settingDao.getSettingsByUserId(userId)
    }

    fun getFirstSetting(): Setting? {
        return settingDao.getFirstSetting()
    }
}