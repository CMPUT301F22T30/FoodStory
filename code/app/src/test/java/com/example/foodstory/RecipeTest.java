package com.example.foodstory;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

public class RecipeTest {

    @Test
    public void test_title(){
        RecipeClass recipe = new RecipeClass("test_title", "test_prep", 5, "test_category","test_comments", "test_photo");

        assertEquals("test_title", recipe.getTitle());
    }

    @Test

    public void test_prepTime(){
        RecipeClass recipe = new RecipeClass("test_title", "test_prep", 5, "test_category","test_comments", "test_photo");

        assertEquals("test_prep", recipe.getPrepTime());
    }

    @Test

    public void test_numServings(){
        RecipeClass recipe = new RecipeClass("test_title", "test_prep", 5, "test_category","test_comments", "test_photo");

        assertEquals(5, recipe.getNumServings());
    };

    @Test
    public void test_recipeCategory(){
        RecipeClass recipe = new RecipeClass("test_title", "test_prep", 5, "test_category","test_comments", "test_photo");

        assertEquals("test_category", recipe.getRecipeCategory());
    }

    @Test
    public void test_comments(){
        RecipeClass recipe = new RecipeClass("test_title", "test_prep", 5, "test_category","test_comments", "test_photo");

        assertEquals("test_comments", recipe.getComments());
    }

    @Test
    public void test_photo(){
        RecipeClass recipe = new RecipeClass("test_title", "test_prep", 5, "test_category","test_comments", "test_photo");

        assertEquals("test_photo", recipe.getPhoto());
    }




}
