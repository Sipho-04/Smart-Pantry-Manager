package com.sipho.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchPantryActivity extends AppCompatActivity {

    EditText searchItem;
    Button searchButton;
    Button backButton;
    TextView searchResultsText;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_pantry);

        searchItem = findViewById(R.id.edtSearchItem);
        searchButton = findViewById(R.id.btnSearch);
        searchResultsText = findViewById(R.id.searchResultsText);
        backButton = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        backButton.setOnClickListener(v -> {
            finish();
        });

        searchButton.setOnClickListener(v -> {

            String searchText =
                    searchItem.getText().toString().trim();

            if (searchText.isEmpty()) {

                searchResultsText.setText(
                        "Please enter an item name."
                );

                return;
            }

            searchPantryItems(searchText);
        });
    }

    private void searchPantryItems(String searchText) {

        Cursor cursor = databaseHelper.getAllPantryItems();

        String results = "";

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

            if (itemName.toLowerCase()
                    .contains(searchText.toLowerCase())) {

                results +=
                        "Item: " + itemName +
                                "\nQuantity: " + quantity +
                                "\nExpiry Date: " + expiryDate +
                                "\n--------------------\n";
            }
        }

        cursor.close();

        if (results.isEmpty()) {

            searchResultsText.setText(
                    "No matching pantry item found."
            );

        } else {

            searchResultsText.setText(results);
        }
    }
}