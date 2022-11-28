package com.example.foodstory;

import static android.os.SystemClock.sleep;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.HomeFragmentBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    FirebaseFirestore dbShopDisp;
    Ingredient ingredient;
    Ingredient meal_ingredient;
    RecipeClass meal_recipe;
    RecipeClass recipeClass;
    MealPlan mealPlan;
    int meal_amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState
    ){
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Ingredient> shopping_ingredients_List = new ArrayList<Ingredient>();
        ArrayList<String> ing_name = new ArrayList<String>();
        ArrayList<String> rec_name = new ArrayList<String>();
        ArrayList<Ingredient> ingredients_List = new ArrayList<Ingredient>();
        ArrayList<RecipeClass> recipe_List = new ArrayList<RecipeClass>();
        ArrayList<MealPlan> mealPlans = new ArrayList<MealPlan>();
        ArrayList<Ingredient> recipe_ingredient_List = new ArrayList<Ingredient>();
        ArrayList<Ingredient> meal_recipe_ingredient_List = new ArrayList<Ingredient>();
        ArrayList<Ingredient> meal_plan_ingredient = new ArrayList<Ingredient>();
        ArrayList<RecipeClass> meal_plan_recipe = new ArrayList<RecipeClass>();
        ArrayList<Ingredient> breakFast = new ArrayList<Ingredient>();
        ArrayList<Ingredient> dinner = new ArrayList<Ingredient>();
        ArrayList<Ingredient> lunch = new ArrayList<Ingredient>();
        dbShopDisp = FirebaseFirestore.getInstance();
        CollectionReference ShoppingListReference = dbShopDisp.collection("ShoppingList");
        CollectionReference IngredientReference = dbShopDisp.collection("Ingredients");
        CollectionReference RecipeReference = dbShopDisp.collection("Recipes");
        CollectionReference MealPlanReference = dbShopDisp.collection("MealPlans");
        binding.Ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_IngredientFragment);
            }
        });
        binding.Recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_RecipeFragment);
            }
        });

        binding.MealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_MealPlanFragment);
            }
        });


        binding.ShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        ingredients_List.clear();
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                        {
                            ingredient = doc.toObject(Ingredient.class);
                            ingredients_List.add(ingredient);
                        }
                    }
                });
                RecipeReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        recipe_List.clear();
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                        {
                            recipeClass = doc.toObject(RecipeClass.class);
                            recipe_List.add(recipeClass);
                        }
                    }
                });
                MealPlanReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        meal_plan_ingredient.clear();
                        meal_plan_recipe.clear();
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                            mealPlan = doc.toObject(MealPlan.class);
                            ing_name.clear();
                            rec_name.clear();
                            ing_name.addAll(mealPlan.getBreakfastIngredientArray());
                            ing_name.addAll(mealPlan.getDinnerIngredientArray());
                            ing_name.addAll(mealPlan.getLunchIngredientArray());
                            rec_name.addAll(mealPlan.getBreakfastRecipeArray());
                            rec_name.addAll(mealPlan.getDinnerRecipeArray());
                            rec_name.addAll(mealPlan.getLunchRecipeArray());
                            meal_amount = mealPlan.getNumOfPeople();

                            for (int i = 0; i<ing_name.size(); i++){
                                for (int j = 0; j< ingredients_List.size(); j++){
                                    if (ingredients_List.get(j).getName().equals(ing_name.get(i))){
                                        meal_ingredient = new Ingredient(ingredients_List.get(j));
                                        meal_ingredient.setAmount(meal_amount);
                                        if (meal_plan_ingredient.contains(meal_ingredient)){
                                            int index = meal_plan_ingredient.indexOf(meal_ingredient);
                                            meal_plan_ingredient.get(index).setAmount(meal_plan_ingredient.get(index).getAmount()+ meal_ingredient.getAmount());
                                        }
                                        else{
                                            meal_plan_ingredient.add(meal_ingredient);
                                        }
                                    }
                                }
                            }

                            for (int i = 0; i<rec_name.size(); i++){
                                for (int j = 0; j < recipe_List.size(); j++){
                                    meal_recipe_ingredient_List.clear();
                                    if (recipe_List.get(j).getTitle().equals(rec_name.get(i))){
                                        int amount = (int)Math.ceil((double)meal_amount / recipe_List.get(j).getNumServings());
                                        Log.d(recipe_List.get(j).getTitle(), String.valueOf(amount));
                                        meal_recipe_ingredient_List.addAll(recipe_List.get(j).getIngredients());
                                        Log.d("meal_recipe_ingredient", String.valueOf(meal_recipe_ingredient_List.get(0).getName()));

                                        for (int k = 0; k < meal_recipe_ingredient_List.size(); k++){
                                            meal_recipe_ingredient_List.get(k).setAmount(amount * meal_recipe_ingredient_List.get(k).getAmount());
                                            Log.d("meal_recipe_ingredient", String.valueOf(meal_recipe_ingredient_List.get(0).getName()) + String.valueOf(meal_recipe_ingredient_List.get(0).getAmount()));
                                            meal_plan_ingredient.add(meal_recipe_ingredient_List.get(k));

                                        }
                                    }
                                }
                            }
                        }


                        shopping_ingredients_List.clear();
                        for (int i = 0; i < meal_plan_ingredient.size(); i++){
                            for (int j = 0; j < ingredients_List.size(); j++){
                                if (ingredients_List.get(j).getName().equals(meal_plan_ingredient.get(i).getName())){
                                    int index = j;
                                    int finalamount = meal_plan_ingredient.get(i).getAmount() - ingredients_List.get(index).getAmount();
                                    if(finalamount > 0 ){
                                        meal_plan_ingredient.get(i).setAmount(finalamount);
                                        shopping_ingredients_List.add(meal_plan_ingredient.get(i));

                                    }
                                    break;

                                }
                                else{
                                    shopping_ingredients_List.add(meal_plan_ingredient.get(i));
                                }


                            }
//
                        }
                        Log.d("THIS", String.valueOf(shopping_ingredients_List.get(1).getName()));

                        for (int i = 0; i < shopping_ingredients_List.size(); i++){
                            shopping_ingredients_List.get(i).setLocation("NULL");
                            Date date = new Date(0);
                            shopping_ingredients_List.get(i).setBestBefore(date);
                            dbShopDisp.collection("ShoppingList").document(shopping_ingredients_List.get(i).getName())
                                    .set(shopping_ingredients_List.get(i));
                        }






                    }
                });
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_ShoppingListFragment);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
