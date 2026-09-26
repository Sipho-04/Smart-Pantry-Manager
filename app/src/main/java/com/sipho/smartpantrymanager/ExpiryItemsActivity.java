package com.sipho.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ExpiryItemsActivity extends AppCompatActivity {

    TextView expiryItemsText;
    Button backButton;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expiry_items);

        expiryItemsText = findViewById(R.id.expiryItemsText);
        backButton = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        backButton.setOnClickListener(v -> {
            finish();
        });

        loadExpiryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadExpiryItems();
    }

    private void loadExpiryItems() {

        // Get pantry items from SQLite
        Cursor cursor = databaseHelper.getAllPantryItems();

        String expiryList = "";

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd");

        Date today = new Date();

        while (cursor.moveToNext()) {

            String itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow("item_name")
            );

            String expiryDateText = cursor.getString(
                    cursor.getColumnIndexOrThrow("expiry_date")
            );

            try {

                Date expiryDate =
                        dateFormat.parse(expiryDateText);

                long difference =
                        expiryDate.getTime() - today.getTime();

                long daysUntilExpiry =
                        TimeUnit.MILLISECONDS.toDays(difference);

                if (daysUntilExpiry < 0) {

                    expiryList +=
                            "EXPIRED\n" +
                                    "Item: " + itemName +
                                    "\nExpiry Date: " + expiryDateText +
                                    "\n--------------------\n";

                } else if (daysUntilExpiry <= 7) {

                    expiryList +=
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

        if (expiryList.isEmpty()) {

            expiryItemsText.setText(
                    "No expired or soon-to-expire items."
            );

        } else {

            expiryItemsText.setText(expiryList);
        }
    }
}