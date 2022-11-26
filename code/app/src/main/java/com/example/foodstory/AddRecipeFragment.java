package com.example.foodstory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddRecipeFragment extends Fragment{
    private AddRecipeFragmentBinding binding;
    Button addIngredient;
    EditText title_recipe;
    EditText prep_time_recipe;
    EditText category_recipe;
    EditText servings_recipe;
    EditText comments_recipe;
    EditText photo_recipe;
    FirebaseFirestore recipeDb;
    DocumentReference docRef;
    String TAG = "Sample";
    ArrayList<Ingredient> ingredients;
    ArrayAdapter<Ingredient> ingredient_Adapter;
    ListView ingredientList;
    Context context;
    String rec_name = "";
    String rec_prep = "";
    String rec_serv = "";
    int rec_serv2 = 0;
    String rec_cate = "";
    String rec_comm = "";
    String rec_phot = "";
    public AddRecipeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("recipeKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                //bundle key = "recipe"
                //recipe.putSerializable("recipe", passedRecipe);
                //request key = "recipeKey"
                //RecipeClass recipe = (RecipeClass) bundle.getBundle("recipe");
                //getParentFragmentManager().setFragmentResult("recipeKey", recipe);
                String recipeName = bundle.getString("recipeTitle");

                // Do something with the result
                recipeDb = FirebaseFirestore.getInstance();
                //https://cloud.google.com/firestore/docs/query-data/get-data#javaandroid
                docRef = recipeDb.collection("Recipes").document(recipeName);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                title_recipe = getView().findViewById(R.id.recipe_title_editText);
                                prep_time_recipe = getView().findViewById(R.id.recipe_preptime_editText);
                                category_recipe = getView().findViewById(R.id.recipe_category_editText);
                                servings_recipe = getView().findViewById(R.id.recipe_num_servings_editText);
                                comments_recipe = getView().findViewById(R.id.recipe_comments_editText);
                                photo_recipe = getView().findViewById(R.id.recipe_photos_editText);
                                String prepTime = document.getString("prepTime");
                                String nServings = document.getString("recipeServingsStr");
                                String recipeCategory = document.getString("recipeCategory");
                                String comments = document.getString("comments");
                                String photo = document.getString("photo");
                                title_recipe.setText(recipeName);
                                prep_time_recipe.setText(prepTime);
                                category_recipe.setText(recipeCategory);
                                servings_recipe.setText(nServings);
                                comments_recipe.setText(comments);
                                photo_recipe.setText(photo);
                                ingredients = new ArrayList<Ingredient>();
                                Date date = new Date();
                                Ingredient ingr = new Ingredient("Name", "Description", date,
                                        "Location", 5, "Unit", "Category");
                                ingredients.add(ingr);
                                //ingredients = (ArrayList<Ingredient>) document.get("ingredients");
                                ingredient_Adapter = new IngredientAdapter(getActivity(), ingredients);
                                ingredientList = getView().findViewById(R.id.recipe_ingredients_list);
                                ingredientList.setAdapter(ingredient_Adapter);
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

        //AddRecipeFragment needs to pass name of recipe to AddIngredientFragment to allow addition to firestore db

        ingredients = new ArrayList<Ingredient>();
        ingredient_Adapter = new IngredientAdapter(getActivity(), ingredients);
        ingredientList = getView().findViewById(R.id.recipe_ingredients_list);
        ingredientList.setAdapter(ingredient_Adapter);
        recipeDb = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = recipeDb.collection("Recipes");
        //docRef = collectionReference.document();
//                .collection("rooms").document("roomA")
//                .collection("messages").document("message1");

        addIngredient = getView().findViewById(R.id.addIngrButton);

        binding.saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_recipe = getView().findViewById(R.id.recipe_title_editText);
                prep_time_recipe = getView().findViewById(R.id.recipe_preptime_editText);
                category_recipe = getView().findViewById(R.id.recipe_category_editText);
                servings_recipe = getView().findViewById(R.id.recipe_num_servings_editText);
                comments_recipe = getView().findViewById(R.id.recipe_comments_editText);
                photo_recipe = getView().findViewById(R.id.recipe_photos_editText);
                rec_name = title_recipe.getText().toString();
                //if (prep_time_recipe.getText().toString().equals("")) {
                //} else {
                rec_prep = prep_time_recipe.getText().toString();
                //}
                if (String.valueOf(servings_recipe.getText()).equals("")) {
                } else {
                    rec_serv = String.valueOf(servings_recipe.getText());
                    rec_serv2 = Integer.valueOf(servings_recipe.getText().toString());
                }
                //if (category_recipe.getText().toString().equals("")) {
                //} else {
                rec_cate = category_recipe.getText().toString();
                //}
                //if (comments_recipe.getText().toString().equals("")) {
                //} else {
                rec_comm = comments_recipe.getText().toString();
                //}
                //if(photo_recipe.getText().toString().equals("")){
                //} else {
                rec_phot = photo_recipe.getText().toString();
                //}


                RecipeClass recipe = new RecipeClass(rec_name,rec_prep, rec_serv2,
                        rec_cate, rec_comm, rec_phot);
                Date date = new Date();
                recipe.addIngredient(new Ingredient("Name", "Description", date,
                        "Location", 5, "Unit", "Category"));
                if (rec_name.length()>0) {
                    collectionReference.document(rec_name).set(recipe);
                }


//                HashMap<String, String> data = new HashMap<>();
//                if (rec_name.length()>0 && rec_prep.length()>0 && rec_serv.length()>0){
//                    data.put("Recipe Prep", rec_prep);
//                    data.put("Recipe Servings", rec_serv);
//                    data.put("Recipe Category", rec_cate);
//                    data.put("Recipe Comments", rec_comm);
//                    data.put("Recipe Photo", rec_phot);
//                    recipeDb.collection("Recipes").document(rec_name)
//                            .set(data)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "DocumentSnapshot successfully written!");
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w(TAG, "Error writing document", e);
//                                }
//                            });
//                }

                NavHostFragment.findNavController(AddRecipeFragment.this)
                        .navigate(R.id.action_AddRecipeFragment_to_RecipeFragment);
            }
        });

        binding.deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_recipe = getView().findViewById(R.id.recipe_title_editText);
                rec_name = title_recipe.getText().toString();
                if (rec_name.length()>0){
                    recipeDb.collection("Recipes").document(rec_name)
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
                NavHostFragment.findNavController(AddRecipeFragment.this)
                        .navigate(R.id.action_AddRecipeFragment_to_RecipeFragment);
            }
        });

        binding.addIngrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_recipe = getView().findViewById(R.id.recipe_title_editText);
                rec_name = title_recipe.getText().toString();

                // might be existing recipe
                if (rec_name.length()>0){
                    Bundle caller = new Bundle();
                    // To communicate with the AddIngredientFragment that AddRecipeFragment is the caller.
                    caller.putString("parentFragment", "AddRecipeFragment");
                    caller.putString("isRecipe", "No");
                    docRef = collectionReference.document(rec_name);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            RecipeClass recipe = documentSnapshot.toObject(RecipeClass.class);
                            // Pass recipe to AddIngredientFragment
                            caller.putSerializable("RecipeObj", recipe);
                            caller.putString("isRecipe", "Yes");
                        }
                    });
                    caller.putString("recipeName", rec_name);
                    getParentFragmentManager().setFragmentResult("callerKey", caller);
                    NavHostFragment.findNavController(AddRecipeFragment.this)
                            .navigate(R.id.action_AddRecipeFragment_to_AddIngredientFragment);
                }
            }
        });

        //Populate AddIngredientFragment with clicked upon item here
        ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // use bundle to pass the city
                //new AddIngredientFragment().show(getChildFragmentManager(), TAG);
                //new AddIngredientFragment().newInstance(ingredients.get(i), i).show(getChildFragmentManager(), "Edit Ingredient");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
