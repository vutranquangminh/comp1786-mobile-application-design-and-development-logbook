// MainActivity.java
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

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        spinnerInputUnit.setOnItemSelectedListener(listener);
        spinnerOutputUnit.setOnItemSelectedListener(listener);
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
        double meters;
        
        // Convert to meters first
        switch (from) {
            case "Kilometers":
                meters = value * 1000;
                break;
            case "Centimeters":
                meters = value / 100;
                break;
            case "Millimeters":
                meters = value / 1000;
                break;
            default:
                meters = value;
                break;
        }

        // Convert from meters to target unit
        switch (to) {
            case "Kilometers":
                return meters / 1000;
            case "Centimeters":
                return meters * 100;
            case "Millimeters":
                return meters * 1000;
            default:
                return meters;
        }
    }
}
