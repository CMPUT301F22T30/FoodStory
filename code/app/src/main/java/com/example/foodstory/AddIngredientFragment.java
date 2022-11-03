//package com.example.foodstory;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//
//public class AddIngredientFragment extends DialogFragment{
//    private EditText cityName;
//    private EditText provinceName;
//
//    private OnFragmentInteractionListener listener;
//    private Ingredient editIngredient;
//    private Bundle argsCity;
//
//    public interface OnFragmentInteractionListener {
//
//        void onOkPressed(Ingredient newIngredient);
//        void onOkPressed(Ingredient editIngredient, int i);
//    }
//        @Override
//        public void onAttach(Context context) {
//            super.onAttach(context);
//            if(context instanceof OnFragmentInteractionListener) {
//                listener = (OnFragmentInteractionListener) context;
//            } else {
//                throw new RuntimeException(context.toString() + "This is not the correct fragment!");
//            }
//        }
//    public static AddIngredientFragment newInstance(Ingredient ingredient, int position) {
//        Bundle args = new Bundle();
//        args.putSerializable("Ingredient", ingredient);
//        args.putInt("position", position);
//        AddIngredientFragment fragment = new AddIngredientFragment();
//        fragment.setArguments(args);
//        return fragment;
//
//    }
//    @NonNull
//
//    @Override
//
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_fragment_layout, null);
//        cityName = view.findViewById(R.id.city_Name_editText);
//        provinceName = view.findViewById(R.id.province_name_editText);
//        argsCity = getArguments();
//        if(argsCity != null) {
//            editCity = (City) argsCity.getSerializable("city");
//            cityName.setText(editCity.getCity().toString());
//            provinceName.setText(editCity.getProvince().toString());
//            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            return builder
//                    .setView(view)
//                    .setTitle("Edit City")
//                    .setNegativeButton("Cancel", null)
//                    .setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    String city = cityName.getText().toString();
//                                    String province = provinceName.getText().toString();
//                                    listener.onOkPressed(new City(city, province), argsCity.getInt("position"));
//                                }
//                    }).create();
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            return builder
//                    .setView(view)
//                    .setTitle("Add City")
//                    .setNegativeButton("Cancel", null)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            String city = cityName.getText().toString();
//                            String province = provinceName.getText().toString();
//                            listener.onOkPressed(new City(city, province));
//                    }
//        }).create();
//
//        }
//
//    }
//}


