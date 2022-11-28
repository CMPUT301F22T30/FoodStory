package com.example.foodstory;

import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MealPlan implements Serializable {

    private String mealPlanHeading;
    private String mealPlanDate;
    private String mealPlanName ;
    private ArrayList<String> breakfastIngredientArray;
    private ArrayList<String> breakfastRecipeArray;
    private ArrayList<String> lunchIngredientArray;
    private ArrayList<String> lunchRecipeArray;
    private ArrayList<String> dinnerIngredientArray;
    private ArrayList<String> dinnerRecipeArray;
    private int numOfPeople;

    public MealPlan(){

    }

    public MealPlan(String mealPlanName,String mealPlanDate, ArrayList<String> breakfastIngredientArray, ArrayList<String> breakfastRecipeArray, ArrayList<String> lunchIngredientArray, ArrayList<String> lunchRecipeArray, ArrayList<String> dinnerIngredientArray, ArrayList<String> dinnerRecipeArray, int numOfPeople) {
        this.mealPlanDate = mealPlanDate;
        this.mealPlanName = mealPlanName;
        this.breakfastIngredientArray = breakfastIngredientArray;
        this.breakfastRecipeArray = breakfastRecipeArray;
        this.lunchIngredientArray = lunchIngredientArray;
        this.lunchRecipeArray = lunchRecipeArray;
        this.dinnerIngredientArray = dinnerIngredientArray;
        this.dinnerRecipeArray = dinnerRecipeArray;
        this.numOfPeople = numOfPeople;
        this.mealPlanHeading = "Meal Plan";
    }

    public String getMealPlanHeading() {
        return mealPlanHeading;
    }

    public String getMealPlanDate() {
        return mealPlanDate;
    }

    public void setMealPlanDate(String mealPlanDate) {
        this.mealPlanDate = mealPlanDate;
    }

    public String getMealPlanName() {
        return mealPlanName;
    }

    public void setMealPlanName(String mealPlanName) {
        this.mealPlanName = mealPlanName;
    }

    public ArrayList<String> getBreakfastIngredientArray() {
        return breakfastIngredientArray;
    }

    public void setBreakfastIngredientArray(ArrayList<String> breakfastIngredientArray) {
        this.breakfastIngredientArray = breakfastIngredientArray;
    }

    public ArrayList<String> getBreakfastRecipeArray() {
        return breakfastRecipeArray;
    }

    public void setBreakfastRecipeArray(ArrayList<String> breakfastRecipeArray) {
        this.breakfastRecipeArray = breakfastRecipeArray;
    }

    public ArrayList<String> getLunchIngredientArray() {
        return lunchIngredientArray;
    }

    public void setLunchIngredientArray(ArrayList<String> lunchIngredientArray) {
        this.lunchIngredientArray = lunchIngredientArray;
    }

    public ArrayList<String> getLunchRecipeArray() {
        return lunchRecipeArray;
    }

    public void setLunchRecipeArray(ArrayList<String> lunchRecipeArray) {
        this.lunchRecipeArray = lunchRecipeArray;
    }

    public ArrayList<String> getDinnerIngredientArray() {
        return dinnerIngredientArray;
    }

    public void setDinnerIngredientArray(ArrayList<String> dinnerIngredientArray) {
        this.dinnerIngredientArray = dinnerIngredientArray;
    }

    public ArrayList<String> getDinnerRecipeArray() {
        return dinnerRecipeArray;
    }

    public void setDinnerRecipeArray(ArrayList<String> dinnerRecipeArray) {
        this.dinnerRecipeArray = dinnerRecipeArray;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }
}
