package com.example.foodstory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.IngredientFragmentBinding;

import java.util.ArrayList;
import java.util.Date;


public class IngredientsFragment extends Fragment {
    private IngredientFragmentBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = IngredientFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Ingredient> ingredients_List = new ArrayList<Ingredient>();
        ArrayAdapter<Ingredient> ingredient_Adapter = new IngredientAdapter(getActivity(),ingredients_List);
        ListView IngredientList = getView().findViewById(R.id.ingredient_list);
        Date date = new Date();
        Ingredient mying = new Ingredient("milk", "chocolate", date, "fridge" , 2, "3" ,"cat");
        ingredients_List.add(mying);
        IngredientList.setAdapter(ingredient_Adapter);

//        binding.recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //Intent viewItem = new Intent(RecipeFragment.this, showActivity.class);
//                //viewItem.putExtra(EXTRA_MESSAGE, recipe_Adapter.getItem(i));
//                NavHostFragment.findNavController(RecipeFragment.this)
//                        .navigate(R.id.action_RecipeFragment_to_AddRecipeFragment);
//            }
//        });

//        binding.addRecipeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(RecipeFragment.this)
//                        .navigate(R.id.action_RecipeFragment_to_AddRecipeFragment);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

