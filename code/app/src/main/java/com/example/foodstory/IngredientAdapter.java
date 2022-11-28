package com.example.foodstory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.DateFormat;

/**
 *
 * This class allows displaying custom objects in a list view, in this case, it allows us to
 * display ingredients in the listview in IngredientFragment
 */
public class IngredientAdapter extends ArrayAdapter<Ingredient> {
    private ArrayList<Ingredient> ingredients;
    private Context context;

    /**
     * Constructor for IngredientAdapter
     * @param context - context
     * @param ingredients - The arraylist of ingredients to display
     */
    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }

    /**
     *
     * @param convertView - the current view
     * This method checks if the view is valid and if it is, sets the appropriate TextViews with
     *                    information about the ingredient attributes
     */
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
        TextView ingredientBbd= view.findViewById(R.id.ingredient_bbd);
        TextView ingredientLocation= view.findViewById(R.id.ingredient_location);
        TextView ingredientCategory = view.findViewById(R.id.ingredient_category);
        ingredientTitle.setText(ingredient.getName());
        ingredientBbd.setText(ingredient.getBBDString());
        ingredientLocation.setText(ingredient.getLocation());
        ingredientCategory.setText(ingredient.getCategory());
        return view;
    }
}