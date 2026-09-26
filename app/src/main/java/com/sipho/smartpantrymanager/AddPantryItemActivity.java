package com.sipho.smartpantrymanager;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPantryItemActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText itemName;
    EditText quantity;
    EditText expiryDate;
    Button saveItem;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_pantry_item);

        databaseHelper = new DatabaseHelper(this);

        itemName = findViewById(R.id.edtItemName);

        quantity = findViewById(R.id.edtQuantity);

        expiryDate = findViewById(R.id.edtExpiryDate);

        saveItem = findViewById(R.id.btnSaveItem);

        backButton = findViewById(R.id.btnBack);

        backButton.setOnClickListener(v -> {
            finish();
        });


        int editId = getIntent().getIntExtra("editId", -1);

        if (editId != -1) {

            String editName = getIntent().getStringExtra("editName");
            int editQuantity = getIntent().getIntExtra("editQuantity", 0);
            String editExpiry = getIntent().getStringExtra("editExpiry");

            itemName.setText(editName);
            quantity.setText(String.valueOf(editQuantity));
            expiryDate.setText(editExpiry);

            saveItem.setText("Save Changes");

        }

        saveItem.setOnClickListener(v -> {

            String name = itemName.getText().toString().trim();
            String qty = quantity.getText().toString().trim();
            String expiry = expiryDate.getText().toString().trim();

            if (name.isEmpty() || qty.isEmpty() || expiry.isEmpty()) {

                Toast.makeText(
                        AddPantryItemActivity.this,
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Open SQLite database
            DatabaseHelper databaseHelper = new DatabaseHelper(this);

            try {
                int quantityValue = Integer.parseInt(qty);

                if (editId == -1) {

                    databaseHelper.addPantryItem(
                            name,
                            quantityValue,
                            expiry
                    );

                } else {

                    databaseHelper.updatePantryItem(
                            editId,
                            name,
                            quantityValue,
                            expiry
                    );
                }

                Toast.makeText(
                        AddPantryItemActivity.this,
                        editId == -1
                                ? "Pantry item saved"
                                : "Pantry item updated",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } catch (NumberFormatException e) {

                Toast.makeText(
                        AddPantryItemActivity.this,
                        "Quantity must be a number",
                        Toast.LENGTH_SHORT
                ).show();
            }

        });
    }
}