package com.vutranquangminh.todolistdb.ui.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vutranquangminh.todolistdb.R;
import com.vutranquangminh.todolistdb.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> _tasks;
    private final OnTaskCompleteListener listener;

    public interface OnTaskCompleteListener {
        void onTaskCompleted(Task task);
    }

    public void updateTasks(List<Task> tasks) {
        _tasks.clear();
        _tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    // Constructor to initialize the adapter with a list of Task objects
    public TaskAdapter(List<Task> tasks, OnTaskCompleteListener listener) {
        _tasks = new ArrayList<>(tasks);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_tasks_recycle_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Bind data to the ViewHolder
        Task item = _tasks.get(position);
        holder.taskName.setText(item.Name);
        holder.taskDescription.setText(item.Description);
        holder.taskDueDate.setText(item.DateTime != null ? item.DateTime.toString() : "No Due Date");
        holder.taskStatus.setText(item.IsCompleted ? "Completed" : "Not Completed");
        holder.btnComplete.setOnClickListener(v -> {
            if (listener != null) listener.onTaskCompleted(item);
        });
        holder.btnComplete.setVisibility(item.IsCompleted ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return _tasks == null ? 0 : _tasks.size(); // Replace with actual item count
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        View btnComplete;
        // Define your ViewHolder views here
        TextView taskName,taskDescription, taskDueDate, taskStatus;

        public TaskViewHolder(View itemView) {
            super(itemView);
            // Initialize your views here
            taskName = itemView.findViewById(R.id.textTaskName);
            taskDescription = itemView.findViewById(R.id.txtDescription);
            taskDueDate = itemView.findViewById(R.id.textDateStart);
            taskStatus = itemView.findViewById(R.id.txtDisplayStatus);
            btnComplete = itemView.findViewById(R.id.btnCompleteTask);
        }
    }
}
