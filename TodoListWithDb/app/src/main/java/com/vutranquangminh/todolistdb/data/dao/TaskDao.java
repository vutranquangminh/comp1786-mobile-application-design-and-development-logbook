package com.vutranquangminh.todolistdb.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.vutranquangminh.todolistdb.entities.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    long insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Transaction
    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Transaction
    @Query("SELECT * FROM tasks WHERE IsCompleted = 1")
    List<Task> getCompletedTasks();

    @Transaction
    @Query("SELECT * FROM tasks WHERE IsCompleted = 0")
    List<Task> getUnCompletedTasks();

    @Query("UPDATE tasks SET IsCompleted = 1 WHERE Id = :taskId")
    void markTaskCompleted(int taskId);

}
