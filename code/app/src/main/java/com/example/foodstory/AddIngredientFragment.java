package com.example.foodstory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.RecipeFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.foodstory.databinding.AddIngredientFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class AddIngredientFragment extends Fragment{
    private AddIngredientFragmentBinding binding;
    public static final String EXTRA_MESSAGE = "";
    FirebaseFirestore dbAddIngr;
    public static String TAG = "";
    EditText ingredientName;
    EditText ingredientDescription;
    EditText ingredientBestBefore;
    EditText ingredientLocation;
    EditText ingredientAmount;
    EditText ingredientUnit;
    EditText ingredientCategory;
    String ingr_name;
    String ingr_desc;
    String ingr_bb;
    String ingr_loca;
    String ingr_amount;
    String ingr_unit;
    String ingr_cate;
    int ingr_amt;
    Date ingr_date;
    RecipeClass recipe;
    Ingredient ingredient;

    public AddIngredientFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("callerKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                //bundle key = "recipe"
                //recipe.putSerializable("recipe", passedRecipe);
                //request key = "recipeKey"
                //RecipeClass recipe = (RecipeClass) bundle.getBundle("recipe");
                //getParentFragmentManager().setFragmentResult("recipeKey", recipe);
                String fragCaller = bundle.getString("parentFragment");
//                String expected = "AddRecipeFragment";
                // Do something with the result
                dbAddIngr = FirebaseFirestore.getInstance();
                if(fragCaller == "AddRecipeFragment"){
                    Button addIngredient = getView().findViewById(R.id.saveIngrRecButton);
                    Button delIngredient = getView().findViewById(R.id.deleteIngrRecButton);
                    addIngredient.setVisibility(View.VISIBLE);
                    delIngredient.setVisibility(View.VISIBLE);
                    if (bundle.getString("isRecipe").equals("Yes")){
                        recipe = (RecipeClass) bundle.getSerializable("RecipeObj");
                    } else {
                        String rec_name = bundle.getString("recipeName");
                        recipe = new RecipeClass(rec_name);
                    }
                } else if (fragCaller == "AddIngredientFragment"){
                    Button addIngredient = getView().findViewById(R.id.saveIngrButton);
                    Button delIngredient = getView().findViewById(R.id.deleteIngrButton);
                    addIngredient.setVisibility(View.VISIBLE);
                    delIngredient.setVisibility(View.VISIBLE);
                    String ingredient_name = bundle.getString("ingredientName");
//                    dbAddIngr = FirebaseFirestore.getInstance();
                    DocumentReference docRef = dbAddIngr.collection("Ingredients").document(ingredient_name);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    ingredientName = getView().findViewById(R.id.ingredient_name_editText);
                                    ingredientDescription = getView().findViewById(R.id.ingredient_description_editText);
                                    ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
                                    ingredientLocation = getView().findViewById(R.id.ingredient_location_editText);
                                    ingredientAmount = getView().findViewById(R.id.ingredient_amount_editText);
                                    ingredientUnit = getView().findViewById(R.id.ingredient_unit_editText);
                                    ingredientCategory = getView().findViewById(R.id.ingredient_category_editText);

                                    String ingr_desc = document.getString("Ingredient Description");
                                    String ingr_bb = document.getString("Ingredient BestBefore");
                                    String ingr_loca = document.getString("Ingredient Category");
                                    String ingr_amount = document.getString("Ingredient Amount");
                                    String ingr_unit = document.getString("Ingredient Unit");
                                    String ingr_cate = document.getString("Ingredient Category");

                                    ingredientName.setText(ingredient_name);
                                    ingredientDescription.setText(ingr_desc);
                                    ingredientBestBefore.setText(ingr_bb);
                                    ingredientLocation.setText(ingr_loca);
                                    ingredientAmount.setText(ingr_amount);
                                    ingredientUnit.setText(ingr_unit);
                                    ingredientCategory.setText(ingr_cate);

                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }
            }
        });

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

        //AddIngredientFragment needs to retrieve recipe name to add the ingredient to appropriate recipe.
        dbAddIngr = FirebaseFirestore.getInstance();
        CollectionReference ingrReference = dbAddIngr.collection("Recipes");


        binding.saveIngrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientName = getView().findViewById(R.id.ingredient_name_editText);
                ingredientDescription = getView().findViewById(R.id.ingredient_description_editText);
                ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
                ingredientLocation = getView().findViewById(R.id.ingredient_location_editText);
                ingredientAmount = getView().findViewById(R.id.ingredient_amount_editText);
                ingredientUnit = getView().findViewById(R.id.ingredient_unit_editText);
                ingredientCategory = getView().findViewById(R.id.ingredient_category_editText);
                ingr_name = ingredientName.getText().toString();
                ingr_desc = ingredientDescription.getText().toString();
                ingr_bb = ingredientBestBefore.getText().toString();
                ingr_loca = ingredientLocation.getText().toString();
                ingr_amount = String.valueOf(ingredientAmount.getText());
                ingr_unit = ingredientUnit.getText().toString();
                ingr_cate = ingredientCategory.getText().toString();
//                HashMap<String, String> data = new HashMap<>();
                ingr_date = new Date();
                if (ingr_name.length()>0){
                    try {
                        ingr_date = new SimpleDateFormat("yyyy/MM/dd").parse(ingr_bb);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (String.valueOf(ingr_amount).equals("")) {
                        ingr_amt = 0;
                    } else {
                        ingr_amt = Integer.valueOf(ingr_amount);
                    }
                    ingredient = new Ingredient(ingr_name, ingr_desc, ingr_date, ingr_loca,
                            ingr_amt, ingr_unit, ingr_cate);
//                    data.put("Ingredient Description", ingr_desc);
//                    data.put("Ingredient BestBefore", ingr_bb);
//                    data.put("Ingredient Location", ingr_loca);
//                    data.put("Ingredient Amount", ingr_amount);
//                    data.put("Ingredient Unit", ingr_unit);
//                    data.put("Ingredient Category", ingr_cate);
                    dbAddIngr.collection("Ingredients").document(ingr_name)
                            .set(ingredient)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });
                }
                NavHostFragment.findNavController(AddIngredientFragment.this)
                        .navigate(R.id.action_AddIngredientFragment_to_IngredientFragment);
            }
        });

        binding.deleteIngrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientName = getView().findViewById(R.id.ingredient_name_editText);
                ingredientDescription = getView().findViewById(R.id.ingredient_description_editText);
                ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
                ingredientLocation = getView().findViewById(R.id.ingredient_location_editText);
                ingredientAmount = getView().findViewById(R.id.ingredient_amount_editText);
                ingredientUnit = getView().findViewById(R.id.ingredient_unit_editText);
                ingredientCategory = getView().findViewById(R.id.ingredient_category_editText);
                final String ingr_name = ingredientName.getText().toString();
                if (ingr_name.length()>0){
                    dbAddIngr.collection("Ingredients").document(ingr_name)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Data has been removed successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Data could not be removed!", e);
                                }
                            });
                    ingredientName.setText("");
                    ingredientDescription.setText("");
                    ingredientBestBefore.setText("");
                    ingredientLocation.setText("");
                    ingredientAmount.setText("");
                    ingredientUnit.setText("");
                    ingredientCategory.setText("");
                }
                NavHostFragment.findNavController(AddIngredientFragment.this)
                        .navigate(R.id.action_AddIngredientFragment_to_IngredientFragment);
            }
        });

        binding.saveIngrRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                NavHostFragment.findNavController(AddIngredientFragment.this)
                        .navigate(R.id.action_AddIngredientFragment_to_AddRecipeFragment);
            }
        });

        binding.deleteIngrRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientName = getView().findViewById(R.id.ingredient_name_editText);
                ingredientDescription = getView().findViewById(R.id.ingredient_description_editText);
                ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
                ingredientLocation = getView().findViewById(R.id.ingredient_location_editText);
                ingredientAmount = getView().findViewById(R.id.ingredient_amount_editText);
                ingredientUnit = getView().findViewById(R.id.ingredient_unit_editText);
                ingredientCategory = getView().findViewById(R.id.ingredient_category_editText);
                final String ingr_name = ingredientName.getText().toString();
                if (ingr_name.length()>0){
                    dbAddIngr.collection("Ingredients").document(ingr_name)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Data has been removed successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Data could not be removed!", e);
                                }
                            });
                    ingredientName.setText("");
                    ingredientDescription.setText("");
                    ingredientBestBefore.setText("");
                    ingredientLocation.setText("");
                    ingredientAmount.setText("");
                    ingredientUnit.setText("");
                    ingredientCategory.setText("");
                }
                NavHostFragment.findNavController(AddIngredientFragment.this)
                        .navigate(R.id.action_AddIngredientFragment_to_AddRecipeFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
