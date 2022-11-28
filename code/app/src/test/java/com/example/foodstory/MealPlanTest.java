package com.example.foodstory;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class MealPlanTest {
    private MealPlan CreateTest() {
        String mealPlanName = "test_name", mealPlanDate = "test_date";
        ArrayList<String> breakfastIngredientArray = new ArrayList<>();
        {
            breakfastIngredientArray.add("a");
            breakfastIngredientArray.add("b");
            breakfastIngredientArray.add("c");
        }
        ArrayList<String> breakfastRecipeArray = new ArrayList<>();
        {
            breakfastRecipeArray.add("d");
            breakfastRecipeArray.add("e");
            breakfastRecipeArray.add("f");
        }

        ArrayList<String> lunchIngredientArray = new ArrayList<>();
        {
            lunchIngredientArray.add("g");
            lunchIngredientArray.add("h");
            lunchIngredientArray.add("i");
        }
        ArrayList<String> lunchRecipeArray = new ArrayList<>();
        {
            lunchRecipeArray.add("j");
            lunchRecipeArray.add("k");
            lunchRecipeArray.add("l");
        }
        ArrayList<String> dinnerIngredientArray = new ArrayList<>();
        {
            dinnerIngredientArray.add("m");
            dinnerIngredientArray.add("n");
            dinnerIngredientArray.add("o");
        }
        ArrayList<String> dinnerRecipeArray = new ArrayList<>();
        {
            dinnerRecipeArray.add("p");
            dinnerRecipeArray.add("q");
            dinnerRecipeArray.add("r");
        }

        int numOfPeople = 5;

        return new MealPlan(mealPlanName, mealPlanDate, breakfastIngredientArray, breakfastRecipeArray, lunchIngredientArray, lunchRecipeArray, dinnerIngredientArray, dinnerRecipeArray, numOfPeople);
    }
        @Test
        public void test_name(){
            assertEquals("test_name", CreateTest().getMealPlanName());
        }

        @Test
        public void test_date(){
            assertEquals("test_date", CreateTest().getMealPlanDate());
        }

        @Test
        public void test_breakfastIngredientArray(){
            ArrayList<String> arr;
            arr = CreateTest().getBreakfastIngredientArray();
            assertEquals("a", arr.get(0));
            assertEquals("b", arr.get(1));
        }

        @Test
        public void test_breakfastRecipeArray(){
            ArrayList<String> arr1;
            arr1 = CreateTest().getBreakfastRecipeArray();
            assertEquals("d", arr1.get(0));
            assertEquals("e", arr1.get(1));
        }

    @Test
    public void test_LunchIngredient(){
        ArrayList<String> arr1;
        arr1 = CreateTest().getLunchIngredientArray();
        assertEquals("g", arr1.get(0));
        assertEquals("h", arr1.get(1));
    }

    @Test
    public void test_LunchRecipe(){
        ArrayList<String> arr1;
        arr1 = CreateTest().getLunchRecipeArray();
        assertEquals("j", arr1.get(0));
        assertEquals("k", arr1.get(1));
    }

    @Test
    public void test_DinnerIngredient(){
        ArrayList<String> arr1;
        arr1 = CreateTest().getDinnerIngredientArray();
        assertEquals("m", arr1.get(0));
        assertEquals("n", arr1.get(1));
    }

    @Test
    public void test_DinnerRecipe(){
        ArrayList<String> arr1;
        arr1 = CreateTest().getDinnerRecipeArray();
        assertEquals("p", arr1.get(0));
        assertEquals("q", arr1.get(1));
    }

    @Test
    public void test_NumberOfPeople(){
        assertEquals(5, CreateTest().getNumOfPeople());
    }




}
