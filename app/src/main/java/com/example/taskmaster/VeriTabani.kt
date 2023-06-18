package com.example.taskmaster

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Tasks::class], version = 1)
abstract class VeriTabani : RoomDatabase() {
    abstract fun getTasksDao(): TasksDao

    companion object {
        private const val DATABASE_NAME = "TaskMaster.sqlite"

        @Volatile
        private var instance: VeriTabani? = null

        fun getInstance(context: Context): VeriTabani {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    VeriTabani::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2) // Eski ve yeni şema arasındaki geçiş için gerekli
                    .build()
                instance = database
                database
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Tasks ADD COLUMN new_column TEXT")

            }
        }
    }
}