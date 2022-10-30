package com.example.foodstory;

import java.util.ArrayList;

public class RecipeClass {
    private String title;
    private String prepTime;
    private int numServings;
    private String recipeCategory;
    private String comments;
    private String photo;
    private ArrayList<Ingredient> ingredients;

    public RecipeClass(String title, String prepTime, int numServings, String recipeCategory, String comments, String photo) {
        this.title = title;
        this.prepTime = prepTime;
        this.numServings = numServings;
        this.recipeCategory = recipeCategory;
        this.comments = comments;
        this.photo = photo;
        //this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }
}
