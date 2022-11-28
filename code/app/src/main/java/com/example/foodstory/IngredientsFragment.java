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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;


public class IngredientsFragment extends Fragment {
    private IngredientFragmentBinding binding;
    FirebaseFirestore dbIngrDisp;
    private Ingredient ingredient;

    public IngredientsFragment(){
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = IngredientFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Ingredient> ingredients_List = new ArrayList<Ingredient>();
        ArrayAdapter<Ingredient> ingredient_Adapter = new IngredientAdapter(getActivity(),ingredients_List);
        ListView ingredientList= getView().findViewById(R.id.ingredient_list);
        ingredientList.setAdapter(ingredient_Adapter);
        dbIngrDisp = FirebaseFirestore.getInstance();
        CollectionReference ingredientReference = dbIngrDisp.collection("Ingredients");
        Date date = new Date();

        binding.ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ingredient passedIngredient = ingredients_List.get(i);

                Bundle caller = new Bundle();
                caller.putString("parentFragment", "AddIngredientFragment");
                caller.putSerializable("IngredientObj", passedIngredient);
                caller.putString("ingredientName", passedIngredient.getName());
                getParentFragmentManager().setFragmentResult("callerKey", caller);
                NavHostFragment.findNavController(IngredientsFragment.this)
                        .navigate(R.id.action_IngredientFragment_to_AddIngredientFragment);
            }
        });
        Button Sort_title = getView().findViewById(R.id.sort_descButton);
        Sort_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(ingredients_List, new Comparator<Ingredient>() {
                    @Override
                    public int compare(Ingredient ingredient, Ingredient t1) {
                        return ingredient.getName().compareTo(t1.getName());
                    }
                });
                ingredient_Adapter.notifyDataSetChanged();
            }
        });

        Button Sort_date = getView().findViewById(R.id.sort_dateButton);
        Sort_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(ingredients_List, new Comparator<Ingredient>() {
                    @Override
                    public int compare(Ingredient ingredient, Ingredient t1) {
                        return ingredient.getBBDString().compareTo(t1.getBBDString());
                    }
                });
                ingredient_Adapter.notifyDataSetChanged();
            }
        });

        Button Sort_location = getView().findViewById(R.id.sort_locButton);
        Sort_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(ingredients_List, new Comparator<Ingredient>() {
                    @Override
                    public int compare(Ingredient ingredient, Ingredient t1) {
                        if(ingredient.getLocation()==""||t1.getLocation()==""){
                            return 0;
                        } else{
                            return ingredient.getLocation().compareTo(t1.getLocation());
                        }
                    }
                });
                ingredient_Adapter.notifyDataSetChanged();
            }
        });

        Button Sort_category = getView().findViewById(R.id.sort_catButton);
        Sort_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(ingredients_List, new Comparator<Ingredient>() {
                    @Override
                    public int compare(Ingredient ingredient, Ingredient t1) {
                        if(ingredient.getCategory() == "" || t1.getCategory() == ""){
                            return 0;
                        } else {
                            return ingredient.getCategory().compareTo(t1.getCategory());
                        }
                    }
                });
                ingredient_Adapter.notifyDataSetChanged();
            }
        });

        binding.addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle caller = new Bundle();
                // To communicate with the AddIngredientFragment that AddRecipeFragment is the caller.
                caller.putString("parentFragment", "AddIngredientFragment");
                caller.putString("ingredientName", "00000000");
                getParentFragmentManager().setFragmentResult("callerKey", caller);
                NavHostFragment.findNavController(IngredientsFragment.this)
                        .navigate(R.id.action_IngredientFragment_to_AddIngredientFragment);
            }
        });

        binding.IngredienttoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(IngredientsFragment.this)
                        .navigate(R.id.action_IngredientFragment_to_HomeFragment);
            }
        });


        ingredientReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                ingredients_List.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    ingredient = doc.toObject(Ingredient.class);
                    ingredients_List.add(ingredient);
                }
                ingredient_Adapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

