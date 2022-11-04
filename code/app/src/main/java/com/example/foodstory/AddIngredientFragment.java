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
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.RecipeFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.foodstory.databinding.AddIngredientFragmentBinding;

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

        //AddIngredientFragment needs to retrieve recipe name to add the ingredient to appropriate recipe.

        //ArrayList<RecipeClass> recipe_List = new ArrayList<RecipeClass>();
        //ArrayAdapter<RecipeClass> recipe_Adapter = new RecipeAdapter(getActivity(),recipe_List);
        //ListView recipeList = getView().findViewById(R.id.recipe_list);
        //recipeList.setAdapter(recipe_Adapter);
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
                final String ingr_name = ingredientName.getText().toString();
                final String ingr_desc = ingredientDescription.getText().toString();
                final String ingr_bb = ingredientBestBefore.getText().toString();
                final String ingr_loca = ingredientLocation.getText().toString();
                final String ingr_amount = String.valueOf(ingredientAmount.getText());
                final String ingr_unit = ingredientUnit.getText().toString();
                final String ingr_cate = ingredientCategory.getText().toString();
//                RecipeClass recipe = new RecipeClass(rec_name, rec_prep, rec_serv, rec_cate, rec_comm, rec_phot);
                HashMap<String, String> data = new HashMap<>();
                if (ingr_name.length()>0){
                    data.put("Ingredient Description", ingr_desc);
                    data.put("Ingredient BestBefore", ingr_bb);
                    data.put("Ingredient Location", ingr_loca);
                    data.put("Ingredient Amount", ingr_amount);
                    data.put("Ingredient Unit", ingr_unit);
                    data.put("Ingredient Category", ingr_cate);
                    dbAddIngr.collection("Ingredients").document(ingr_name)
                            .set(data)
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

        binding.deleteIngrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        //This method refreshes the Recipe List soon as an event is recorded in the firestore data base
//        recipeReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
//                    FirebaseFirestoreException error) {
//                recipe_List.clear();
//                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
//                {
//                    //Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
//                    String title = doc.getId();
//                    String prepTime = (String) doc.getData().get("Recipe Prep");
//                    String nServings = (String) doc.getData().get("Recipe Servings");
//                    int numServings = Integer.valueOf(nServings);
//                    String recipeCategory = (String) doc.getData().get("Recipe Category");
//                    String comments = (String) doc.getData().get("Recipe Comments");
//                    String photo = (String) doc.getData().get("Recipe Photo");
//                    recipe_List.add(new RecipeClass(title, prepTime, numServings, recipeCategory, comments, photo)); // Adding the cities and provinces from FireStore
//                }
//                recipe_Adapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
//            }
//        });


        //Test objects add
//        RecipeClass testRecipe = new RecipeClass("abcd", "cd", 12, "efg", "de", "xyz");
//        Date date = new Date();
//        Ingredient testIngredient = new Ingredient("Rice", "Describe Rice", date, "Pantry", 20, "Medium", "Perishables");
//        testRecipe.addIngredient(testIngredient);
//        recipe_List.add(testRecipe);
//        recipe_Adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

//public class AddIngredientFragment extends DialogFragment{
//    private EditText ingredientName;
//    private EditText ingredientDescription;
//    private EditText ingredientBestBefore;
//    private EditText ingredientLocation;
//    private EditText ingredientAmount;
//    private EditText ingredientUnit;
//    private EditText ingredientCategory;
//    public static String TAG = "Add/Edit Fragment";
//
//    private OnFragmentInteractionListener listener;
//    private Ingredient editIngredient;
//    private Bundle argsIngredient;
//
//    public interface OnFragmentInteractionListener {
//
//        void onOkPressed();
//
//        void onOkPressed(Ingredient newIngredient);
//        void onOkPressed(Ingredient editIngredient, int i);
//    }
//
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        if(context instanceof OnFragmentInteractionListener) {
////            listener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString() + "This is not the correct fragment!");
////        }
////    }
//
//    public static AddIngredientFragment newInstance(Ingredient ingredient, int position) {
//        Bundle args = new Bundle();
//        args.putSerializable("Ingredient",  ingredient);
//        args.putInt("position", position);
//        AddIngredientFragment fragment = new AddIngredientFragment();
//        fragment.setArguments(args);
//        return fragment;
//
//    }
//    @NonNull
//
//    @Override
//
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_ingredient_fragment, null);
//        ingredientName = view.findViewById(R.id.ingredient_name_editText);
//        ingredientDescription = view.findViewById(R.id.ingredient_description_editText);
//        ingredientBestBefore = view.findViewById(R.id.ingredient_bb_editText);
//        ingredientLocation = view.findViewById(R.id.ingredient_location_editText);
//        ingredientAmount = view.findViewById(R.id.ingredient_amount);
//        ingredientUnit = view.findViewById(R.id.ingredient_unit_editText);
//        ingredientCategory = view.findViewById(R.id.ingredient_category_editText);
//        argsIngredient = getArguments();
//        if(argsIngredient != null) {
//            editIngredient = (Ingredient) argsIngredient.getSerializable("Ingredient");
//            ingredientName.setText(editIngredient.getName().toString());
//            ingredientName.setText(editIngredient.getDescription().toString());
//            ingredientName.setText(editIngredient.getBestBefore().toString());
//            ingredientName.setText(editIngredient.getLocation().toString());
//            ingredientName.setText(Integer.toString(editIngredient.getAmount()));
//            ingredientName.setText(editIngredient.getUnit().toString());
//            ingredientName.setText(editIngredient.getCategory().toString());
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            return builder
//                    .setView(view)
//                    .setTitle("Edit Ingredient")
//                    .setNegativeButton("Cancel", null)
//                    .setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    String name = ingredientName.getText().toString();
//                                    String description =  ingredientDescription.getText().toString();
//                                    String bestBefore =  ingredientBestBefore.getText().toString();
//                                    String location =  ingredientLocation.getText().toString();
//                                    int amount = Integer.parseInt(ingredientAmount.getText().toString());
//                                    String unit =  ingredientUnit.getText().toString();
//                                    String category =  ingredientCategory.getText().toString();
//                                    Date date = new Date();
//                                    listener.onOkPressed(new Ingredient(name, description, date, location, amount, unit, category));
//                                    //listener.onOkPressed(new City(city, province), argsCity.getInt("position"));
//                                }
//                    }
//                    ).create();
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            return builder
//                    .setView(view)
//                    .setTitle("Add Ingredient")
//                    .setNegativeButton("Cancel", null)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
////                            String name = ingredientName.getText().toString();
////                            String description =  ingredientDescription.getText().toString();
////                            String bestBefore =  ingredientBestBefore.getText().toString();
////                            String location =  ingredientLocation.getText().toString();
////                            int amount = Integer.parseInt(ingredientAmount.getText().toString());
////                            String unit =  ingredientUnit.getText().toString();
////                            String category =  ingredientCategory.getText().toString();
////                            Date date = new Date();
//                            //listener.onOkPressed();
//                            //listener.onOkPressed(new Ingredient(name, description, date, location, amount, unit, category));
//                        }
//                    }
//        ).create();
//
//        }
//    }
//}
