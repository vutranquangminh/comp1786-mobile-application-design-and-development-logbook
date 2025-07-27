package com.example.unit_converter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner spinnerInputUnit, spinnerOutputUnit;
    TextView resultText;
    ImageButton swapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Center the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        
        // Create centered title TextView
        TextView titleView = new TextView(this);
        titleView.setText("Length Unit Converter");
        titleView.setTextSize(20);
        titleView.setTextColor(getResources().getColor(android.R.color.white));
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER;
        titleView.setLayoutParams(layoutParams);
        
        toolbar.addView(titleView);

        inputValue = findViewById(R.id.edtInputValue);
        spinnerInputUnit = findViewById(R.id.spinnerInputUnit);
        spinnerOutputUnit = findViewById(R.id.spinnerOutputUnit);
        resultText = findViewById(R.id.txtViewResult);
        swapButton = findViewById(R.id.btnSwap);

        // Add text change listener for real-time conversion
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                convert();
            }
        });

        // Setup swap button click listener
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapUnits();
            }
        });

        AdapterView.OnItemSelectedListener inputListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preventSameUnitSelection();
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        AdapterView.OnItemSelectedListener outputListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preventSameUnitSelection();
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        spinnerInputUnit.setOnItemSelectedListener(inputListener);
        spinnerOutputUnit.setOnItemSelectedListener(outputListener);
    }

    private void preventSameUnitSelection() {
        String inputUnit = spinnerInputUnit.getSelectedItem().toString();
        String outputUnit = spinnerOutputUnit.getSelectedItem().toString();
        
        // If both units are the same, change output unit to next available option
        if (inputUnit.equals(outputUnit)) {
            String[] units = getResources().getStringArray(R.array.length_units);
            int currentOutputPosition = spinnerOutputUnit.getSelectedItemPosition();
            int newPosition = (currentOutputPosition + 1) % units.length;
            
            // Make sure new position is different from input position
            int inputPosition = spinnerInputUnit.getSelectedItemPosition();
            if (newPosition == inputPosition) {
                newPosition = (newPosition + 1) % units.length;
            }
            
            spinnerOutputUnit.setSelection(newPosition);
        }
    }

    private void swapUnits() {
        // Get current selections
        int inputPosition = spinnerInputUnit.getSelectedItemPosition();
        int outputPosition = spinnerOutputUnit.getSelectedItemPosition();
        
        // Swap the selections
        spinnerInputUnit.setSelection(outputPosition);
        spinnerOutputUnit.setSelection(inputPosition);
        
        // Convert with new units
        convert();
    }

    private void convert() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            resultText.setText("");
            return;
        }

        try {
            double value = Double.parseDouble(input);
            String fromUnit = spinnerInputUnit.getSelectedItem().toString();
            String toUnit = spinnerOutputUnit.getSelectedItem().toString();

            double result = convertUnits(value, fromUnit, toUnit);
            resultText.setText(String.format("%.4f", result));
        } catch (NumberFormatException e) {
            resultText.setText("Invalid input");
        }
    }

    private double convertUnits(double value, String from, String to) {
        double metres;
        
        // Convert to metres first
        switch (from) {
            case "Metre":
                metres = value;
                break;
            case "Millimetre":
                metres = value / 1000;
                break;
            case "Mile":
                metres = value * 1609.344;
                break;
            case "Foot":
                metres = value * 0.3048;
                break;
            default:
                metres = value;
                break;
        }

        // Convert from metres to target unit
        switch (to) {
            case "Metre":
                return metres;
            case "Millimetre":
                return metres * 1000;
            case "Mile":
                return metres / 1609.344;
            case "Foot":
                return metres / 0.3048;
            default:
                return metres;
        }
    }
}
