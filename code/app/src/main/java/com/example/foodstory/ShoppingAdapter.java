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

/**
 * Adapter class to hold the ingredient in the shopping list
 */
public class ShoppingAdapter extends ArrayAdapter<Ingredient> {
    private ArrayList<Ingredient> ingredients;
    private Context context;

    public ShoppingAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;
        view = LayoutInflater.from(context).inflate(R.layout.ingredient_in_shoppinglist_display, parent,false);
        Ingredient ingredient = ingredients.get(position);
        TextView ingredientTitle= view.findViewById(R.id.ingredient_title);
        TextView ingredientdesc = view.findViewById(R.id.ingredient_description);
        TextView ingredientamount= view.findViewById(R.id.ingredient_amount);
        TextView ingredientCategory = view.findViewById(R.id.ingredient_category);
        TextView ingredientUnit = view.findViewById(R.id.ingredient_unit);
        ingredientTitle.setText(ingredient.getName());
        ingredientamount.setText(String.valueOf(ingredient.getAmount()));
        ingredientdesc.setText(ingredient.getDescription());
        ingredientCategory.setText(ingredient.getCategory());
        ingredientUnit.setText(String.valueOf(ingredient.getUnit()));
        return view;
    }
}
