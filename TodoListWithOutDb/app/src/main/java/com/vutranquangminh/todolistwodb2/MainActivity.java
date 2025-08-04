package com.vutranquangminh.todolistwodb2;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vutranquangminh.todolistwodb.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDate, editTextDuration, editTextDescription;
    private RecyclerView recyclerView;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDate = findViewById(R.id.editTextDate);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextDescription = findViewById(R.id.editTextDescription);
        recyclerView = findViewById(R.id.recyclerViewTasks);

        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onEdit(int position) {
                Task task = taskList.get(position);
                editTextTask.setText(task.name);
                editTextDate.setText(task.date);
                editTextDuration.setText(String.valueOf(task.duration));
                editTextDescription.setText(task.description);
                editPosition = position;
            }

            @Override
            public void onDelete(int position) {
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.buttonAdd).setOnClickListener(v -> {
            String name = editTextTask.getText().toString();
            String date = editTextDate.getText().toString();
            String durationStr = editTextDuration.getText().toString();
            String description = editTextDescription.getText().toString();

            if (name.isEmpty() || date.isEmpty() || durationStr.isEmpty()) return;

            int duration = Integer.parseInt(durationStr);
            Task task = new Task(name, date, duration, description);

            if (editPosition >= 0) {
                taskList.set(editPosition, task);
                adapter.notifyItemChanged(editPosition);
                editPosition = -1;
            } else {
                taskList.add(task);
                adapter.notifyItemInserted(taskList.size() - 1);
            }

            editTextTask.setText("");
            editTextDate.setText("");
            editTextDuration.setText("");
            editTextDescription.setText("");
        });
    }
}
