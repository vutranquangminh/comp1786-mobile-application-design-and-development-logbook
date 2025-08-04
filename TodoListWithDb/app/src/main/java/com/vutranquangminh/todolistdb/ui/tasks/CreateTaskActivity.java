package com.vutranquangminh.todolistdb.ui.tasks;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vutranquangminh.todolistdb.R;
import com.vutranquangminh.todolistdb.data.AppDatabase;
import com.vutranquangminh.todolistdb.entities.Task;

public class CreateTaskActivity extends AppCompatActivity {
    private EditText editName, editDuration, editDescription;
    private DatePicker datePicker;
    private Button buttonCreate;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        editName = findViewById(R.id.edtName);
        editDuration = findViewById(R.id.edtDuration);
        editDescription = findViewById(R.id.edtDescription);
        datePicker = findViewById(R.id.datePicker);
        buttonCreate = findViewById(R.id.btnSaveTask);
        db = AppDatabase.getInstance(getApplicationContext());

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
    }

    private void createTask() {
        String name = editName.getText().toString();
        Integer duration = Integer.valueOf(editDuration.getText().toString());
        String description = editDescription.getText().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        // Validate inputs
        if (TextUtils.isEmpty(name) || duration == null) {
            Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert date to string
        String dateString = String.format("%04d-%02d-%02d", year, month + 1, day);
        // Create Task object
        Task task = new Task(description, name, dateString, duration, false);
        new Thread(() -> {
            try {
                db.taskDao().insertTask(task);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Course created!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Failed to create course: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}


