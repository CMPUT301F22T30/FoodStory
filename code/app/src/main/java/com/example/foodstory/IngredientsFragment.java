package com.example.foodstory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Date;


public class IngredientsFragment extends Fragment {
    private IngredientFragmentBinding binding;
    FirebaseFirestore dbIngrDisp;

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
        Date date = new Date();
        ingredientList.setAdapter(ingredient_Adapter);

        dbIngrDisp = FirebaseFirestore.getInstance();
        CollectionReference ingredientReference = dbIngrDisp.collection("Ingredients");

        binding.ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavHostFragment.findNavController(IngredientsFragment.this)
                        .navigate(R.id.action_IngredientFragment_to_AddIngredientFragment);
            }
        });

        binding.addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(IngredientsFragment.this)
                        .navigate(R.id.action_IngredientFragment_to_AddIngredientFragment);
            }
        });

        ingredientReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                ingredients_List.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String ingr_name = doc.getId();
                    String ingr_desc = (String) doc.getData().get("Ingredient Description");
                    String ingr_bb = (String) doc.getData().get("Ingredient BestBefore");
                    String ingr_loca = (String) doc.getData().get("Ingredient Location");
                    String nAmount = (String) doc.getData().get("Ingredient Amount");
                    int ingr_amount = Integer.valueOf(nAmount);
                    Date date = new Date();
                    String ingr_unit = (String) doc.getData().get("Ingredient Unit");
                    String ingr_cate = (String) doc.getData().get("Ingredient Category");
                    ingredients_List.add(new Ingredient(ingr_name, ingr_desc, date, ingr_loca, ingr_amount, ingr_unit, ingr_cate));
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

