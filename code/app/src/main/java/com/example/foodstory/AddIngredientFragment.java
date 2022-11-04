package com.example.foodstory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "This is not the correct fragment!");
        }
    }

    public static AddIngredientFragment newInstance(Ingredient ingredient, int position) {
        Bundle args = new Bundle();
        args.putSerializable("Ingredient", (Serializable) ingredient);
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
            //editCity = (City) argsCity.getSerializable("city");
            //cityName.setText(editCity.getCity().toString());
            //provinceName.setText(editCity.getProvince().toString());
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
                    }).create();
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


=======
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
