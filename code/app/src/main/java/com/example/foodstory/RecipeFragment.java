package com.example.foodstory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.RecipeFragmentBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecipeFragment extends Fragment {
    private RecipeFragmentBinding binding;
    public static final String EXTRA_MESSAGE = "";

    public RecipeFragment(){
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = RecipeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<RecipeClass> recipe_List = new ArrayList<RecipeClass>();
        ArrayAdapter<RecipeClass> recipe_Adapter = new RecipeAdapter(getActivity(),recipe_List);
        ListView recipeList = getView().findViewById(R.id.recipe_list);
        recipeList.setAdapter(recipe_Adapter);

        binding.recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent viewItem = new Intent(RecipeFragment.this, showActivity.class);
                //viewItem.putExtra(EXTRA_MESSAGE, recipe_Adapter.getItem(i));
                NavHostFragment.findNavController(RecipeFragment.this)
                        .navigate(R.id.action_RecipeFragment_to_AddRecipeFragment);
            }
        });

        binding.addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RecipeFragment.this)
                        .navigate(R.id.action_RecipeFragment_to_AddRecipeFragment);
            }
        });

        RecipeClass testRecipe = new RecipeClass("abcd", "cd", 12, "efg", "de", "xyz");
        Date date = new Date();
        Ingredient testIngredient = new Ingredient("Rice", "Describe Rice", date, "Pantry", 20, "Medium", "Perishables");
        testRecipe.addIngredient(testIngredient);
        recipe_List.add(testRecipe);
        recipe_Adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
