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

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.foodstory.databinding.AddIngredientFragmentBinding;
import com.example.foodstory.databinding.AddRecipeFragmentBinding;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.Date;

public class AddIngredientFragment extends DialogFragment{
    private EditText ingredientName;
    private EditText ingredientDescription;
    private EditText ingredientBestBefore;
    private EditText ingredientLocation;
    private EditText ingredientAmount;
    private EditText ingredientUnit;
    private EditText ingredientCategory;
    public static String TAG = "Add/Edit Fragment";

    private OnFragmentInteractionListener listener;
    private Ingredient editIngredient;
    private Bundle argsIngredient;

    public interface OnFragmentInteractionListener {

        void onOkPressed(Ingredient newIngredient);
        void onOkPressed(Ingredient editIngredient, int i);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof OnFragmentInteractionListener) {
//            listener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + "This is not the correct fragment!");
//        }
//    }

    public static AddIngredientFragment newInstance(Ingredient ingredient, int position) {
        Bundle args = new Bundle();
        args.putSerializable("Ingredient",  ingredient);
        args.putInt("position", position);
        AddIngredientFragment fragment = new AddIngredientFragment();
        fragment.setArguments(args);
        return fragment;

    }
    @NonNull

    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_ingredient_fragment, null);
        ingredientName = view.findViewById(R.id.ingredient_name_editText);
        ingredientDescription = view.findViewById(R.id.ingredient_description_editText);
        ingredientBestBefore = view.findViewById(R.id.ingredient_bb_editText);
        ingredientLocation = view.findViewById(R.id.ingredient_location_editText);
        ingredientAmount = view.findViewById(R.id.ingredient_amount);
        ingredientUnit = view.findViewById(R.id.ingredient_unit_editText);
        ingredientCategory = view.findViewById(R.id.ingredient_category_editText);
        argsIngredient = getArguments();
        if(argsIngredient != null) {
            editIngredient = (Ingredient) argsIngredient.getSerializable("Ingredient");
            ingredientName.setText(editIngredient.getName().toString());
            ingredientName.setText(editIngredient.getDescription().toString());
            ingredientName.setText(editIngredient.getBestBefore().toString());
            ingredientName.setText(editIngredient.getLocation().toString());
            ingredientName.setText(Integer.toString(editIngredient.getAmount()));
            ingredientName.setText(editIngredient.getUnit().toString());
            ingredientName.setText(editIngredient.getCategory().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            return builder
                    .setView(view)
                    .setTitle("Edit Ingredient")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String name = ingredientName.getText().toString();
                                    String description =  ingredientDescription.getText().toString();
                                    String bestBefore =  ingredientBestBefore.getText().toString();
                                    String location =  ingredientLocation.getText().toString();
                                    int amount = Integer.parseInt(ingredientAmount.getText().toString());
                                    String unit =  ingredientUnit.getText().toString();
                                    String category =  ingredientCategory.getText().toString();
                                    Date date = new Date();
                                    listener.onOkPressed(new Ingredient(name, description, date, location, amount, unit, category));
                                    //listener.onOkPressed(new City(city, province), argsCity.getInt("position"));
                                }
                    }
                    ).create();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            return builder
                    .setView(view)
                    .setTitle("Add Ingredient")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = ingredientName.getText().toString();
                            String description =  ingredientDescription.getText().toString();
                            String bestBefore =  ingredientBestBefore.getText().toString();
                            String location =  ingredientLocation.getText().toString();
                            int amount = Integer.parseInt(ingredientAmount.getText().toString());
                            String unit =  ingredientUnit.getText().toString();
                            String category =  ingredientCategory.getText().toString();
                            Date date = new Date();
                            listener.onOkPressed(new Ingredient(name, description, date, location, amount, unit, category));
                        }
                    }
        ).create();

        }
    }
}
