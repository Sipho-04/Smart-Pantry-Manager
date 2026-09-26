package com.sipho.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryItemsActivity extends AppCompatActivity {
    RecyclerView recyclerViewPantry;
    Button backButton;
    DatabaseHelper databaseHelper;

    ArrayList<PantryItem> pantryItems;
    PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_items);

        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);

        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        pantryItems = new ArrayList<>();
        backButton = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        backButton.setOnClickListener(v -> {
            finish();
        });

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {

        pantryItems.clear();

        Cursor cursor = databaseHelper.getAllPantryItems();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("item_name")
            );

            int quantity = cursor.getInt(
                    cursor.getColumnIndexOrThrow("quantity")
            );

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("expiry_date")
            );

            PantryItem item = new PantryItem(
                    id,
                    name,
                    quantity,
                    expiryDate
            );

            pantryItems.add(item);
        }

        cursor.close();

        if (pantryAdapter == null) {

            pantryAdapter = new PantryAdapter(
                    pantryItems,
                    new PantryAdapter.OnItemActionListener() {

                        @Override
                        public void onEdit(PantryItem item) {

                            Intent intent = new Intent(
                                    PantryItemsActivity.this,
                                    AddPantryItemActivity.class
                            );

                            intent.putExtra("editId", item.getId());
                            intent.putExtra("editName", item.getItemName());
                            intent.putExtra(
                                    "editQuantity",
                                    item.getQuantity()
                            );
                            intent.putExtra(
                                    "editExpiry",
                                    item.getExpiryDate()
                            );

                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(PantryItem item) {

                            new AlertDialog.Builder(
                                    PantryItemsActivity.this
                            )
                                    .setTitle("Delete Pantry Item")
                                    .setMessage(
                                            "Are you sure you want to delete this item?"
                                    )
                                    .setNegativeButton(
                                            "Cancel",
                                            null
                                    )
                                    .setPositiveButton(
                                            "Delete",
                                            (dialog, which) -> {

                                                databaseHelper
                                                        .deletePantryItem(
                                                                item.getId()
                                                        );

                                                loadPantryItems();
                                            }
                                    )
                                    .show();
                        }
                    }
            );

            recyclerViewPantry.setAdapter(pantryAdapter);

        } else {

            pantryAdapter.notifyDataSetChanged();
        }
    }
}