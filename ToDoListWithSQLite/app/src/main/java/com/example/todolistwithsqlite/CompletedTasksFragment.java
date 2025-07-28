package com.example.todolistwithsqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CompletedTasksFragment extends Fragment {
    private List<Task> completedTaskList;
    private SimpleDateFormat dateFormat;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_completed_tasks, container, false);
        
        completedTaskList = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            updateTasks(activity.getDatabaseHelper().getCompletedTasks());
        }
    }

    public void updateTasks(List<Task> allTasks) {
        if (allTasks == null) return;
        
        completedTaskList.clear();
        for (Task task : allTasks) {
            if (task.isCompleted()) {
                completedTaskList.add(task);
            }
        }
        displayTasks();
    }

    private void displayTasks() {
        ViewGroup container = rootView.findViewById(R.id.llCompletedTaskContainer);
        container.removeAllViews();
        
        for (int i = 0; i < completedTaskList.size(); i++) {
            Task task = completedTaskList.get(i);
            View taskView = createTaskView(task, i);
            container.addView(taskView);
        }
    }

    private View createTaskView(Task task, int position) {
        View taskView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, (ViewGroup) rootView.findViewById(R.id.llCompletedTaskContainer), false);
        
        TextView tvTaskName = taskView.findViewById(R.id.tvTaskName);
        TextView tvTaskStartDuration = taskView.findViewById(R.id.tvTaskStartDuration);
        TextView tvTaskEndDuration = taskView.findViewById(R.id.tvTaskEndDuration);
        TextView tvTaskDescription = taskView.findViewById(R.id.tvTaskDescription);
        
        tvTaskName.setText(task.getName());
        tvTaskStartDuration.setText(dateFormat.format(task.getStartDate()));
        tvTaskEndDuration.setText(dateFormat.format(task.getDeadline()));
        tvTaskDescription.setText(task.getDescription());
        
        // Mark completed tasks with different styling
        tvTaskName.setAlpha(0.6f);
        tvTaskStartDuration.setAlpha(0.6f);
        tvTaskEndDuration.setAlpha(0.6f);
        tvTaskDescription.setAlpha(0.6f);
        
        // Hide edit and delete buttons for completed tasks
        taskView.findViewById(R.id.btnEditTask).setVisibility(View.GONE);
        taskView.findViewById(R.id.btnDeleteTask).setVisibility(View.GONE);
        taskView.findViewById(R.id.btnCompleteTask).setVisibility(View.GONE);
        
        // Show uncomplete task button
        taskView.findViewById(R.id.btnUncompleteTask).setVisibility(View.VISIBLE);
        taskView.findViewById(R.id.btnUncompleteTask).setOnClickListener(v -> uncompleteTask(position));
        
        return taskView;
    }

    private void uncompleteTask(int position) {
        Task task = completedTaskList.get(position);
        task.setCompleted(false);
        
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            activity.updateTaskCompletion(task);
            // Refresh the completed tasks list
            updateTasks(activity.getDatabaseHelper().getCompletedTasks());
        }
    }
} 