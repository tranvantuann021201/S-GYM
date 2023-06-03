package com.example.s_gym.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.s_gym.database.entity.Days

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDay(day: Days)

    @Update
    suspend fun updateDay(day: Days)

    @Delete
    suspend fun deleteDay(day: Days)

    @Query("SELECT * FROM days_roomdb_table ORDER BY id DESC LIMIT 1")
    fun getLatestDay(): LiveData<Days>

    @Query("DELETE FROM days_roomdb_table")
    suspend fun deleteAllFromDays()

    @Query("SELECT * FROM days_roomdb_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Days>>

    @Query("UPDATE days_roomdb_table SET weight = :newWeight WHERE id = (SELECT MAX(id) FROM days_roomdb_table)")
    suspend fun updateWeight(newWeight: Double)

    @Query("DELETE FROM days_roomdb_table WHERE id > 158")
    suspend fun deletedDayByIDOption()

    @Query("UPDATE days_roomdb_table SET weight = :newWeight, height = :newHeight WHERE id = (SELECT MAX(id) FROM days_roomdb_table)")
    suspend fun updateBMI(newWeight: Double, newHeight: Double)

    @Query("UPDATE days_roomdb_table SET drunk = :newDrunk WHERE id = (SELECT MAX(id) FROM days_roomdb_table)")
    fun updateDrink(newDrunk: Int)

    @Query("SELECT SUM(completedExercise) FROM days_roomdb_table")
    fun getTotalCompletedExercise(): LiveData<Int>

    @Query("SELECT SUM(kcalConsumed) FROM days_roomdb_table")
    fun getTotalKcalConsumed(): LiveData<Double>


}




