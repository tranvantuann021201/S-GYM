package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("UPDATE user_roomdb_table SET currentWeight = :weight, currentHeight = :height WHERE gender = :gender")
    suspend fun updateUserCurrentWeightAndHeight(gender: Boolean, weight: Double, height: Double)


    @Query("UPDATE user_roomdb_table SET currentWeight = :weight, currentHeight = :height WHERE id = 1")
    suspend fun updateUserWeightAndHeight(weight: Double, height: Double)

    @Query("SELECT * FROM user_roomdb_table")
    fun readUser(): User

    @Query("SELECT COUNT(*) FROM user_roomdb_table")
    suspend fun getUserCount(): Int

    @Transaction
    suspend fun insertIfEmpty(user: User) {
        if (getUserCount() == 0) {
            insert(user)
        }
    }
}