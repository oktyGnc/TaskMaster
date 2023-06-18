package com.example.taskmaster

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TasksDao {

    @Insert
    suspend fun insertTask(task: Tasks)

    @Query("SELECT * FROM Tasks")
    suspend fun getAllTasks(): List<Tasks>

    @Delete
    suspend fun deleteTask(task: Tasks)

    companion object {
        suspend fun insertTask(database: VeriTabani, task: Tasks) {
            database.getTasksDao().insertTask(task)
        }
    }
}