package com.example.foodstory;

import android.os.Bundle;
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
        ArrayAdapter<Ingredient> shopping_ingredient_Adapter = new ShoppingAdapter(getActivity(),shopping_ingredients_List);
        ListView shoppingIngredientList = getView().findViewById(R.id.shopping_ingredient_list);
        shoppingIngredientList.setAdapter(shopping_ingredient_Adapter);
        dbShopDisp = FirebaseFirestore.getInstance();
        CollectionReference ShoppingListReference = dbShopDisp.collection("ShoppingList");
        CollectionReference IngredientReference = dbShopDisp.collection("Ingredients");

//        binding.shoppingIngredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Ingredient passedIngredient = ingredients_List.get(i);
//
//                Bundle caller = new Bundle();
//                //bundle key = "recipe"
//                //recipe.putSerializable("recipe", passedRecipe);
//                //request key = "recipeKey"
//                // To communicate with the AddIngredientFragment that AddRecipeFragment is the caller.
//                caller.putString("parentFragment", "DisplayShoppingListFragment");
//                caller.putSerializable("IngredientObj", passedIngredient);
//                caller.putString("ingredientName", passedIngredient.getName());
//                getParentFragmentManager().setFragmentResult("callerKey", caller);
//                NavHostFragment.findNavController(ShoppingListFragment.this)
//                        .navigate(R.id.action_ShoppingListFragment_to_DisplayIngredientFragment);
//            }
//        });

        binding.ShoppingListtoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ShoppingListFragment.this)
                        .navigate(R.id.action_ShoppingListFragment_to_HomeFragment);
            }
        });
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

        ShoppingListReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                shopping_ingredients_List.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    shopping_ingredient = doc.toObject(Ingredient.class);
//                    if (ingredients_List.contains(shopping_ingredient)){
//                        int index = ingredients_List.indexOf(shopping_ingredient);
//                        int ingredient_amount = ingredients_List.get(index).getAmount();
//                        shopping_ingredient.setAmount(ingredient_amount);
//                    }
                    shopping_ingredient.setAmount(6);
                    shopping_ingredients_List.add(shopping_ingredient);

                }
                shopping_ingredient_Adapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });
    }
}
