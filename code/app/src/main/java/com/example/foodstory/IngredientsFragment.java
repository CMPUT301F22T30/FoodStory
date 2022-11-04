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

/**
 * Defining a new class for IngredientsFragment
 */
public class IngredientsFragment extends Fragment {
    private IngredientFragmentBinding binding;

    /**
     * Defining a new view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = IngredientFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /**
     * Defining a new ingredient list
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Ingredient> ingredients_List = new ArrayList<Ingredient>();
        ArrayAdapter<Ingredient> ingredient_Adapter = new IngredientAdapter(getActivity(),ingredients_List);
        ListView ingredientList= getView().findViewById(R.id.ingredient_list);
        Date date = new Date();
        Ingredient mying = new Ingredient("milk", "chocolate", date, "fridge" , 2, "3" ,"cat");
        ingredients_List.add(mying);
        ingredientList.setAdapter(ingredient_Adapter);
        binding.ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavHostFragment.findNavController(IngredientsFragment.this)
                        .navigate(R.id.action_IngredientFragment_to_AddIngredientFragment);
            }
        });

        binding.addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(IngredientsFragment.this)
                        .navigate(R.id.action_IngredientFragment_to_AddIngredientFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

