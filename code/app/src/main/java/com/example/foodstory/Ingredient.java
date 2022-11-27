package com.example.foodstory;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

public class Ingredient implements Serializable{

    // initialize the attributes for the FoodItem
    private String name;
    private String description ;
    private Date bestBefore;
    private String location;
    private int amount;
    private String unit;
    private String category;

    /**
     * Constructor without arguments
     * */
    public Ingredient(){
    }


    /**
     * Constructor with the arguments
     **/
    public Ingredient(String name, String description, Date bestBefore, String location, int amount, String unit, String category) {
        this.name = name;
        this.description = description;
        this.bestBefore = bestBefore;
        this.location = location;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBestBefore() {
        return bestBefore;
    }
    
    public String getBBDString(){
        if (bestBefore != null) {
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            return formatter.format(this.getBestBefore());
        } else {
            String placeHolder = "";
            return placeHolder;
        }
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
