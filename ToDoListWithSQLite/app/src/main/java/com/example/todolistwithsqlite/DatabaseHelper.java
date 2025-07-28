package com.example.todolistwithsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TaskDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_TASKS = "tasks";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE = "end_date";
    private static final String COLUMN_IS_COMPLETED = "is_completed";

    // Create table SQL query
    private static final String CREATE_TABLE_TASKS = 
        "CREATE TABLE " + TABLE_TASKS + "("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COLUMN_NAME + " TEXT,"
        + COLUMN_DESCRIPTION + " TEXT,"
        + COLUMN_START_DATE + " TEXT,"
        + COLUMN_END_DATE + " TEXT,"
        + COLUMN_IS_COMPLETED + " INTEGER"
        + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Add a new task
    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_START_DATE, dateFormat.format(task.getStartDate()));
        values.put(COLUMN_END_DATE, dateFormat.format(task.getEndDate()));
        values.put(COLUMN_IS_COMPLETED, task.isCompleted() ? 1 : 0);

        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " ORDER BY " + COLUMN_ID + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = cursorToTask(cursor);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    // Get current (incomplete) tasks
    public List<Task> getCurrentTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + 
                           " WHERE " + COLUMN_IS_COMPLETED + " = 0" +
                           " ORDER BY " + COLUMN_ID + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = cursorToTask(cursor);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    // Get completed tasks
    public List<Task> getCompletedTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + 
                           " WHERE " + COLUMN_IS_COMPLETED + " = 1" +
                           " ORDER BY " + COLUMN_ID + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = cursorToTask(cursor);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    // Update a task
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_START_DATE, dateFormat.format(task.getStartDate()));
        values.put(COLUMN_END_DATE, dateFormat.format(task.getEndDate()));
        values.put(COLUMN_IS_COMPLETED, task.isCompleted() ? 1 : 0);

        int result = db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", 
                             new String[]{String.valueOf(task.getId())});
        db.close();
        return result;
    }

    // Delete a task
    public int deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TASKS, COLUMN_ID + " = ?", 
                             new String[]{String.valueOf(task.getId())});
        db.close();
        return result;
    }

    // Update task completion status
    public int updateTaskCompletion(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_COMPLETED, task.isCompleted() ? 1 : 0);

        int result = db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", 
                             new String[]{String.valueOf(task.getId())});
        db.close();
        return result;
    }

    // Helper method to convert cursor to Task object
    private Task cursorToTask(Cursor cursor) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int startDateIndex = cursor.getColumnIndex(COLUMN_START_DATE);
            int endDateIndex = cursor.getColumnIndex(COLUMN_END_DATE);
            int isCompletedIndex = cursor.getColumnIndex(COLUMN_IS_COMPLETED);
            
            if (idIndex == -1 || nameIndex == -1 || descriptionIndex == -1 || 
                startDateIndex == -1 || endDateIndex == -1 || isCompletedIndex == -1) {
                return null;
            }
            
            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            String startDateStr = cursor.getString(startDateIndex);
            String endDateStr = cursor.getString(endDateIndex);
            int isCompleted = cursor.getInt(isCompletedIndex);

            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            Task task = new Task(name, startDate, endDate, description);
            task.setId(id);
            task.setCompleted(isCompleted == 1);

            return task;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 