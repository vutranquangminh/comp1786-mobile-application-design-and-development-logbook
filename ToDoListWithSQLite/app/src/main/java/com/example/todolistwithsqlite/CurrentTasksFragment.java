package com.example.todolistwithsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CurrentTasksFragment extends Fragment {
    private List<Task> currentTaskList;
    private SimpleDateFormat dateFormat;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_current_tasks, container, false);
        
        currentTaskList = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            updateTasks(activity.getDatabaseHelper().getCurrentTasks());
        }
    }

    public void updateTasks(List<Task> allTasks) {
        if (allTasks == null) return;
        
        currentTaskList.clear();
        for (Task task : allTasks) {
            if (!task.isCompleted()) {
                currentTaskList.add(task);
            }
        }
        displayTasks();
    }

    private void displayTasks() {
        ViewGroup container = rootView.findViewById(R.id.llCurrentTaskContainer);
        container.removeAllViews();
        
        for (int i = 0; i < currentTaskList.size(); i++) {
            Task task = currentTaskList.get(i);
            View taskView = createTaskView(task, i);
            container.addView(taskView);
        }
    }

    private View createTaskView(Task task, int position) {
        View taskView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, (ViewGroup) rootView.findViewById(R.id.llCurrentTaskContainer), false);
        
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
        
        // Show complete task button and hide uncomplete button
        taskView.findViewById(R.id.btnCompleteTask).setVisibility(View.VISIBLE);
        taskView.findViewById(R.id.btnCompleteTask).setOnClickListener(v -> completeTask(position));
        taskView.findViewById(R.id.btnUncompleteTask).setVisibility(View.GONE);
        
        return taskView;
    }

    private void editTask(int position) {
        Task task = currentTaskList.get(position);
        
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        intent.putExtra("task_name", task.getName());
        intent.putExtra("task_end_date", task.getEndDate().getTime());
        intent.putExtra("task_description", task.getDescription());
        intent.putExtra("task_start_date", task.getStartDate().getTime());
        intent.putExtra("edit_position", position);
        
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).launchEditTask(intent);
        }
    }

    private void deleteTask(int position) {
        new AlertDialog.Builder(requireContext())
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete", (dialog, which) -> {
                Task taskToDelete = currentTaskList.get(position);
                if (getActivity() instanceof MainActivity) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.deleteTask(taskToDelete);
                    // Refresh the current tasks list
                    updateTasks(activity.getDatabaseHelper().getCurrentTasks());
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void completeTask(int position) {
        Task task = currentTaskList.get(position);
        task.setCompleted(true);
        
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            activity.updateTaskCompletion(task);
            // Refresh the current tasks list
            updateTasks(activity.getDatabaseHelper().getCurrentTasks());
        }
    }
} 