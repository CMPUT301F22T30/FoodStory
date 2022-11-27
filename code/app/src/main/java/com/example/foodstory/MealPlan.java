package com.example.foodstory;

import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

public class MealPlan {

    private String mealPlanHeading = "Meal Plan";
    private String mealPlanDate;


    public String getMealPlanHeading() {
        return mealPlanHeading;
    }

    public String getMealPlanDate() {
        return mealPlanDate;
    }

    public void setMealPlanDate(String mealPlanDate) {
        this.mealPlanDate = mealPlanDate;
    }
}
