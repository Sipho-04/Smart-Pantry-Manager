package com.sipho.smartpantrymanager;

public class Recipe {

    private int id;
    private String recipeName;
    private String ingredients;
    private String preparation;

    public Recipe(
            int id,
            String recipeName,
            String ingredients,
            String preparation) {

        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.preparation = preparation;
    }

    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getPreparation() {
        return preparation;
    }
}