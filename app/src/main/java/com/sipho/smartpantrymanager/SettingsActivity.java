package com.sipho.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    EditText lowStockThreshold;
    Button saveSettings;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        lowStockThreshold = findViewById(R.id.edtLowStockThreshold);
        saveSettings = findViewById(R.id.btnSaveSettings);
        backButton = findViewById(R.id.btnBack);

        backButton.setOnClickListener(v -> {
            finish();
        });

        // Load the previously saved threshold
        SharedPreferences preferences = getSharedPreferences(
                "PantryData",
                MODE_PRIVATE
        );

        int savedThreshold = preferences.getInt("low_stock_threshold", 5);

        lowStockThreshold.setText(String.valueOf(savedThreshold));

        saveSettings.setOnClickListener(v -> {

            String thresholdText =
                    lowStockThreshold.getText().toString();

            if (thresholdText.isEmpty()) {

                Toast.makeText(
                        SettingsActivity.this,
                        "Please enter a threshold",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                int threshold = Integer.parseInt(thresholdText);

                preferences.edit()
                        .putInt("low_stock_threshold", threshold)
                        .apply();

                Toast.makeText(
                        SettingsActivity.this,
                        "Settings saved",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}