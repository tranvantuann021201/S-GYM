package com.example.s_gym.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.s_gym.database.dao.*
import com.example.s_gym.database.entity.*

@Database(
    entities = [Exercises::class, Days::class, User::class, FitnessAdvance::class, FitnessBasic::class, Setting::class],
    version = 11,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exercisesDao(): ExercisesDao
    abstract fun daysDao(): DaysDao
    abstract fun userDao(): UserDao
    abstract fun fitnessAdvanceDao(): FitnessAdvanceDao
    abstract fun fitnessBasicModeDao(): FitnessBasicDao

    abstract fun settingDao(): SettingDao


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

                val migration7to8 = object : Migration(7, 8) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("CREATE TABLE IF NOT EXISTS setting_table (id INTEGER PRIMARY KEY NOT NULL, userId TEXT NOT NULL, restTime INTEGER NOT NULL, drinkMind INTEGER NOT NULL, fitnessMind INTEGER NOT NULL, fitnessMindTime TEXT NOT NULL, FOREIGN KEY(userId) REFERENCES user_roomdb_table(id) ON DELETE CASCADE)")
                    }
                }

                val migration8to9 = object : Migration(8, 9) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Xóa bảng cũ
                        database.execSQL("DROP TABLE setting_table")

                        // Tạo một bảng mới với cấu trúc mong muốn
                        database.execSQL("CREATE TABLE IF NOT EXISTS setting_table (id INTEGER PRIMARY KEY NOT NULL, userId TEXT NOT NULL, restTime INTEGER NOT NULL, drinkMind INTEGER NOT NULL, fitnessMind INTEGER NOT NULL, fitnessMindTime TEXT NOT NULL, FOREIGN KEY(userId) REFERENCES user_roomdb_table(id) ON DELETE CASCADE)")

                        // Thêm chỉ mục mới
                        database.execSQL("CREATE UNIQUE INDEX index_setting_table_userId ON setting_table(userId)")
                    }
                }

                val migration9to10 = object : Migration(9, 10) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Tạo bảng mới với cột userId
                        database.execSQL(
                            "CREATE TABLE new_fitness_basic_table (id INTEGER PRIMARY KEY NOT NULL, nameDay TEXT, isRestDay INTEGER NOT NULL, totalExercise INTEGER NOT NULL, exerciseCompleted INTEGER NOT NULL, exercise TEXT, userId TEXT NOT NULL, FOREIGN KEY(userId) REFERENCES user_roomdb_table(id) ON DELETE CASCADE)"
                        )
                        // Xóa bảng cũ
                        database.execSQL("DROP TABLE fitness_basic_table")
                        // Đổi tên bảng mới
                        database.execSQL("ALTER TABLE new_fitness_basic_table RENAME TO fitness_basic_table")
                    }
                }

                val migration10to11 = object : Migration(10, 11) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Tạo bảng mới với cột userId
                        database.execSQL(
                            "CREATE TABLE new_fitness_basic_table (id INTEGER PRIMARY KEY NOT NULL, nameDay TEXT NOT NULL, isRestDay INTEGER NOT NULL, totalExercise INTEGER NOT NULL, exerciseCompleted INTEGER NOT NULL, exercise TEXT NOT NULL, userId TEXT NOT NULL, FOREIGN KEY(userId) REFERENCES user_roomdb_table(id) ON DELETE CASCADE)"
                        )
                        // Xóa bảng cũ
                        database.execSQL("DROP TABLE fitness_basic_table")
                        // Đổi tên bảng mới
                        database.execSQL("ALTER TABLE new_fitness_basic_table RENAME TO fitness_basic_table")
                    }
                }

                    val instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).addMigrations(
                        migration2to3, migration3to4, migration4to5, migration5to6,
                        migration6to7, migration7to8, migration8to9, migration9to10, migration10to11
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
