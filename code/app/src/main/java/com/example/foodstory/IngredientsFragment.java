package com.example.foodstory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.foodstory.databinding.IngredientFragmentBinding;

import java.util.ArrayList;
import java.util.Date;


public class IngredientsFragment extends Fragment {
    private IngredientFragmentBinding binding;
    private ListView ingredientListView;
    private IngredientAdapter ingredientAdapter;
    private ArrayList<Ingredient> ingredientList ;
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
        ingredientListView = getView().findViewById(R.id.ingredient_list);
        ingredientList = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(getContext(), ingredientList);
        Date date = new Date();
        Ingredient mying = new Ingredient("Milk","Beverage",date,"Fridge",2,"litre","Beverage");
        ingredientList.add(mying);
        ingredientListView.setAdapter(ingredientAdapter);
//        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

