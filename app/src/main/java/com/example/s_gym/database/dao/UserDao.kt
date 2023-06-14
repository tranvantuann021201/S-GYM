package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_roomdb_table WHERE id = :id")
    suspend fun getUserById(id: String): User?

    @Query("SELECT * FROM user_roomdb_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>
}