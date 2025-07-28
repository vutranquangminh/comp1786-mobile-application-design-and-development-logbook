package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistapp.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<Task> taskList;
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskList = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        
        addTaskLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String taskName = result.getData().getStringExtra("task_name");
                    long startDateMillis = result.getData().getLongExtra("task_start_date", 0);
                    long endDateMillis = result.getData().getLongExtra("task_end_date", 0);
                    String description = result.getData().getStringExtra("task_description");
                    if (description == null) {
                        description = "";
                    }
                    
                    if (taskName != null && !taskName.isEmpty()) {
                        int editPosition = result.getData().getIntExtra("edit_position", -1);
                        Date startDate = new Date(startDateMillis);
                        Date endDate = new Date(endDateMillis);
                        
                        if (editPosition != -1) {
                            Task task = taskList.get(editPosition);
                            task.setName(taskName);
                            task.setStartDate(startDate);
                            task.setEndDate(endDate);
                            task.setDescription(description);
                        } else {
                            Task newTask = new Task(taskName, startDate, endDate, description);
                            taskList.add(newTask);
                        }
                        displayTasks();
                    }
                }
            }
        );

        binding.btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskLauncher.launch(intent);
        });
    }

    private void displayTasks() {
        binding.llTaskContainer.removeAllViews();
        
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            View taskView = createTaskView(task, i);
            binding.llTaskContainer.addView(taskView);
        }
    }

    private View createTaskView(Task task, int position) {
        View taskView = LayoutInflater.from(this).inflate(R.layout.task_item, binding.llTaskContainer, false);
        
        TextView tvTaskName = taskView.findViewById(R.id.tvTaskName);
        TextView tvTaskStartDuration = taskView.findViewById(R.id.tvTaskStartDuration);
        TextView tvTaskEndDuration = taskView.findViewById(R.id.tvTaskEndDuration);
        TextView tvTaskDescription = taskView.findViewById(R.id.tvTaskDescription);
        
        tvTaskName.setText(task.getName());
        tvTaskStartDuration.setText(dateFormat.format(task.getStartDate()));
        tvTaskEndDuration.setText(dateFormat.format(task.getDeadline()));
        tvTaskDescription.setText(task.getDescription());
        
        taskView.findViewById(R.id.btnEditTask).setOnClickListener(v -> editTask(position));
        taskView.findViewById(R.id.btnDeleteTask).setOnClickListener(v -> deleteTask(position));
        
        return taskView;
    }

    private void editTask(int position) {
        Task task = taskList.get(position);
        
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra("task_name", task.getName());
        intent.putExtra("task_end_date", task.getEndDate().getTime());
        intent.putExtra("task_description", task.getDescription());
        intent.putExtra("task_start_date", task.getStartDate().getTime());
        intent.putExtra("edit_position", position);
        
        addTaskLauncher.launch(intent);
    }

    private void deleteTask(int position) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete", (dialog, which) -> {
                taskList.remove(position);
                displayTasks();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
}