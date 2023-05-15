package com.example.s_gym.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.dao.ExercisesDao
import com.example.s_gym.database.dao.FitnessAdvanceDao
import com.example.s_gym.database.dao.UserDao
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.entity.User

@Database(
    entities = [Exercises::class, Days::class, User::class, FitnessAdvance::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exercisesDao(): ExercisesDao
    abstract fun daysDao(): DaysDao
    abstract fun userDao(): UserDao
    abstract fun fitnessAdvanceDao(): FitnessAdvanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(application: Application): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
