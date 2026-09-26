package com.sipho.smartpantrymanager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AlertsActivity extends AppCompatActivity {

    TextView alertsText;
    Button backButton;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alerts);

        alertsText = findViewById(R.id.alertsText);

        backButton = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        backButton.setOnClickListener(v -> {
            finish();
        });

        loadAlerts();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadAlerts();
    }

    private void loadAlerts() {

        // Get the saved low stock threshold
        SharedPreferences preferences = getSharedPreferences(
                "PantryData",
                MODE_PRIVATE
        );

        int lowStockThreshold = preferences.getInt(
                "low_stock_threshold",
                5
        );

        // Get pantry items from SQLite
        Cursor cursor = databaseHelper.getAllPantryItems();

        String alerts = "";

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd");

        Date today = new Date();

        while (cursor.moveToNext()) {

            String itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow("item_name")
            );

            int quantity = cursor.getInt(
                    cursor.getColumnIndexOrThrow("quantity")
            );

            String expiryDateText = cursor.getString(
                    cursor.getColumnIndexOrThrow("expiry_date")
            );

            // Check low stock
            if (quantity <= lowStockThreshold) {

                alerts +=
                        "LOW STOCK\n" +
                                "Item: " + itemName +
                                "\nQuantity: " + quantity +
                                "\n--------------------\n";
            }

            // Check expiry
            try {

                Date expiryDate =
                        dateFormat.parse(expiryDateText);

                long difference =
                        expiryDate.getTime() - today.getTime();

                long daysUntilExpiry =
                        TimeUnit.MILLISECONDS.toDays(difference);

                if (daysUntilExpiry < 0) {

                    alerts +=
                            "EXPIRED\n" +
                                    "Item: " + itemName +
                                    "\nExpiry Date: " + expiryDateText +
                                    "\n--------------------\n";

                } else if (daysUntilExpiry <= 7) {

                    alerts +=
                            "EXPIRING SOON\n" +
                                    "Item: " + itemName +
                                    "\nExpiry Date: " + expiryDateText +
                                    "\nDays remaining: " +
                                    daysUntilExpiry +
                                    "\n--------------------\n";
                }

            } catch (ParseException e) {

                // Ignore invalid expiry dates
            }
        }

        cursor.close();

        if (alerts.isEmpty()) {

            alertsText.setText(
                    "No alerts. Your pantry looks good!"
            );

        } else {

            alertsText.setText(alerts);
        }
    }
}