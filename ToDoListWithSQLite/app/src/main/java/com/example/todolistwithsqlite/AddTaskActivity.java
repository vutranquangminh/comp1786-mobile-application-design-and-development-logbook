package com.example.todolistwithsqlite;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistwithsqlite.databinding.ActivityAddTaskBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private ActivityAddTaskBinding binding;
    private Calendar calendar;
    private Calendar deadlineCalendar;
    private Calendar durationCalendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat durationFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar = Calendar.getInstance();
        deadlineCalendar = Calendar.getInstance();
        durationCalendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        durationFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        binding.btnStartDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeadlinePicker();
            }
        });

        binding.btnEndDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDurationPicker();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("task_name")) {
            String existingTaskName = intent.getStringExtra("task_name");
            binding.etTaskName.setText(existingTaskName);
            binding.btnAddTask.setText("Update Task");
            
            if (intent.hasExtra("task_end_date")) {
                long existingEndDateMillis = intent.getLongExtra("task_end_date", 0);
                durationCalendar.setTimeInMillis(existingEndDateMillis);
                updateEndDurationButtonText();
            }
            
            if (intent.hasExtra("task_description")) {
                String existingDescription = intent.getStringExtra("task_description");
                binding.etDescription.setText(existingDescription);
            }
            
            if (intent.hasExtra("task_start_date")) {
                long startDateMillis = intent.getLongExtra("task_start_date", 0);
                deadlineCalendar.setTimeInMillis(startDateMillis);
                updateStartDurationButtonText();
            }
        }

        binding.btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = binding.etTaskName.getText().toString().trim();
                String description = binding.etDescription.getText().toString().trim();
                
                if (taskName.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
                    return;
                }

                calendar.setTimeInMillis(deadlineCalendar.getTimeInMillis());

                Intent resultIntent = new Intent();
                resultIntent.putExtra("task_name", taskName);
                resultIntent.putExtra("task_start_date", calendar.getTimeInMillis());
                resultIntent.putExtra("task_end_date", durationCalendar.getTimeInMillis());
                resultIntent.putExtra("task_description", description);
                if (intent.hasExtra("edit_position")) {
                    resultIntent.putExtra("edit_position", intent.getIntExtra("edit_position", -1));
                }
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void showDeadlinePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_picker, null);
        builder.setView(dialogView);

        TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
        NumberPicker npDay = dialogView.findViewById(R.id.npDay);
        NumberPicker npMonth = dialogView.findViewById(R.id.npMonth);
        NumberPicker npYear = dialogView.findViewById(R.id.npYear);
        NumberPicker npHour = dialogView.findViewById(R.id.npHour);
        NumberPicker npMinute = dialogView.findViewById(R.id.npMinute);

        tvDialogTitle.setText("Select Start Date & Time");

        npDay.setMinValue(1);
        npDay.setMaxValue(31);
        npDay.setValue(deadlineCalendar.get(Calendar.DAY_OF_MONTH));

        npMonth.setMinValue(1);
        npMonth.setMaxValue(12);
        npMonth.setValue(deadlineCalendar.get(Calendar.MONTH) + 1);

        npYear.setMinValue(2024);
        npYear.setMaxValue(2030);
        npYear.setValue(deadlineCalendar.get(Calendar.YEAR));

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npHour.setValue(deadlineCalendar.get(Calendar.HOUR_OF_DAY));

        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npMinute.setValue(deadlineCalendar.get(Calendar.MINUTE));

        builder.setPositiveButton("OK", (dialog, which) -> {
            deadlineCalendar.set(Calendar.YEAR, npYear.getValue());
            deadlineCalendar.set(Calendar.MONTH, npMonth.getValue() - 1);
            deadlineCalendar.set(Calendar.DAY_OF_MONTH, npDay.getValue());
            deadlineCalendar.set(Calendar.HOUR_OF_DAY, npHour.getValue());
            deadlineCalendar.set(Calendar.MINUTE, npMinute.getValue());
            updateStartDurationButtonText();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDurationPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_picker, null);
        builder.setView(dialogView);

        TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
        NumberPicker npDay = dialogView.findViewById(R.id.npDay);
        NumberPicker npMonth = dialogView.findViewById(R.id.npMonth);
        NumberPicker npYear = dialogView.findViewById(R.id.npYear);
        NumberPicker npHour = dialogView.findViewById(R.id.npHour);
        NumberPicker npMinute = dialogView.findViewById(R.id.npMinute);

        tvDialogTitle.setText("Set Duration");

        npDay.setMinValue(1);
        npDay.setMaxValue(31);
        npDay.setValue(durationCalendar.get(Calendar.DAY_OF_MONTH));

        npMonth.setMinValue(1);
        npMonth.setMaxValue(12);
        npMonth.setValue(durationCalendar.get(Calendar.MONTH) + 1);

        npYear.setMinValue(2024);
        npYear.setMaxValue(2030);
        npYear.setValue(durationCalendar.get(Calendar.YEAR));

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npHour.setValue(durationCalendar.get(Calendar.HOUR_OF_DAY));

        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npMinute.setValue(durationCalendar.get(Calendar.MINUTE));

        builder.setPositiveButton("OK", (dialog, which) -> {
            durationCalendar.set(Calendar.YEAR, npYear.getValue());
            durationCalendar.set(Calendar.MONTH, npMonth.getValue() - 1);
            durationCalendar.set(Calendar.DAY_OF_MONTH, npDay.getValue());
            durationCalendar.set(Calendar.HOUR_OF_DAY, npHour.getValue());
            durationCalendar.set(Calendar.MINUTE, npMinute.getValue());
            updateEndDurationButtonText();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateStartDurationButtonText() {
        binding.btnStartDuration.setText(dateFormat.format(deadlineCalendar.getTime()));
    }

    private void updateEndDurationButtonText() {
        binding.btnEndDuration.setText(durationFormat.format(durationCalendar.getTime()));
    }


} 