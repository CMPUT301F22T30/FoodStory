package com.example.foodstory;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.RecipeFragmentBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;


/**
 * Defining the RecipeFragment which extends Fragment class
 */
public class RecipeFragment extends Fragment {
    private RecipeFragmentBinding binding;
    public static final String EXTRA_MESSAGE = "";
    FirebaseFirestore dbRecipeDisp;
    public static String TAG = "";

    /**
     * Defining the constructor for RecipeFragment class
     */
    public RecipeFragment(){
    }

    /**
     * Creating view for the recipe fragment
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

        binding = RecipeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /**
     * On creating the view defining a recipe_list which stores all the recipes
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<RecipeClass> recipe_List = new ArrayList<RecipeClass>();
        ArrayAdapter<RecipeClass> recipe_Adapter = new RecipeAdapter(getActivity(),recipe_List);
        ListView recipeList = getView().findViewById(R.id.recipe_list);
        recipeList.setAdapter(recipe_Adapter);
        dbRecipeDisp = FirebaseFirestore.getInstance();
        CollectionReference recipeReference = dbRecipeDisp.collection("Recipes");

        binding.recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Defining NavController to take the sure to AddRecipeFragment from RecipeFragment
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //https://developer.android.com/guide/fragments/communicate

                RecipeClass passedRecipe = recipe_List.get(i);
                Bundle recipe = new Bundle();
                //bundle key = "recipe"
                //recipe.putSerializable("recipe", passedRecipe);
                //request key = "recipeKey"

                recipe.putString("recipeTitle", passedRecipe.getTitle());
                getParentFragmentManager().setFragmentResult("recipeKey", recipe);
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


        //This method refreshes the Recipe List soon as an event is recorded in the firestore data base
        recipeReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                recipe_List.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    //Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    String title = doc.getId();
                    String prepTime = (String) doc.getData().get("Recipe Prep");
                    String nServings = (String) doc.getData().get("Recipe Servings");
                    int numServings = Integer.valueOf(nServings);
                    String recipeCategory = (String) doc.getData().get("Recipe Category");
                    String comments = (String) doc.getData().get("Recipe Comments");
                    String photo = (String) doc.getData().get("Recipe Photo");
                    recipe_List.add(new RecipeClass(title, prepTime, numServings, recipeCategory, comments, photo));
                }
                recipe_Adapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });


        //Test objects add
        /**
         * Creating test recipe and test ingredients
         */
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
