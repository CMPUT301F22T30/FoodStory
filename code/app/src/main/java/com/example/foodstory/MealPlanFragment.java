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

import com.example.foodstory.databinding.MealPlanFragmentBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MealPlanFragment extends Fragment {

    private MealPlanFragmentBinding binding;
    FirebaseFirestore dbMealPlan;
    MealPlan meal_plan;

    public MealPlanFragment(){
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MealPlanFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<MealPlan> mealPlan_List = new ArrayList<MealPlan>();
        ArrayAdapter<MealPlan> mealPlan_Adapter = new MealPlanAdapter(getActivity(),mealPlan_List);
        ListView mealPlanList = getView().findViewById(R.id.meal_plan_list);
        mealPlanList.setAdapter(mealPlan_Adapter);
        dbMealPlan = FirebaseFirestore.getInstance();
        CollectionReference mealPlanReference = dbMealPlan.collection("MealPlans");


        binding.mealPlanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //https://developer.android.com/guide/fragments/communicate

                MealPlan passedMealPlan = mealPlan_List.get(i);
                Bundle meal_plan = new Bundle();

                meal_plan.putString("parentFragment", "ViewMealPlanFragment");
                meal_plan.putString("mealPlanName", passedMealPlan.getMealPlanName());
                getParentFragmentManager().setFragmentResult("callerKey", meal_plan);
                NavHostFragment.findNavController(MealPlanFragment.this)
                        .navigate(R.id.action_MealPlanFragment_to_ViewMealPlanFragment);
            }
        });

        Button addMealPlanButton = getView().findViewById(R.id.addMealPlanButton);
        addMealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MealPlanFragment.this)
                        .navigate(R.id.action_MealPlanFragment_to_AddMealPlanFragment);
            }
        });

        binding.MealPlanFragmenttoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MealPlanFragment.this)
                        .navigate(R.id.action_MealPlanFragment_to_HomeFragment);
            }
        });

        mealPlanReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                mealPlan_List.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                      meal_plan = doc.toObject(MealPlan.class);
                      mealPlan_List.add(meal_plan);
                }
                mealPlan_Adapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}
