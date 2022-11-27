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

public class RecipeAdapter extends ArrayAdapter<RecipeClass> {
    private ArrayList<RecipeClass> recipes;
    private Context context;
    public RecipeAdapter(Context context, ArrayList<RecipeClass> recipes){
        super(context,0, recipes);
        this.recipes = recipes;
        this.context = context;
    }

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
