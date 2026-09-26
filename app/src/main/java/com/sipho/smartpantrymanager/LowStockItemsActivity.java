package com.sipho.smartpantrymanager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LowStockItemsActivity extends AppCompatActivity {

    TextView lowStockItemsText;
    Button backButton;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_low_stock_items);

        lowStockItemsText = findViewById(R.id.lowStockItemsText);
        backButton = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        backButton.setOnClickListener(v -> {
            finish();
        });

        loadLowStockItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadLowStockItems();
    }

    private void loadLowStockItems() {

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

        String lowStockItems = "";

        while (cursor.moveToNext()) {

            String itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow("item_name")
            );

            int quantity = cursor.getInt(
                    cursor.getColumnIndexOrThrow("quantity")
            );

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("expiry_date")
            );

            // Check whether the item is low stock
            if (quantity <= lowStockThreshold) {

                lowStockItems +=
                        "Item: " + itemName +
                                "\nQuantity: " + quantity +
                                "\nExpiry Date: " + expiryDate +
                                "\n--------------------\n";
            }
        }

        cursor.close();

        if (lowStockItems.isEmpty()) {

            lowStockItemsText.setText(
                    "No low stock items."
            );

        } else {

            lowStockItemsText.setText(lowStockItems);
        }
    }
}