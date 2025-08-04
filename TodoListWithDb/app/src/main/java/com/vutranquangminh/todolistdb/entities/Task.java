package com.vutranquangminh.todolistdb.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {

    public Task() {
    }

    public Task(String description, String name, String dateTime, int duration, boolean isCompleted) {
        Id = 0;
        Name = name;
        DateTime = dateTime;
        Duration = duration;
        IsCompleted = isCompleted;
        Description = description;
    }
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public String DateTime;
    public int Duration;
    public boolean IsCompleted;
    public String Description;
}
