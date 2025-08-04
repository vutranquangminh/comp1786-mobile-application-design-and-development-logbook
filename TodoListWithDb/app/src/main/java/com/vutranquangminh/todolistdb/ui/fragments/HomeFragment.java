package com.vutranquangminh.todolistdb.ui.fragments;

import android.content.Intent;
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
import com.vutranquangminh.todolistdb.ui.tasks.CreateTaskActivity;
import com.vutranquangminh.todolistdb.ui.tasks.TaskAdapter;

import java.util.List;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_task);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Load courses with teachers from database (off main thread)
        new Thread(() -> {
            List<Task> tasks = AppDatabase.getInstance(getContext())
                    .taskDao()
                    .getUnCompletedTasks();
            requireActivity().runOnUiThread(() -> {
                final TaskAdapter[] adapter = new TaskAdapter[1];
                adapter[0] = new TaskAdapter(tasks, task -> {
                    new Thread(() -> {
                        AppDatabase.getInstance(getContext()).taskDao().markTaskCompleted(task.Id);
                        // Reload the list after marking as completed
                        List<Task> updatedTasks = AppDatabase.getInstance(getContext()).taskDao().getUnCompletedTasks();
                        requireActivity().runOnUiThread(() -> {
                            adapter[0].updateTasks(updatedTasks);
                        });
                    }).start();
                });
                recyclerView.setAdapter(adapter[0]);
            });
        }).start();
        getView().findViewById(R.id.btnNewTask).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
            startActivity(intent);
        });
    }
}
