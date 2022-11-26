package com.example.foodstory;

import java.util.ArrayList;
import java.io.Serializable;

public class RecipeClass implements Serializable{
    private String title;
    private String prepTime;
    private int numServings;
    private String recipeCategory;
    private String comments;
    private String photo;
    private ArrayList<Ingredient> ingredients;

    public RecipeClass(){}

    public RecipeClass(String title, String prepTime, int numServings, String recipeCategory,
                       String comments, String photo) {
        this.title = title;
        this.prepTime = prepTime;
        this.numServings = numServings;
        this.recipeCategory = recipeCategory;
        this.comments = comments;
        this.photo = photo;
        this.ingredients = new ArrayList<>();
    }

    public RecipeClass(String title){
        this.title = title;
        this.prepTime = "";
        this.numServings = 0;
        this.recipeCategory = "";
        this.comments = "";
        this.photo = "";
        this.ingredients = new ArrayList<>();
    }

    public String getPrepTime() {
        return prepTime;
    }

    public int getNumServings() {
        return numServings;
    }

    public String getComments() {
        return comments;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRecipePrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getRecipeServingsStr(){
        return String.valueOf(numServings);
    }

    public void setRecipeServings(int numServings) {
        this.numServings = numServings;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
