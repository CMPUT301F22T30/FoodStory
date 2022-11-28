package com.example.foodstory;

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

import com.example.foodstory.databinding.IngredientFragmentBinding;
import com.example.foodstory.databinding.ShoppingListFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class ShoppingListFragment extends Fragment {
    private ShoppingListFragmentBinding binding;
    FirebaseFirestore dbShopDisp;
    Ingredient shopping_ingredient;
    Ingredient ingredient;
    Ingredient recipe_ingredient;
    RecipeClass recipeClass;

    public ShoppingListFragment(){
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ShoppingListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Ingredient> shopping_ingredients_List = new ArrayList<Ingredient>();
        ArrayList<Ingredient> ingredients_List = new ArrayList<Ingredient>();
        ArrayList<RecipeClass> recipe_List = new ArrayList<RecipeClass>();
        ArrayList<Ingredient> recipe_ingredient_List = new ArrayList<Ingredient>();
        ArrayList<Ingredient> recipe_allingredient_List = new ArrayList<Ingredient>();
        ArrayAdapter<Ingredient> shopping_ingredient_Adapter = new ShoppingAdapter(getActivity(),shopping_ingredients_List);
        ListView shoppingIngredientList = getView().findViewById(R.id.shopping_ingredient_list);
        shoppingIngredientList.setAdapter(shopping_ingredient_Adapter);
        dbShopDisp = FirebaseFirestore.getInstance();
        CollectionReference ShoppingListReference = dbShopDisp.collection("ShoppingList");
        CollectionReference IngredientReference = dbShopDisp.collection("Ingredients");
        CollectionReference RecipeReference = dbShopDisp.collection("Recipes");


        binding.ShoppingListtoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(ShoppingListFragment.this)
                        .navigate(R.id.action_ShoppingListFragment_to_HomeFragment);
            }
        });
        int num = 4;


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
                //shopping_ingredients_List.addAll(recipe_ingredient_List);
                shopping_ingredient_Adapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud

            }
        });

        Button shopping_c = getView().findViewById(R.id.shopping_sort_c);
        shopping_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(shopping_ingredients_List, new Comparator<Ingredient>() {
                    @Override
                    public int compare(Ingredient ingredient, Ingredient t1) {
                        if(ingredient.getCategory() == "" || t1.getCategory() == ""){
                            return 0;
                        } else {
                            return ingredient.getCategory().compareTo(t1.getCategory());
                        }
                    }
                });
                shopping_ingredient_Adapter.notifyDataSetChanged();
            }
        });

        Button shopping_d = getView().findViewById(R.id.shopping_sort_d);
        shopping_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(shopping_ingredients_List, new Comparator<Ingredient>() {
                    @Override
                    public int compare(Ingredient ingredient, Ingredient t1) {
                        if(ingredient.getDescription() == "" || t1.getDescription() == ""){
                            return 0;
                        } else {
                            return ingredient.getDescription().compareTo(t1.getDescription());
                        }
                    }
                });
                shopping_ingredient_Adapter.notifyDataSetChanged();
            }
        });
    }
}
