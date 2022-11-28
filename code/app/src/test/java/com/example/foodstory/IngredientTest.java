package com.example.foodstory;


import static org.junit.jupiter.api.Assertions.assertEquals;


//import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class IngredientTest {
    private Ingredient CreateTest(){
        String name = "test_name", description = "test_description", location = "test_location",
                unit = "test_unit", category = "test_category";
        Date bestBefore;
        int amount = 5;

        return new Ingredient(name, description, null, location, amount, unit, category);
    }

    @Test
    public void test_name(){assertEquals("test_name", CreateTest().getName());
    }

    @Test
    public void test_description(){assertEquals("test_description", CreateTest().getDescription());
    }

    @Test
    public void test_location(){assertEquals("test_location", CreateTest().getLocation());
    }

    @Test
    public void test_unit(){assertEquals("test_unit", CreateTest().getUnit());
    }

    @Test
    public void test_category(){assertEquals("test_category", CreateTest().getCategory());
    }

    @Test
    public void test_amount(){assertEquals(5, CreateTest().getAmount());
    }






}
