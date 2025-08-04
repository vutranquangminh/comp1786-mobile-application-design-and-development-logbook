package com.vutranquangminh.todolistdb.ui.completes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vutranquangminh.todolistdb.R;
import com.vutranquangminh.todolistdb.entities.Task;

import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.TaskViewHolder> {
    private final List<Task> _tasks;

    public CompletedTaskAdapter(List<Task> tasks) {
        _tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_tasks_completed_recycle_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task item = _tasks.get(position);
        holder.taskName.setText(item.Name);
        holder.taskDueDate.setText(item.DateTime != null ? item.DateTime : "No Due Date");
        holder.taskStatus.setText("Completed");
        holder.taskDescription.setText(item.Description != null ? item.Description : "No Description");
    }

    @Override
    public int getItemCount() {
        return _tasks == null ? 0 : _tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDueDate, taskStatus, taskDescription;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.textTaskCompeletedName);
            taskDueDate = itemView.findViewById(R.id.textDateStart);
            taskStatus = itemView.findViewById(R.id.txtDisplayStatus);
            taskDescription = itemView.findViewById(R.id.textTaskCompeletedDescription);
        }
    }
}