package com.example.foodstory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.foodstory.databinding.AddIngredientFragmentBinding;
import com.example.foodstory.databinding.AddRecipeFragmentBinding;

import java.util.ArrayList;
import java.util.Date;

public class AddIngredientFragment extends Fragment {

    private AddIngredientFragmentBinding binding;
    EditText ingredient_title;
    EditText ingredient_description;
    DatePicker ingredient_date;
    EditText ingredient_location;
    EditText ingredient_amount;
    EditText ingredient_unit;
    EditText ingredient_cost;

    public AddIngredientFragment(){
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddIngredientFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ArrayAdapter<Ingredient> ingredient_Adapter = new IngredientAdapter(getActivity(), ingredients);
        //ListView ingredientList = getView().findViewById(R.id.recipe_ingredients_list);
        //ingredientList.setAdapter(ingredient_Adapter);

//        .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                title_recipe = getView().findViewById(R.id.recipe_title_editText);
//                prep_time_recipe = getView().findViewById(R.id.recipe_preptime_editText);
//                category_recipe = getView().findViewById(R.id.recipe_category_editText);
//                servings_recipe = getView().findViewById(R.id.recipe_num_servings_editText);
//                comments_recipe = getView().findViewById(R.id.recipe_comments_editText);
//                photo_recipe = getView().findViewById(R.id.recipe_photos_editText);
//                String rec_name = title_recipe.getText().toString();
//                String rec_prep = prep_time_recipe.getText().toString();
//                int rec_serv = Integer.valueOf(servings_recipe.getText().toString());
//                String rec_cate = category_recipe.getText().toString();
//                String rec_comm = comments_recipe.getText().toString();
//                String rec_phot = photo_recipe.getText().toString();
//                RecipeClass recipe = new RecipeClass(rec_name, rec_prep, rec_serv, rec_cate, rec_comm, rec_phot);
//
//            }
//        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
