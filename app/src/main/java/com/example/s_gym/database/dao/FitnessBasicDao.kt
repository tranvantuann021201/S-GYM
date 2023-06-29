package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.FitnessBasic

@Dao
interface FitnessBasicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fitnessBasic: FitnessBasic)

    @Update
    suspend fun update(fitnessBasic: FitnessBasic)

    @Query("SELECT * FROM fitness_basic_table WHERE userId = :userId")
    fun getAll(userId: String): LiveData<List<FitnessBasic>>

    @Query("SELECT * FROM fitness_basic_table WHERE id = :id")
    fun getById(id: Int): LiveData<FitnessBasic>

    @Query("SELECT * FROM fitness_basic_table ORDER BY id DESC LIMIT 1")
    fun getLatest(): LiveData<FitnessBasic>

    @Query("DELETE FROM fitness_basic_table")
    suspend fun deleteAllFromFitnessBasic()

    @Query("DELETE FROM fitness_basic_table WHERE id > 31")
    suspend fun fitnessBasicTrim()
    @Delete
    suspend fun delete(fitnessBasicMode: FitnessBasic)
}