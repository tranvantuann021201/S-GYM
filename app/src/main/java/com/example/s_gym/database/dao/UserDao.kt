package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_roomdb_table")
    fun readUser(): User
}