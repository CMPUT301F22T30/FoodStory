package com.example.foodstory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
//import android.app.Fragment;
import android.app.DialogFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.AddRecipeFragmentBinding;
import com.example.foodstory.databinding.RecipeFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddRecipeFragment extends Fragment implements AddIngredientFragment.OnFragmentInteractionListener{
    private AddRecipeFragmentBinding binding;
    Button saveRecipe;
    Button addIngredient;
    EditText title_recipe;
    EditText prep_time_recipe;
    EditText category_recipe;
    EditText servings_recipe;
    EditText comments_recipe;
    EditText photo_recipe;
    FirebaseFirestore recipeDb;
    String TAG = "Sample";
    ArrayList<Ingredient> ingredients;
    ArrayAdapter<Ingredient> ingredient_Adapter;
    Context context;
    public AddRecipeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("recipeKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                //RecipeClass recipe = (RecipeClass) bundle.getClass();
                // Do something with the result
//                if (cityName.length()>0) {
//                    //data.put("Province Name", provinceName);
//                    db.collection("Cities").document(cityName)
//                            .delete()
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {

//                                    Log.d(TAG, "Data has been removed successfully!");
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {

//                                    Log.d(TAG, "Data could not be removed!" + e.toString());
//                                }
//                            });
//                    deleteCityEditText.setText("");
//                }
            }
        });

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddRecipeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredients = new ArrayList<Ingredient>();
        ingredient_Adapter = new IngredientAdapter(getActivity(), ingredients);
//        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
//        ArrayAdapter<Ingredient> ingredient_Adapter = new IngredientAdapter(getActivity(), ingredients);
        ListView ingredientList = getView().findViewById(R.id.recipe_ingredients_list);
        ingredientList.setAdapter(ingredient_Adapter);
        recipeDb = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = recipeDb.collection("Recipes");
//        DocumentReference messageRef = recipeDb
//                .collection("rooms").document("roomA")
//                .collection("messages").document("message1");

        addIngredient = getView().findViewById(R.id.addIngredientButton);
        saveRecipe = getView().findViewById(R.id.saveRecipeButton);
        saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_recipe = getView().findViewById(R.id.recipe_title_editText);
                prep_time_recipe = getView().findViewById(R.id.recipe_preptime_editText);
                category_recipe = getView().findViewById(R.id.recipe_category_editText);
                servings_recipe = getView().findViewById(R.id.recipe_num_servings_editText);
                comments_recipe = getView().findViewById(R.id.recipe_comments_editText);
                photo_recipe = getView().findViewById(R.id.recipe_photos_editText);
                final String rec_name = title_recipe.getText().toString();
                final String rec_prep = prep_time_recipe.getText().toString();
                final String rec_serv = String.valueOf(servings_recipe.getText());
                //int rec_serv = Integer.valueOf(servings_recipe.getText().toString());
                final String rec_cate = category_recipe.getText().toString();
                final String rec_comm = comments_recipe.getText().toString();
                final String rec_phot = photo_recipe.getText().toString();
//                RecipeClass recipe = new RecipeClass(rec_name, rec_prep, rec_serv, rec_cate, rec_comm, rec_phot);
                HashMap<String, String> data = new HashMap<>();
                if (rec_name.length()>0 && rec_prep.length()>0 && rec_serv.length()>0){
                    data.put("Recipe Prep", rec_prep);
                    data.put("Recipe Servings", rec_serv);
                    data.put("Recipe Category", rec_cate);
                    data.put("Recipe Comments", rec_comm);
                    data.put("Recipe Photo", rec_phot);
                    recipeDb.collection("Recipes").document(rec_name)
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
//                    collectionReference
//                            .document(rec_name)
//                            .set(data)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//// These are a method which gets executed when the task is succeeded
//                                    Log.d(TAG, "Data has been added successfully!");
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//// These are a method which gets executed if there’s any problem
//                                    Log.d(TAG, "Data could not be added!" + e.toString());
//                                }
//                            });
                    title_recipe.setText("");
                    prep_time_recipe.setText("");
                    category_recipe.setText("");
                    servings_recipe.setText("");
                    comments_recipe.setText("");
                    photo_recipe.setText("");
                }
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddIngredientFragment().show(getChildFragmentManager(), TAG);
            }

        });


        //Populate AddIngredientFragment with clicked upon item here
//        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //position = i;
//                //cityList.getItemAtPosition(i);
//                // use bundle to pass the city
//                //City clicked_city = (dataList.get(i));
//                new AddCityFragment().newInstance(dataList.get(i), i).show(getSupportFragmentManager(), "Modify_City");
////                Toast t =  Toast.makeText(context, "This is a test",Toast.LENGTH_LONG);
////                t.show();
//            }
//        });
//        Date date = new Date();
//        Ingredient testIngredient = new Ingredient("Rice", "Describe Rice", date, "Pantry", 20, "Medium", "Perishables");
//        ingredients.add(testIngredient);
//        ingredient_Adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onOkPressed(Ingredient newIngredient) {
        //ingredient_Adapter.add(newIngredient);
    }

    @Override
    public void onOkPressed(Ingredient editIngredient, int i) {
//        dataList.remove(i);
//        cityAdapter.insert(editCity, i);
    }
}
