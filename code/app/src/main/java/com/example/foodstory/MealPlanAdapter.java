package com.example.foodstory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MealPlanAdapter extends ArrayAdapter<MealPlan> {

    private ArrayList<MealPlan> mealPlans;
    private Context context;

    public MealPlanAdapter(Context context, ArrayList<MealPlan> mealPlans) {
        super(context, 0, mealPlans);
        this.mealPlans = mealPlans;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.meal_plan_item, parent,false);
        }
        MealPlan mealPlan = mealPlans.get(position);
        TextView mealPlanHeading = view.findViewById(R.id.meal_plan_heading);
        TextView mealPlanDate = view.findViewById(R.id.meal_plan_date);
        mealPlanHeading.setText(mealPlan.getMealPlanHeading());
        mealPlanDate.setText(mealPlan.getMealPlanDate());
        return view;
    }
}
