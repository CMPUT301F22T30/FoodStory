package com.example.foodstory;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Defining Recipe class that stores all the attributes of the recipe
 */
public class RecipeClass implements Serializable{
    private String title;
    private String prepTime;
    private int numServings;
    private String recipeCategory;
    private String comments;
    private String photo;
    private ArrayList<Ingredient> ingredients;

    /**
     * Constructor for recipe class
     * Declaring all the parameters:
     * @param title
     * Defining title
     * @param prepTime
     * Defining prepTime
     * @param numServings
     * Defining numServings
     * @param recipeCategory
     * Defining recipeCategory
     * @param comments
     * Defining comments
     * @param photo
     * Defining photo
     */
    public RecipeClass(String title, String prepTime, int numServings, String recipeCategory, String comments, String photo) {
        this.title = title;
        this.prepTime = prepTime;
        this.numServings = numServings;
        this.recipeCategory = recipeCategory;
        this.comments = comments;
        this.photo = photo;
        this.ingredients = new ArrayList<>();
    }
    /**
     * Defining getters and setters for the attributes of recipe class
     * @return
     * Returns the values
     */
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

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    protected ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
