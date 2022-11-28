package com.example.foodstory;

import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MealPlan implements Serializable {

    private String mealPlanHeading = "Meal Plan";
    private String mealPlanDate;
    private UUID mealPlanId = UUID.randomUUID();
    private ArrayList<String> breakfastArray;
    private ArrayList<String> lunchArray;
    private ArrayList<String> dinnerArray;


    public String getMealPlanHeading() {
        return mealPlanHeading;
    }

    public String getMealPlanDate() {
        return mealPlanDate;
    }

    public void setMealPlanDate(String mealPlanDate) {
        this.mealPlanDate = mealPlanDate;
    }

    public UUID getMealPlanId(){
        return mealPlanId;
    }

    public void setBreakfastArray(ArrayList<String> breakfastArr){
        this.breakfastArray = breakfastArr;
    }
    public void setLunchArray(ArrayList<String> lunchArr){
        this.lunchArray = lunchArr;
    }
    public void setDinnerArray(ArrayList<String> dinnerArr){
        this.dinnerArray = dinnerArr;
    }

    public ArrayList<String> getBreakfastArray(){ return this.breakfastArray; }

    public ArrayList<String> getLunchArray(){return this.lunchArray; }

    public ArrayList<String> getDinner(){return this.dinnerArray; }



}
