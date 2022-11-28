package com.example.foodstory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class RecipeFragment extends Fragment {
    private RecipeFragmentBinding binding;
    public static final String EXTRA_MESSAGE = "";
    FirebaseFirestore dbRecipeDisp;
    public static String TAG = "";
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
        dbRecipeDisp = FirebaseFirestore.getInstance();
        CollectionReference recipeReference = dbRecipeDisp.collection("Recipes");

        binding.recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //https://developer.android.com/guide/fragments/communicate

                RecipeClass passedRecipe = recipe_List.get(i);
                Bundle recipe = new Bundle();
                //bundle key = "recipe"
                recipe.putSerializable("recipeObj", passedRecipe);
                //request key = "recipeKey"

                recipe.putString("recipeTitle", passedRecipe.getTitle());
                getParentFragmentManager().setFragmentResult("recipeKey", recipe);
                NavHostFragment.findNavController(RecipeFragment.this)
                        .navigate(R.id.action_RecipeFragment_to_AddRecipeFragment);
            }
        });

        Button Sort_title = getView().findViewById(R.id.sort_titleButton);
        Sort_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(recipe_List, new Comparator<RecipeClass>() {
                    @Override
                    public int compare(RecipeClass recipe, RecipeClass t1) {
                        return recipe.getTitle().compareTo(t1.getTitle());
                    }
                });
                recipe_Adapter.notifyDataSetChanged();
            }
        });

        Button Sort_Category = getView().findViewById(R.id.sort_categoryButton);
        Sort_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(recipe_List, new Comparator<RecipeClass>() {
                    @Override
                    public int compare(RecipeClass recipe, RecipeClass t1) {
                        if (recipe.getRecipeCategory() == "" || t1.getRecipeCategory() == "") {
                            return 0;
                        } else {
                            return recipe.getRecipeCategory().compareTo(t1.getRecipeCategory());
                        }
                    }
                });
                recipe_Adapter.notifyDataSetChanged();
            }
        });

        Button Sort_prepTime = getView().findViewById(R.id.sort_PreptimeButton);
        Sort_prepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(recipe_List, new Comparator<RecipeClass>() {
                    @Override
                    public int compare(RecipeClass recipe, RecipeClass t1) {
                        int preptime1 = Integer.valueOf(recipe.getPrepTime());
                        int preptime2 = Integer.valueOf(t1.getPrepTime());
                        if (preptime1 ==preptime2 || recipe.getPrepTime() == "" || t1.getPrepTime() == ""){
                            return 0;
                        } else if (preptime1 > preptime2){
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                recipe_Adapter.notifyDataSetChanged();
            }
        });

        Button Sort_servings = getView().findViewById(R.id.sort_ServingsButton);
        Sort_servings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(recipe_List, new Comparator<RecipeClass>() {
                    @Override
                    public int compare(RecipeClass recipe, RecipeClass t1) {
                        int serve1 = recipe.getNumServings();
                        int serve2 = t1.getNumServings();
                        if (serve1 ==serve2 || recipe.getRecipeServingsStr() == "" || t1.getRecipeServingsStr() == ""){
                            return 0;
                        } else if (serve1 > serve2){
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                recipe_Adapter.notifyDataSetChanged();
            }
        });

        binding.addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RecipeFragment.this)
                        .navigate(R.id.action_RecipeFragment_to_AddRecipeFragment);
            }
        });

        binding.RecipetoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RecipeFragment.this)
                        .navigate(R.id.action_RecipeFragment_to_HomeFragment);
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
                    RecipeClass recipe = doc.toObject(RecipeClass.class);
                    //String title = doc.getId();
                    //String prepTime = (String) doc.getData().get("prepTime");
                    //String nServings = (String) doc.getData().get("recipeServingsStr");
                    //int numServings = Integer.valueOf(nServings);
                    //String recipeCategory = (String) doc.getData().get("recipeCategory");
                    //String comments = (String) doc.getData().get("comments");
                    //String photo = (String) doc.getData().get("photo");
                    recipe_List.add(recipe);
                    //recipe_List.add(new RecipeClass(title, prepTime, numServings, recipeCategory, comments, photo));
                }
                recipe_Adapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
