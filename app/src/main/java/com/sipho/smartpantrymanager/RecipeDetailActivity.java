package com.sipho.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    TextView recipeName;
    TextView recipeIngredients;
    TextView recipePreparation;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        recipeName = findViewById(R.id.recipeDetailName);
        recipeIngredients = findViewById(R.id.recipeDetailIngredients);
        recipePreparation = findViewById(R.id.recipeDetailPreparation);
        backButton = findViewById(R.id.btnBack);

        String name = getIntent().getStringExtra("recipeName");
        String ingredients = getIntent().getStringExtra("recipeIngredients");
        String preparation = getIntent().getStringExtra("recipePreparation");

        recipeName.setText(name);
        recipeIngredients.setText(ingredients);
        recipePreparation.setText(preparation);

        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}