package com.example.foodstory;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.DatePicker;
import java.text.DateFormat;
import java.util.Calendar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * This is a fragment that is used to Add an ingredient
 */
public class AddIngredientFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
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

    /**
     * Constructor for the fragment
     */
    public AddIngredientFragment(){
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("callerKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String fragCaller = bundle.getString("parentFragment");
                // Do something with the result
                dbAddIngr = FirebaseFirestore.getInstance();
                if(Objects.equals(fragCaller, "AddRecipeFragment")){
                    Button addIngredient = getView().findViewById(R.id.saveIngrRecButton);
                    Button delIngredient = getView().findViewById(R.id.deleteIngrRecButton);
                    addIngredient.setVisibility(View.VISIBLE);
                    delIngredient.setVisibility(View.VISIBLE);
                    recipe = (RecipeClass) bundle.getSerializable("RecipeObj");
                    ingredient = (Ingredient) bundle.getSerializable("IngredientObj");
                } else if (Objects.equals(fragCaller, "AddIngredientFragment")){
                    Button addIngredient = getView().findViewById(R.id.saveIngrButton);
                    Button delIngredient = getView().findViewById(R.id.deleteIngrButton);
                    addIngredient.setVisibility(View.VISIBLE);
                    delIngredient.setVisibility(View.VISIBLE);
                    ingredient = (Ingredient) bundle.getSerializable("IngredientObj");
                }
                if (ingredient != null) {
                    ingredientName = getView().findViewById(R.id.ingredient_name_editText);
                    ingredientDescription = getView().findViewById(R.id.ingredient_description_editText);
                    ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
                    ingredientLocation = getView().findViewById(R.id.ingredient_location_editText);
                    ingredientAmount = getView().findViewById(R.id.ingredient_amount_editText);
                    ingredientUnit = getView().findViewById(R.id.ingredient_unit_editText);
                    ingredientCategory = getView().findViewById(R.id.ingredient_category_editText);
                    ingredientName.setText(ingredient.getName());
                    ingredientDescription.setText(ingredient.getDescription());
                    ingredientBestBefore.setText(ingredient.getBBDString());
                    ingredientLocation.setText(ingredient.getLocation());
                    ingredientAmount.setText(Integer.toString(ingredient.getAmount()));
                    ingredientUnit.setText(ingredient.getUnit());
                    ingredientCategory.setText(ingredient.getCategory());
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

    /**
     * Defining all the fields of Ingredient class and checking if those fields are in suitable
     * formats.
     * Getting and setting the values of those fields
     *
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //AddIngredientFragment needs to retrieve recipe name to add the ingredient to appropriate recipe.
        dbAddIngr = FirebaseFirestore.getInstance();
        CollectionReference ingrReference = dbAddIngr.collection("Recipes");
        ingredientName = getView().findViewById(R.id.ingredient_name_editText);
        ingredientDescription = getView().findViewById(R.id.ingredient_description_editText);
        ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
        ingredientLocation = getView().findViewById(R.id.ingredient_location_editText);
        ingredientAmount = getView().findViewById(R.id.ingredient_amount_editText);
        ingredientUnit = getView().findViewById(R.id.ingredient_unit_editText);
        ingredientCategory = getView().findViewById(R.id.ingredient_category_editText);

        binding.saveIngrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingr_name = ingredientName.getText().toString();
                ingr_desc = ingredientDescription.getText().toString();
                ingr_bb = ingredientBestBefore.getText().toString();
                ingr_loca = ingredientLocation.getText().toString();
                ingr_amount = String.valueOf(ingredientAmount.getText());
                ingr_unit = ingredientUnit.getText().toString();
                ingr_cate = ingredientCategory.getText().toString();
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
                    if(ingredient != null){
                        dbAddIngr.collection("Ingredients").document(ingredient.getName())
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
                        ingredient.setName(ingr_name);
                        ingredient.setDescription(ingr_desc);
                        ingredient.setBestBefore(ingr_date);
                        ingredient.setLocation(ingr_loca);
                        ingredient.setAmount(ingr_amt);
                        ingredient.setUnit(ingr_unit);
                        ingredient.setCategory(ingr_cate);
                    } else {
                        ingredient = new Ingredient(ingr_name, ingr_desc, ingr_date, ingr_loca,
                                ingr_amt, ingr_unit, ingr_cate);
                    }
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
                if (ingredient != null){
                    dbAddIngr.collection("Ingredients").document(ingredient.getName())
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
                }
                NavHostFragment.findNavController(AddIngredientFragment.this)
                        .navigate(R.id.action_AddIngredientFragment_to_IngredientFragment);
            }
        });

        binding.saveIngrRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingr_name = ingredientName.getText().toString();
                ingr_desc = ingredientDescription.getText().toString();
                ingr_bb = ingredientBestBefore.getText().toString();
                ingr_loca = ingredientLocation.getText().toString();
                ingr_amount = String.valueOf(ingredientAmount.getText());
                ingr_unit = ingredientUnit.getText().toString();
                ingr_cate = ingredientCategory.getText().toString();
                ingr_date = new Date();
                Bundle bundle = new Bundle();
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
                    if (recipe != null) {
                        recipe.addIngredient(ingredient);
                        ingrReference.document(recipe.getTitle()).set(recipe);
                    }

                }
                if (recipe != null) {
                    bundle.putSerializable("recipeObj", recipe);
                    getParentFragmentManager().setFragmentResult("recipeKey", bundle);
                }
                NavHostFragment.findNavController(AddIngredientFragment.this)
                        .navigate(R.id.action_AddIngredientFragment_to_AddRecipeFragment);
            }
        });

        binding.deleteIngrRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipeObj", recipe);
                getParentFragmentManager().setFragmentResult("recipeKey", bundle);
                NavHostFragment.findNavController(AddIngredientFragment.this)
                        .navigate(R.id.action_AddIngredientFragment_to_AddRecipeFragment);
            }
        });

        ImageButton btPickDate = getView().findViewById(R.id.btPickDate);
        // https://www.geeksforgeeks.org/datepickerdialog-in-android/
        btPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(AddIngredientFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String selectedDate = format.format(mCalendar.getTime());
        ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
        ingredientBestBefore.setText(selectedDate);
    }
}
