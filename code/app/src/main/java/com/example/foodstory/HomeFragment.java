package com.example.foodstory;

import android.os.Bundle;
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
    RecipeClass recipeClass;

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
        ArrayList<Ingredient> ingredients_List = new ArrayList<Ingredient>();
        ArrayList<RecipeClass> recipe_List = new ArrayList<RecipeClass>();
        ArrayList<Ingredient> recipe_ingredient_List = new ArrayList<Ingredient>();
        dbShopDisp = FirebaseFirestore.getInstance();
        CollectionReference ShoppingListReference = dbShopDisp.collection("ShoppingList");
        CollectionReference IngredientReference = dbShopDisp.collection("Ingredients");
        CollectionReference RecipeReference = dbShopDisp.collection("Recipes");



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
                int num = 4;

                RecipeReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        recipe_ingredient_List.clear();
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                        {
                            recipeClass = doc.toObject(RecipeClass.class);
                            int amount = (int)Math.ceil((double)num / recipeClass.getNumServings());
                            recipe_ingredient_List.addAll(recipeClass.getIngredients());
                            recipe_List.add(recipeClass);
                            for (int i = 0; i < recipe_ingredient_List.size(); i++){
                                recipe_ingredient_List.get(i).setAmount(amount * recipe_ingredient_List.get(i).getAmount());
                            }

                        }
                    }
                });
                ShoppingListReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                            FirebaseFirestoreException error) {
                        shopping_ingredients_List.clear();
                        for (int i = 0; i < recipe_ingredient_List.size(); i++){
                            if (ingredients_List.contains(recipe_ingredient_List.get(i))){
                                int index = ingredients_List.indexOf(recipe_ingredient_List.get(i));
                                int amount = recipe_ingredient_List.get(i).getAmount() - ingredients_List.get(index).getAmount();
                                if(amount > 0 ){
                                    shopping_ingredients_List.add(ingredients_List.get(index));
                                }
                            }
                            else{
                                shopping_ingredients_List.add(recipe_ingredient_List.get(i));
                            }

                        }
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
