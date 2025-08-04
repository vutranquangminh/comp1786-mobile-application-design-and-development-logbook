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


public class ViewFragment extends Fragment {
    public ViewFragment() {
        super(R.layout.fragment_task_completed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCompletedTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new Thread(() -> {
            List<Task> completedTasks = AppDatabase.getInstance(getContext())
                    .taskDao()
                    .getCompletedTasks();
            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    com.vutranquangminh.todolistdb.ui.completes.CompletedTaskAdapter adapter =
                            new com.vutranquangminh.todolistdb.ui.completes.CompletedTaskAdapter(completedTasks);
                    recyclerView.setAdapter(adapter);
                });
            }
        }).start();
    }
}
