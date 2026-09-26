package com.sipho.smartpantrymanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPantryTable = "CREATE TABLE pantry_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "item_name TEXT NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "expiry_date TEXT" +
                ")";

        db.execSQL(createPantryTable);

        String createRecipesTable = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_name TEXT NOT NULL, " +
                "ingredients TEXT NOT NULL, " +
                "preparation TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipesTable);
        addDefaultRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 2) {

            String createRecipesTable = "CREATE TABLE recipes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "recipe_name TEXT NOT NULL, " +
                    "ingredients TEXT NOT NULL, " +
                    "preparation TEXT NOT NULL" +
                    ")";

            db.execSQL(createRecipesTable);
        }

        if (oldVersion < 4) {
            addDefaultRecipes(db);
        }

        if (oldVersion < 5) {
            db.execSQL("DELETE FROM recipes");
            addDefaultRecipes(db);
        }
    }

    public void addPantryItem(
            String itemName,
            int quantity,
            String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        android.content.ContentValues values =
                new android.content.ContentValues();

        values.put("item_name", itemName);
        values.put("quantity", quantity);
        values.put("expiry_date", expiryDate);

        db.insert("pantry_items", null, values);

        db.close();
    }

    public android.database.Cursor getAllPantryItems() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM pantry_items",
                null
        );
    }

    public void updatePantryItem(
            int id,
            String itemName,
            int quantity,
            String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        android.content.ContentValues values =
                new android.content.ContentValues();

        values.put("item_name", itemName);
        values.put("quantity", quantity);
        values.put("expiry_date", expiryDate);

        db.update(
                "pantry_items",
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    public void deletePantryItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                "pantry_items",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    public void addRecipe(
            String recipeName,
            String ingredients,
            String preparation) {

        SQLiteDatabase db = this.getWritableDatabase();

        android.content.ContentValues values =
                new android.content.ContentValues();

        values.put("recipe_name", recipeName);
        values.put("ingredients", ingredients);
        values.put("preparation", preparation);

        db.insert("recipes", null, values);

        db.close();
    }

    public android.database.Cursor getAllRecipes() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM recipes",
                null
        );
    }

    private void addDefaultRecipes(SQLiteDatabase db) {

        insertRecipe(db,
                "Egg Sandwich",
                "bread:2,egg:2",
                "Fry or boil the eggs. Place the eggs between two slices of bread and serve.");

        insertRecipe(db,
                "French Toast",
                "bread:2,egg:2,milk:1",
                "Beat the egg and milk together. Dip the bread into the mixture and fry until golden.");

        insertRecipe(db,
                "Pancakes",
                "flour:2,egg:1,milk:2",
                "Mix the flour, egg and milk into a smooth batter. Cook spoonfuls of batter in a pan.");

        insertRecipe(db,
                "Scrambled Eggs",
                "egg:3,milk:1",
                "Beat the eggs with milk. Cook gently in a pan while stirring until scrambled.");

        insertRecipe(db,
                "Omelette",
                "egg:3,onion:1,tomato:1",
                "Beat the eggs. Add chopped onion and tomato, then cook in a pan until set.");

        insertRecipe(db,
                "Grilled Cheese Sandwich",
                "bread:2,cheese:2,butter:1",
                "Butter the bread, add cheese and grill until golden and the cheese melts.");

        insertRecipe(db,
                "Cheese Omelette",
                "egg:3,cheese:1",
                "Beat the eggs, cook them in a pan and add cheese before folding.");

        insertRecipe(db,
                "Tomato Egg Sandwich",
                "bread:2,egg:2,tomato:1",
                "Cook the eggs and place them on bread with sliced tomato.");

        insertRecipe(db,
                "Peanut Butter Sandwich",
                "bread:2,peanut butter:1",
                "Spread peanut butter on two slices of bread and put them together.");

        insertRecipe(db,
                "Banana Pancakes",
                "flour:2,egg:1,milk:1,banana:2",
                "Mash the bananas and mix with flour, egg and milk. Cook in a pan.");

        insertRecipe(db,
                "Egg Fried Rice",
                "rice:2,egg:2,onion:1",
                "Cook the rice. Fry onion and eggs, then add the rice and stir-fry.");

        insertRecipe(db,
                "Vegetable Fried Rice",
                "rice:2,carrot:1,peas:1,onion:1",
                "Fry the vegetables and onion. Add cooked rice and stir-fry.");

        insertRecipe(db,
                "Chicken Sandwich",
                "bread:2,chicken:2,tomato:1",
                "Cook the chicken. Place it on bread with sliced tomato.");

        insertRecipe(db,
                "Chicken Fried Rice",
                "rice:2,chicken:2,onion:1",
                "Cook the chicken and rice. Fry the onion and combine everything.");

        insertRecipe(db,
                "Tuna Sandwich",
                "bread:2,tuna:1,mayonnaise:1",
                "Mix tuna with mayonnaise and spread between two slices of bread.");

        insertRecipe(db,
                "Pasta with Tomato Sauce",
                "pasta:2,tomato:2,onion:1",
                "Cook the pasta. Prepare tomato and onion sauce and mix with pasta.");

        insertRecipe(db,
                "Garlic Pasta",
                "pasta:2,garlic:2,butter:1",
                "Cook the pasta. Fry garlic in butter and mix with the pasta.");

        insertRecipe(db,
                "Potato Omelette",
                "potato:2,egg:3,onion:1",
                "Cook the potatoes and onion. Add beaten eggs and cook until set.");

        insertRecipe(db,
                "Tuna Pasta",
                "pasta:2,tuna:1,mayonnaise:1",
                "Cook the pasta. Mix tuna and mayonnaise, then combine with pasta.");

        insertRecipe(db,
                "Vegetable Omelette",
                "egg:3,carrot:1,onion:1,tomato:1",
                "Beat the eggs and mix with chopped vegetables. Cook until set.");
    }

    private void insertRecipe(
            SQLiteDatabase db,
            String recipeName,
            String ingredients,
            String preparation) {

        android.content.ContentValues values =
                new android.content.ContentValues();

        values.put("recipe_name", recipeName);
        values.put("ingredients", ingredients);
        values.put("preparation", preparation);

        db.insert("recipes", null, values);
    }

}