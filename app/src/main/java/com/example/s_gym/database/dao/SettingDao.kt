package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
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
    fun getSettingsByUserId(userId: String): LiveData<Setting>

    @Query("SELECT * FROM setting_table")
    fun getAllSettings(): List<Setting>
}
