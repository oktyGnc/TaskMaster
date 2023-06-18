package com.example.taskmaster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Tasks")

data class Tasks(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id") @NotNull var task_id: Int,
    @ColumnInfo(name = "title") @NotNull var title: String,
    @ColumnInfo(name = "description") @NotNull var description: String,
    @ColumnInfo(name = "date") @NotNull var date: String,
    @ColumnInfo(name = "katagori") @NotNull var katagori: String,
)