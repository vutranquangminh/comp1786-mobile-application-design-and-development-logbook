package com.vutranquangminh.todolistdb.ui.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vutranquangminh.todolistdb.R;
import com.vutranquangminh.todolistdb.data.AppDatabase;
import com.vutranquangminh.todolistdb.entities.Task;

import java.util.List;


public class ViewFragment extends Fragment implements com.vutranquangminh.todolistdb.ui.completes.CompletedTaskAdapter.OnTaskDeleteListener {
    private com.vutranquangminh.todolistdb.ui.completes.CompletedTaskAdapter adapter;
    private List<Task> completedTasks;

    public ViewFragment() {
        super(R.layout.fragment_task_completed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCompletedTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCompletedTasks(recyclerView);
    }

    private void loadCompletedTasks(RecyclerView recyclerView) {
        new Thread(() -> {
            completedTasks = AppDatabase.getInstance(getContext())
                    .taskDao()
                    .getCompletedTasks();
            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    adapter = new com.vutranquangminh.todolistdb.ui.completes.CompletedTaskAdapter(completedTasks, this);
                    recyclerView.setAdapter(adapter);
                });
            }
        }).start();
    }

    @Override
    public void onTaskDeleted(Task task) {
        new Thread(() -> {
            AppDatabase.getInstance(getContext())
                    .taskDao()
                    .deleteTask(task);
            
            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    completedTasks.remove(task);
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }
}
