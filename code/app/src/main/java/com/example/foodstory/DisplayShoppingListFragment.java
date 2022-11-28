package com.example.foodstory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.foodstory.databinding.DisplayShoppingListFragmentBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class DisplayShoppingListFragment extends Fragment {
    private DisplayShoppingListFragmentBinding binding;
    public static final String EXTRA_MESSAGE = "";
    FirebaseFirestore dbDisplayIngr;
    public static String TAG = "";
    TextView ingredientName;
    TextView ingredientDescription;
//    TextView ingredientBestBefore;
//    TextView ingredientLocation;
    TextView ingredientAmount;
    TextView ingredientUnit;
    TextView ingredientCategory;
    Ingredient ingredient;

    public DisplayShoppingListFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("callerKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String fragCaller = bundle.getString("parentFragment");
                // Do something with the result
                dbDisplayIngr = FirebaseFirestore.getInstance();
                ingredient = (Ingredient) bundle.getSerializable("IngredientObj");
                if (ingredient != null) {
                    ingredientName = getView().findViewById(R.id.ingredient_name_editText);
                    ingredientDescription = getView().findViewById(R.id.ingredient_description_editText);
//                    ingredientBestBefore = getView().findViewById(R.id.ingredient_bb_editText);
//                    ingredientLocation = getView().findViewById(R.id.ingredient_location_editText);
                    ingredientAmount = getView().findViewById(R.id.ingredient_amount_editText);
                    ingredientUnit = getView().findViewById(R.id.ingredient_unit_editText);
                    ingredientCategory = getView().findViewById(R.id.ingredient_category_editText);
                    ingredientName.setText(ingredient.getName());
                    ingredientDescription.setText(ingredient.getDescription());
//                    ingredientBestBefore.setText(ingredient.getBBDString());
//                    ingredientLocation.setText(ingredient.getLocation());
                    ingredientAmount.setText(Integer.toString(ingredient.getAmount()));
                    ingredientUnit.setText(ingredient.getUnit());
                    ingredientCategory.setText(ingredient.getCategory());
                }
            }
        });
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = DisplayShoppingListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

