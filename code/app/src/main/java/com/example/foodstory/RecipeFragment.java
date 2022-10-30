package com.example.foodstory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodstory.databinding.RecipeFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {
    private RecipeFragmentBinding binding;

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
        RecipeClass testRecipe = new RecipeClass("abcd", "cd", 12, "efg", "de", "xyz");
        recipe_List.add(testRecipe);
        recipe_Adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
