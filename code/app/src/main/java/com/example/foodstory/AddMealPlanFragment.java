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
import androidx.navigation.fragment.NavHostFragment;


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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddMealPlanFragment extends Fragment {

    private AddMealPlanFragmentBinding binding;

    String TAG = "Sample";
    FirebaseFirestore dbAddMealPlan;
    ArrayList<MealPlan> mealplans;
    ArrayAdapter<MealPlan> mealPlan_Adapter;


    String[] recipeArray ;
    RecipeClass recipefromdb;
    ArrayList<String> recipeNames;

    String[] ingredientArray;
    Ingredient ingredientfromdb;
    ArrayList<String> ingredientNames;

    TextView breakfast_recipe_select;
    TextView breakfast_ingredient_select;
    TextView lunch_recipe_select;
    TextView lunch_ingredient_select;
    TextView dinner_recipe_select;
    TextView dinner_ingredient_select;

    ArrayList<String> breakfast_recipe_array = new ArrayList<>();
    ArrayList<String> breakfast_ingredient_array = new ArrayList<>();
    ArrayList<String> lunch_recipe_array = new ArrayList<>();
    ArrayList<String> lunch_ingredient_array = new ArrayList<>();
    ArrayList<String> dinner_recipe_array = new ArrayList<>();
    ArrayList<String> dinner_ingredient_array = new ArrayList<>();

    EditText numberOfServings;
    EditText mealPlanName;
    DatePicker mealPlanDate;

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

        breakfast_recipe_select = getView().findViewById(R.id.breakfast_recipe_select);
        breakfast_ingredient_select = getView().findViewById(R.id.breakfast_ingredient_select);
        lunch_recipe_select = getView().findViewById(R.id.lunch_recipe_select);
        lunch_ingredient_select = getView().findViewById(R.id.lunch_ingredient_select);
        dinner_recipe_select = getView().findViewById(R.id.dinner_recipe_select);
        dinner_ingredient_select = getView().findViewById(R.id.dinner_ingredient_select);

        recipeNames = new ArrayList<>();
        ingredientNames = new ArrayList<>();

        dbAddMealPlan.collection("Recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        recipefromdb = document.toObject(RecipeClass.class);
                        recipeNames.add(recipefromdb.getTitle());
                    }
                    breakfast_recipe_array = createRecipeDropdown(breakfast_recipe_select, recipeNames);
                    lunch_recipe_array = createRecipeDropdown(lunch_recipe_select, recipeNames);
                    dinner_recipe_array = createRecipeDropdown(dinner_recipe_select, recipeNames);


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });




        dbAddMealPlan.collection("Ingredients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ingredientfromdb = document.toObject(Ingredient.class);
                        ingredientNames.add(ingredientfromdb.getName());
                    }
                    breakfast_ingredient_array = createIngredientDropdown(breakfast_ingredient_select, ingredientNames);
                    lunch_ingredient_array = createIngredientDropdown(lunch_ingredient_select, ingredientNames);
                    dinner_ingredient_array = createIngredientDropdown(dinner_ingredient_select, ingredientNames);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });

        binding.saveMpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealPlanName = getView().findViewById(R.id.meal_plan_name);
                String mpName = mealPlanName.getText().toString();
                numberOfServings = getView().findViewById(R.id.num_of_servings);
                int numOfServings = Integer.valueOf(numberOfServings.getText().toString());
                mealPlanDate = (DatePicker) getView().findViewById(R.id.meal_plan_datepicker);
                int day = mealPlanDate.getDayOfMonth();
                int month = mealPlanDate.getMonth() ;
                int year = mealPlanDate.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                UUID mealPlanId = UUID.randomUUID();
                String mpDate = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
                MealPlan newMealPlan = new MealPlan(mpName,mpDate,breakfast_ingredient_array,breakfast_recipe_array,lunch_ingredient_array,lunch_recipe_array,
                        dinner_ingredient_array,dinner_recipe_array,numOfServings);

                dbAddMealPlan.collection("MealPlans").document(mpName)
                        .set(newMealPlan)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
                NavHostFragment.findNavController(AddMealPlanFragment.this)
                        .navigate(R.id.action_AddMealPlanFragment_to_MealPlanFragment);

            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddMealPlanFragment.this)
                        .navigate(R.id.action_AddMealPlanFragment_to_MealPlanFragment);
            }
        });





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<String> createRecipeDropdown(TextView breakfast_recipe_select, ArrayList<String> recipeNames){
        ArrayList<Integer> selectedRecList = new ArrayList<>();
        recipeArray = new String[recipeNames.size()];
        for (int i =0; i < recipeNames.size(); i++){
            recipeArray[i] = recipeNames.get(i);
        }
        boolean[] selectedRecipe = new boolean[recipeArray.length];
        ArrayList<String> final_recipe_names = new ArrayList<>();
        breakfast_recipe_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Recipe");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(recipeArray, selectedRecipe, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            // when checkbox selected : Add position  in mpList
                            selectedRecList.add(i);
                            Collections.sort(selectedRecList);
                        } else {
                            // when checkbox unselected:  Remove position from mpList
                            selectedRecList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < selectedRecList.size(); j++) {
                            // concat array value
                            stringBuilder.append(recipeArray[selectedRecList.get(j)]);
                            final_recipe_names.add(recipeArray[selectedRecList.get(j)]);
                            // check condition
                            if (j != selectedRecList.size() - 1) {
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
                        for (int j = 0; j < selectedRecipe.length; j++) {
                            // remove all selection
                            selectedRecipe[j] = false;
                            // clear language list
                            selectedRecList.clear();
                            // clear text view value
                            breakfast_recipe_select.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        return final_recipe_names;




    }

    public ArrayList<String> createIngredientDropdown(TextView breakfast_ingredient_select, ArrayList<String> ingredientNames ){
        ingredientArray = new String[ingredientNames.size()];
        for (int i =0; i < ingredientNames.size(); i++){

            ingredientArray[i] = ingredientNames.get(i);
        }
        ArrayList<String> final_ingredient_names = new ArrayList<>();
        boolean[] selectedIngredient = new boolean[ingredientArray.length];
        ArrayList<Integer> selectedIngList = new ArrayList<>();
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
                            final_ingredient_names.add(ingredientArray[selectedIngList.get(j)]);
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

        return final_ingredient_names;
    }


}
