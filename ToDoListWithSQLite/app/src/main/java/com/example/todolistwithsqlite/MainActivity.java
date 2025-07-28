package com.example.todolistwithsqlite;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistwithsqlite.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<Task> taskList;
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private TaskPagerAdapter pagerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database
        databaseHelper = new DatabaseHelper(this);
        
        // Load tasks from database
        taskList = databaseHelper.getAllTasks();
        
        setupViewPager();
        setupTabLayout();
        setupAddTaskLauncher();
        setupFloatingActionButton();
    }

    private void setupViewPager() {
        pagerAdapter = new TaskPagerAdapter(this);
        binding.viewPager.setAdapter(pagerAdapter);
    }

    private void setupTabLayout() {
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
            (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText(R.string.current_tasks);
                        break;
                    case 1:
                        tab.setText(R.string.completed_tasks);
                        break;
                }
            }
        ).attach();
    }

    private void setupAddTaskLauncher() {
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
                            // Find the task in the main list and update it
                            Task taskToEdit = findTaskByPosition(editPosition);
                            if (taskToEdit != null) {
                                taskToEdit.setName(taskName);
                                taskToEdit.setStartDate(startDate);
                                taskToEdit.setEndDate(endDate);
                                taskToEdit.setDescription(description);
                                databaseHelper.updateTask(taskToEdit);
                            }
                        } else {
                            Task newTask = new Task(taskName, startDate, endDate, description);
                            long taskId = databaseHelper.addTask(newTask);
                            newTask.setId((int) taskId);
                            taskList.add(newTask);
                        }
                        updateAllFragments();
                    }
                }
            }
        );
    }

    private void setupFloatingActionButton() {
        binding.btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskLauncher.launch(intent);
        });
    }

    private Task findTaskByPosition(int position) {
        // This method needs to be updated to work with the new fragment structure
        // For now, we'll search through all tasks
        if (position >= 0 && position < taskList.size()) {
            return taskList.get(position);
        }
        return null;
    }

    public void launchEditTask(Intent intent) {
        addTaskLauncher.launch(intent);
    }

    public void deleteTask(Task taskToDelete) {
        databaseHelper.deleteTask(taskToDelete);
        taskList.remove(taskToDelete);
        updateAllFragments();
    }

    public void updateTaskCompletion(Task task) {
        // Update the task completion status in database
        databaseHelper.updateTaskCompletion(task);
        updateAllFragments();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    private void updateAllFragments() {
        // Force refresh the current fragment
        int currentItem = binding.viewPager.getCurrentItem();
        binding.viewPager.setCurrentItem(currentItem, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAllFragments();
    }
}