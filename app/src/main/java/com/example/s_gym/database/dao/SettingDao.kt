package com.example.s_gym.database.dao

import androidx.room.*
import com.example.s_gym.database.entity.Setting


@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: Setting)

    @Update
    suspend fun update(setting: Setting)

    @Delete
    suspend fun delete(setting: Setting)

    @Query("SELECT * FROM setting_table WHERE userId = :userId")
    fun getSettingsByUserId(userId: Int): List<Setting>

    @Query("SELECT * FROM setting_table LIMIT 1")
    fun getFirstSetting(): Setting?
}
