package com.vutranquangminh.todolistwodb2;

public class Task {
    String name;
    String date;
    int duration;
    String description;

    public Task(String name, String date, int duration, String description) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.description = description;
    }
}

