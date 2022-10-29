package com.example.foodstory;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodstory.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }


    // For back button
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}





















//package com.example.foodstory;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//
//import androidx.navigation.NavController;
//import androidx.navigation.NavHost;
//import androidx.navigation.NavHostController;
//import androidx.navigation.Navigation;
//import androidx.navigation.fragment.NavHostFragment;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//
//import com.example.foodstory.databinding.ActivityMainBinding;
//
//public class MainActivity extends AppCompatActivity {
//    private AppBarConfiguration appBarConfiguration;
//    Button ingredients;
//    private ActivityMainBinding binding;
//    Intent intent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ingredients = findViewById(R.id.Ingredients);
//        binding = ActivityMainBinding.inflate(inf);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        binding.Ingredients.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navController.navigate(R.id.action_MainActivity_to_IngredientFragment);
//            }
//        });
////        ingredients.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                navController.navigate(R.id.action_MainActivity_to_IngredientFragment);
////                setContentView(binding.getRoot());
////                binding.Ingredients.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        navController.navigate(R.id.action_MainActivity_to_IngredientFragment);
////                    }
////                });
////            }
////        });
//
//
//
//
//    }
//}