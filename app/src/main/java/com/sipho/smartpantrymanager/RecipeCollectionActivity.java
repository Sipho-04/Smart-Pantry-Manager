package com.sipho.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeCollectionActivity extends AppCompatActivity {

    RecyclerView recyclerViewRecipes;
    Button backButton;

    DatabaseHelper databaseHelper;

    ArrayList<Recipe> recipes;
    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_collection);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        backButton = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        recipes = new ArrayList<>();

        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        backButton.setOnClickListener(v -> finish());

        loadRecipes();
    }

    private void loadRecipes() {

        recipes.clear();

        Cursor cursor = databaseHelper.getAllRecipes();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String recipeName = cursor.getString(
                    cursor.getColumnIndexOrThrow("recipe_name")
            );

            String ingredients = cursor.getString(
                    cursor.getColumnIndexOrThrow("ingredients")
            );

            String preparation = cursor.getString(
                    cursor.getColumnIndexOrThrow("preparation")
            );

            Recipe recipe = new Recipe(
                    id,
                    recipeName,
                    ingredients,
                    preparation
            );

            recipes.add(recipe);
        }

        cursor.close();

        recipeAdapter = new RecipeAdapter(
                recipes,
                recipe -> {

                    Intent intent = new Intent(
                            RecipeCollectionActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra(
                            "recipeName",
                            recipe.getRecipeName()
                    );

                    intent.putExtra(
                            "recipeIngredients",
                            recipe.getIngredients()
                    );

                    intent.putExtra(
                            "recipePreparation",
                            recipe.getPreparation()
                    );

                    startActivity(intent);
                }
        );

        recyclerViewRecipes.setAdapter(recipeAdapter);
    }
}