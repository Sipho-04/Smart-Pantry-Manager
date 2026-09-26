package com.sipho.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        Button viewPantryButton = findViewById(R.id.btnViewPantry);
        Button addItemButton = findViewById(R.id.btnAddItem);
        Button lowStockButton = findViewById(R.id.btnLowStock);
        Button expiryItemsButton = findViewById(R.id.btnExpiryItems);
        Button settingsButton = findViewById(R.id.btnSettings);
        Button alertsButton = findViewById(R.id.btnAlerts);
        Button searchPantryButton = findViewById(R.id.btnSearchPantry);
        Button suggestedRecipesButton =
                findViewById(R.id.btnSuggestedRecipes);
        Button recipeCollectionButton =
                findViewById(R.id.btnRecipeCollection);

        lowStockButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    LowStockItemsActivity.class
            );

            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });

        alertsButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AlertsActivity.class
            );

            startActivity(intent);
        });

        viewPantryButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    PantryItemsActivity.class
            );

            startActivity(intent);
        });

        addItemButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddPantryItemActivity.class
            );

            startActivity(intent);
        });

        expiryItemsButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ExpiryItemsActivity.class
            );

            startActivity(intent);
        });

        searchPantryButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SearchPantryActivity.class
            );

            startActivity(intent);
        });

        suggestedRecipesButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
        });

        recipeCollectionButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    RecipeCollectionActivity.class
            );

            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.menuPantry) {

            Intent intent = new Intent(
                    MainActivity.this,
                    PantryItemsActivity.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuRecipes) {

            Intent intent = new Intent(
                    MainActivity.this,
                    RecipeCollectionActivity.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuSettings) {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}