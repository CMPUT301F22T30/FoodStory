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
    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.ingredient_in_recipe_display, parent,false);
        }
        Ingredient ingredient = ingredients.get(position);
        TextView ingredientTitle= view.findViewById(R.id.ingredient_title);
        TextView ingredientAmount= view.findViewById(R.id.ingredient_amount);
        TextView ingredientCategory = view.findViewById(R.id.ingredient_category);
        ingredientTitle.setText(ingredient.getName());
        ingredientAmount.setText(String.valueOf(ingredient.getAmount()));
        ingredientCategory.setText(ingredient.getCategory());
        return view;
    }
}