package com.example.foodstory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
    private ArrayList<Ingredient> ingredientDataSet;
    private Context context;

    /**
     * Constructor for the Adapter
     * */
    public IngredientAdapter(Context context, ArrayList<Ingredient> dataSet ){
        super(context,0,dataSet);
        this.context = context;
        this.ingredientDataSet = dataSet;
    }

    /**
     * Method from the ArrayAdapter superclass for initializing and binding all
     * the views in each item of the list view.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ingredient, parent, false);
        }

        // Get the FoodItem Object of the ListView
        Ingredient ingredient = ingredientDataSet.get(position);


        // Get reference to food name field and set its value.
        TextView iName = convertView.findViewById(R.id.ingredientName);
        iName.setText(ingredient.getName());


        // Get reference to food quantity field and set its value.
        TextView iAmount = convertView.findViewById(R.id.ingredientAmount);
        iAmount.setText(String.valueOf(ingredient.getAmount()));

        // Get reference to food cost field and set its value.
        TextView iUnit = convertView.findViewById(R.id.ingredientUnit);
        iUnit.setText(String.valueOf(ingredient.getUnit()));


        return convertView;
    }



}
