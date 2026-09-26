package com.sipho.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class SuggestedRecipesActivity extends AppCompatActivity {

    RecyclerView recyclerViewRecipes;
    TextView txtRecipeMessage;
    Button backButton;

    DatabaseHelper databaseHelper;

    ArrayList<Recipe> suggestedRecipes;
    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        txtRecipeMessage = findViewById(R.id.txtRecipeMessage);
        backButton = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        suggestedRecipes = new ArrayList<>();

        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        backButton.setOnClickListener(v -> finish());

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSuggestedRecipes();
    }

    private boolean recipeMatchesPantry(Recipe recipe) {

        HashMap<String, Integer> pantryItems = new HashMap<>();

        Cursor pantryCursor = databaseHelper.getAllPantryItems();

        while (pantryCursor.moveToNext()) {

            String itemName = pantryCursor.getString(
                    pantryCursor.getColumnIndexOrThrow("item_name")
            );

            int quantity = pantryCursor.getInt(
                    pantryCursor.getColumnIndexOrThrow("quantity")
            );

            itemName = normalizeIngredientName(itemName);

            pantryItems.put(itemName, quantity);
        }

        pantryCursor.close();

        String ingredientsText = recipe.getIngredients();

        String[] ingredients = ingredientsText.split(",");

        for (String ingredient : ingredients) {

            String[] parts = ingredient.trim().split(":");

            if (parts.length != 2) {
                return false;
            }

            String requiredIngredient =
                    normalizeIngredientName(parts[0]);

            int requiredQuantity;

            try {

                requiredQuantity =
                        Integer.parseInt(parts[1].trim());

            } catch (NumberFormatException e) {

                return false;
            }

            Integer availableQuantity =
                    pantryItems.get(requiredIngredient);

            if (availableQuantity == null) {

                return false;
            }

            if (availableQuantity < requiredQuantity) {

                return false;
            }
        }

        return true;
    }

    private String normalizeIngredientName(String ingredient) {

        ingredient = ingredient.toLowerCase().trim();

        // Remove common measurement words
        ingredient = ingredient.replace("grams", "");
        ingredient = ingredient.replace("gram", "");
        ingredient = ingredient.replace("kg", "");
        ingredient = ingredient.replace("kilograms", "");
        ingredient = ingredient.replace("kilogram", "");

        ingredient = ingredient.trim();

        // Convert common plural forms to singular
        if (ingredient.equals("eggs")) {
            return "egg";
        }

        if (ingredient.equals("tomatoes")) {
            return "tomato";
        }

        if (ingredient.equals("onions")) {
            return "onion";
        }

        if (ingredient.equals("bananas")) {
            return "banana";
        }

        if (ingredient.equals("potatoes")) {
            return "potato";
        }

        if (ingredient.equals("carrots")) {
            return "carrot";
        }

        if (ingredient.equals("peas")) {
            return "pea";
        }

        return ingredient;
    }

    private void loadSuggestedRecipes() {

        suggestedRecipes.clear();

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

            if (recipeMatchesPantry(recipe)) {

                suggestedRecipes.add(recipe);
            }
        }

        cursor.close();

        if (suggestedRecipes.isEmpty()) {

            txtRecipeMessage.setText(
                    "No recipes can be made with your current pantry items."
            );

            recyclerViewRecipes.setAdapter(null);

        } else {

            txtRecipeMessage.setText(
                    "Recipes you can make with your pantry:"
            );

            recipeAdapter = new RecipeAdapter(
                    suggestedRecipes,
                    recipe -> {

                        Intent intent = new Intent(
                                SuggestedRecipesActivity.this,
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
}