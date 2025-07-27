package com.example.todolistapp;

import java.util.Calendar;
import java.util.Date;

public class Task {
    private String name;
    private Date startDate;
    private int duration;
    private String description;

    public Task(String name, Date startDate, int duration, String description) {
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_MONTH, duration);
        return cal.getTime();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} 