package com.example.foodstory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.foodstory.databinding.AddMealPlanFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddMealPlanFragment extends Fragment {

    private AddMealPlanFragmentBinding binding;
    DatePicker mealPlanDate;
    TextView breakfast_recipe_select;

    FirebaseFirestore dbAddMealPlan;
    ArrayList<MealPlan> mealplans;
    ArrayAdapter<MealPlan> mealPlan_Adapter;
    boolean[] selectedMealPlan;
    ArrayList<Integer> selectedMpList = new ArrayList<>();
    String[] recipeArray ;
    String TAG = "Sample";
    RecipeClass recipefromdb;
    ArrayList<String> recipeNames;

    TextView breakfast_ingredient_select;
    boolean[] selectedIngredient;
    ArrayList<Integer> selectedIngList = new ArrayList<>();
    String[] ingredientArray;
    Ingredient ingredientfromdb;
    ArrayList<String> ingredientNames;
    public AddMealPlanFragment(){
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddMealPlanFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbAddMealPlan = FirebaseFirestore.getInstance();
        CollectionReference mealplanReference = dbAddMealPlan.collection("MealPlans");

        breakfast_recipe_select = getView().findViewById(R.id.breakfast_recipe_select);
        recipeNames = new ArrayList<>();
        dbAddMealPlan.collection("Recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        recipefromdb = document.toObject(RecipeClass.class);
                        Log.d(TAG, "recipename: " + recipefromdb.getTitle());
                        recipeNames.add(recipefromdb.getTitle());
                    }
                    createRecipeDropdown(breakfast_recipe_select, recipeNames);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });


        breakfast_ingredient_select = getView().findViewById(R.id.breakfast_ingredient_select);
        ingredientNames = new ArrayList<>();
        dbAddMealPlan.collection("Ingredients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ingredientfromdb = document.toObject(Ingredient.class);
                        Log.d(TAG, "ingname: " + ingredientfromdb.getName());
                        ingredientNames.add(ingredientfromdb.getName());
                    }
                    createIngredientDropdown(breakfast_ingredient_select, ingredientNames);


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });









    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createRecipeDropdown(TextView breakfast_recipe_select, ArrayList<String> recipeNames){
        recipeArray = new String[recipeNames.size()];
        for (int i =0; i < recipeNames.size(); i++){
            recipeArray[i] = recipeNames.get(i);
        }
        selectedMealPlan = new boolean[recipeArray.length];

        breakfast_recipe_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Recipe");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(recipeArray, selectedMealPlan, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            // when checkbox selected : Add position  in mpList
                            selectedMpList.add(i);
                            Collections.sort(selectedMpList);
                        } else {
                            // when checkbox unselected:  Remove position from mpList
                            selectedMpList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < selectedMpList.size(); j++) {
                            // concat array value
                            stringBuilder.append(recipeArray[selectedMpList.get(j)]);
                            // check condition
                            if (j != selectedMpList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        breakfast_recipe_select.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedMealPlan.length; j++) {
                            // remove all selection
                            selectedMealPlan[j] = false;
                            // clear language list
                            selectedMpList.clear();
                            // clear text view value
                            breakfast_recipe_select.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });


    }

    public void createIngredientDropdown(TextView breakfast_ingredient_select, ArrayList<String> ingredientNames ){
        ingredientArray = new String[ingredientNames.size()];
        for (int i =0; i < ingredientNames.size(); i++){
            Log.d(TAG, "title" + ingredientNames.get(i));
            ingredientArray[i] = ingredientNames.get(i);
        }
        Log.d(TAG, "FINAL ARRAAY SIZE:  " + String.valueOf(ingredientArray.length));
        selectedIngredient = new boolean[ingredientArray.length];

        breakfast_ingredient_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Ingredient");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(ingredientArray, selectedIngredient, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            // when checkbox selected : Add position  in mpList
                            selectedIngList.add(i);
                            Collections.sort(selectedIngList);
                        } else {
                            // when checkbox unselected:  Remove position from mpList
                            selectedIngList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < selectedIngList.size(); j++) {
                            // concat array value
                            stringBuilder.append(ingredientArray[selectedIngList.get(j)]);
                            // check condition
                            if (j != selectedIngList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        breakfast_ingredient_select.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedIngredient.length; j++) {
                            // remove all selection
                            selectedIngredient[j] = false;
                            // clear language list
                            selectedIngList.clear();
                            // clear text view value
                            breakfast_ingredient_select.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }


}
