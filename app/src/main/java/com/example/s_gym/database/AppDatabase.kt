package com.example.s_gym.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.s_gym.database.dao.DaysDao
import com.example.s_gym.database.dao.ExercisesDao
import com.example.s_gym.database.dao.FitnessAdvanceDao
import com.example.s_gym.database.dao.UserDao
import com.example.s_gym.database.entity.*
import com.example.s_gym.database.dao.FitnessBasicDao

@Database(
    entities = [Exercises::class, Days::class, User::class, FitnessAdvance::class, FitnessBasic::class],
    version = 7,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exercisesDao(): ExercisesDao
    abstract fun daysDao(): DaysDao
    abstract fun userDao(): UserDao
    abstract fun fitnessAdvanceDao(): FitnessAdvanceDao
    abstract fun fitnessBasicModeDao(): FitnessBasicDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val migration2to3 = object : Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE days_roomdb_table ADD COLUMN kcalConsumed DOUBLE NOT NULL DEFAULT 0")
                    }
                }

                val migration3to4 = object : Migration(3, 4) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("CREATE TABLE IF NOT EXISTS `fitness_basic_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nameDay` TEXT NOT NULL, `isRestDay` INTEGER NOT NULL, `totalExercise` INTEGER NOT NULL, `exerciseCompleted` INTEGER NOT NULL, `exercise` TEXT NOT NULL)")
                    }
                }

                val migration4to5 = object : Migration(4, 5) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE days_roomdb_table ADD COLUMN currentBMI DOUBLE NOT NULL DEFAULT 0")
                    }
                }

                val migration5to6 = object : Migration(5, 6) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Remove old table
                        database.execSQL("DROP TABLE IF EXISTS user_roomdb_table")

                        // Create new table with desired schema
                        database.execSQL("CREATE TABLE user_roomdb_table_temp (id TEXT PRIMARY KEY NOT NULL, gender INTEGER NOT NULL, birthDays TEXT NOT NULL, originalWeight REAL NOT NULL, originalHeight REAL NOT NULL, currentWeight REAL NOT NULL, currentHeight REAL NOT NULL)")

                        // Rename the new table to the correct name
                        database.execSQL("ALTER TABLE user_roomdb_table_temp RENAME TO user_roomdb_table")
                    }
                }

                val migration6to7 = object : Migration(6, 7) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Add new columns to the user table
                        database.execSQL("ALTER TABLE user_roomdb_table ADD COLUMN name TEXT")
                        database.execSQL("ALTER TABLE user_roomdb_table ADD COLUMN photoUrl TEXT")
                    }
                }

                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).addMigrations(migration2to3, migration3to4, migration4to5, migration5to6, migration6to7).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
