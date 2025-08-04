package com.vutranquangminh.lengthunitconverterapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText edtInputValue;
    private Spinner spinnerInputUnit, spinnerOutputUnit;
    private TextView txtViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtInputValue = findViewById(R.id.edtInputValue);
        spinnerInputUnit = findViewById(R.id.spinnerInputUnit);
        spinnerOutputUnit = findViewById(R.id.spinnerOutputUnit);
        txtViewResult = findViewById(R.id.txtViewResult);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertAndDisplay();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
        edtInputValue.addTextChangedListener(watcher);

        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Prevent selecting the same unit for both input and output
                if (parent.getId() == R.id.spinnerInputUnit) {
                    // If input unit is selected, check if it's the same as output unit
                    if (position == spinnerOutputUnit.getSelectedItemPosition()) {
                        // Change output unit to next available option
                        int newOutputPosition = (position + 1) % 4; // 4 units total
                        spinnerOutputUnit.setSelection(newOutputPosition);
                    }
                } else if (parent.getId() == R.id.spinnerOutputUnit) {
                    // If output unit is selected, check if it's the same as input unit
                    if (position == spinnerInputUnit.getSelectedItemPosition()) {
                        // Change input unit to next available option
                        int newInputPosition = (position + 1) % 4; // 4 units total
                        spinnerInputUnit.setSelection(newInputPosition);
                    }
                }
                convertAndDisplay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        spinnerInputUnit.setOnItemSelectedListener(spinnerListener);
        spinnerOutputUnit.setOnItemSelectedListener(spinnerListener);
    }

    private void convertAndDisplay() {
        String inputStr = edtInputValue.getText().toString();
        if (inputStr.isEmpty()) {
            txtViewResult.setText("");
            return;
        }
        double inputValue;
        try {
            inputValue = Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            txtViewResult.setText("Invalid input");
            return;
        }
        int from = spinnerInputUnit.getSelectedItemPosition();
        int to = spinnerOutputUnit.getSelectedItemPosition();
        double result = convertLength(inputValue, from, to);
        txtViewResult.setText(String.valueOf(result));
    }

    // Conversion logic: all units are converted to meters as base, then to target
    private double convertLength(double value, int from, int to) {
        double valueInMeters = value * getUnitToMeterFactor(from);
        return valueInMeters / getUnitToMeterFactor(to);
    }

    // 0: Metre, 1: Millimetre, 2: Mile, 3: Foot
    private double getUnitToMeterFactor(int unit) {
        switch (unit) {
            case 0: return 1.0;      // Metre
            case 1: return 0.001;    // Millimetre
            case 2: return 1609.344; // Mile
            case 3: return 0.3048;   // Foot
            default: return 1.0;
        }
    }
}