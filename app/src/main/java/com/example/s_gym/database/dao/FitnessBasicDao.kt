package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.FitnessBasic

@Dao
interface FitnessBasicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fitnessBasic: FitnessBasic)

    @Query("SELECT * FROM fitness_basic_table")
    fun getAll(): LiveData<List<FitnessBasic>>

    @Query("SELECT * FROM fitness_basic_table WHERE id = :id")
    fun getById(id: Int): LiveData<FitnessBasic>

    @Query("SELECT * FROM fitness_basic_table ORDER BY id DESC LIMIT 1")
    fun getLatest(): LiveData<FitnessBasic>

    @Query("DELETE FROM fitness_basic_table")
    suspend fun deleteAllFromFitnessBasic()

    @Delete
    suspend fun delete(fitnessBasicMode: FitnessBasic)
}