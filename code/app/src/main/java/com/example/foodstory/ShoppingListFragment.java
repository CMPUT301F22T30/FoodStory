package com.example.foodstory;

import android.app.AlertDialog;
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
    int currentPos = -1;

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

        ShoppingListReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                shopping_ingredients_List.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    ingredient = doc.toObject(Ingredient.class);
                    shopping_ingredients_List.add(ingredient);
                }
                shopping_ingredient_Adapter.notifyDataSetChanged();
            }
        });

        binding.shoppingIngredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentPos == i ){
                    shoppingIngredientList.setAdapter(shopping_ingredient_Adapter);
                    currentPos = -1;
                }
                else{
                    currentPos = i;
                }

            }
        });
        Button delete = getView().findViewById(R.id.shopping_delete_btn);

        binding.shoppingDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPos != -1){
                    shopping_ingredient = shopping_ingredients_List.get(currentPos);
                    dbShopDisp.collection("ShoppingList").document(shopping_ingredient.getName())
                            .delete();

                    shopping_ingredients_List.remove(currentPos);
                    shopping_ingredient_Adapter.notifyDataSetChanged();
                    shoppingIngredientList.setAdapter(shopping_ingredient_Adapter);
                    dbShopDisp.collection("Ingredients").document(shopping_ingredient.getName())
                            .set(shopping_ingredient);
                    AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                    builder.setTitle("Notification");
                    builder.setMessage("Transferring to ingredients tab. Please fill the correct details for the shopped for ingredients. Click anywhere to continue");
                    builder.show();
                    currentPos = -1;
                    NavHostFragment.findNavController(ShoppingListFragment.this)
                            .navigate(R.id.action_ShoppingListFragment_to_IngredientFragment);
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
    }
}
