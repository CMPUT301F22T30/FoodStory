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
 *
 * This class allows displaying custom objects in a list view, in this case, it allows us to
 * display recipes in the listview in RecipeFragment
 */
public class RecipeAdapter extends ArrayAdapter<RecipeClass> {
    private ArrayList<RecipeClass> recipes;
    private Context context;

    /**
     * Constructor for RecipeAdapter
     * @param context - context
     * @param recipes - The arraylist of recipes to display
     */
    public RecipeAdapter(Context context, ArrayList<RecipeClass> recipes){
        super(context,0, recipes);
        this.recipes = recipes;
        this.context = context;
    }

    /**
     *
     * @param convertView - the current view
     * This method checks if the view is valid and if it is, sets the appropriate TextViews with
     *                    information about the recipe attributes
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.recipe_display, parent,false);
        }
        RecipeClass recipe = recipes.get(position);
        TextView recipeTitle = view.findViewById(R.id.title_text);
        TextView recipeCategory = view.findViewById(R.id.category_text);
        TextView recipeTime = view.findViewById(R.id.time_text);
        TextView recipeServings = view.findViewById(R.id.servings_text);
        recipeTitle.setText(recipe.getTitle());
        recipeCategory.setText(recipe.getRecipeCategory());
        recipeTime.setText(recipe.getPrepTime());
        recipeServings.setText(recipe.getRecipeServingsStr());
        return view;
    }
}
