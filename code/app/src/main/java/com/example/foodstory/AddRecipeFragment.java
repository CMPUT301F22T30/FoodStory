package com.example.foodstory;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
//import android.app.Fragment;
import android.app.DialogFragment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodstory.databinding.AddRecipeFragmentBinding;
import com.example.foodstory.databinding.RecipeFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddRecipeFragment extends Fragment{
    private AddRecipeFragmentBinding binding;
    private Button addIngredient;
    private EditText title_recipe;
    private EditText prep_time_recipe;
    private EditText category_recipe;
    private EditText servings_recipe;
    private EditText comments_recipe;
    private EditText photo_recipe;
    private ImageButton img_recipe;
    private ImageView img_view_recipe;
    private ImageButton upload_image;
    private FirebaseFirestore recipeDb;
    private CollectionReference collectionReference;
    private DocumentReference docRef;
    private String TAG = "Sample";
    private ArrayList<Ingredient> ingredients;
    private ArrayAdapter<Ingredient> ingredient_Adapter;
    private ListView ingredientList;
    private Context context;
    private String rec_name = "";
    private String rec_prep = "";
    private String rec_serv = "";
    private int rec_serv2 = 0;
    private String rec_cate = "";
    private String rec_comm = "";
    private String rec_phot = "";
    private String img_def = "";
    private RecipeClass curr_Recipe;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    public AddRecipeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("recipeKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                curr_Recipe = (RecipeClass) bundle.getSerializable("recipeObj");
                title_recipe = getView().findViewById(R.id.recipe_title_editText);
                prep_time_recipe = getView().findViewById(R.id.recipe_preptime_editText);
                category_recipe = getView().findViewById(R.id.recipe_category_editText);
                servings_recipe = getView().findViewById(R.id.recipe_num_servings_editText);
                comments_recipe = getView().findViewById(R.id.recipe_comments_editText);
                photo_recipe = getView().findViewById(R.id.recipe_photos_editText);
                img_recipe = getView().findViewById(R.id.imageButton);
                img_view_recipe = getView().findViewById(R.id.recipe_image);
                rec_name = curr_Recipe.getTitle();
                rec_prep = curr_Recipe.getPrepTime();
                rec_serv = curr_Recipe.getRecipeServingsStr();
                rec_cate = curr_Recipe.getRecipeCategory();
                rec_comm = curr_Recipe.getComments();
                rec_phot = curr_Recipe.getPhoto();
                if (curr_Recipe.getPhoto().length() > 0) {
                    img_def = curr_Recipe.getPhoto();
                    Bitmap bmp = StringToBitMap(curr_Recipe.getPhoto());
                    img_view_recipe.setImageBitmap(bmp);
                }
                title_recipe.setText(rec_name);
                prep_time_recipe.setText(rec_prep);
                category_recipe.setText(rec_cate);
                servings_recipe.setText(rec_serv);
                comments_recipe.setText(rec_comm);
                photo_recipe.setText(rec_phot);
                ingredients = curr_Recipe.getIngredients();
                ingredient_Adapter = new IngredientAdapter(getActivity(), ingredients);
                ingredientList = getView().findViewById(R.id.recipe_ingredients_list);
                ingredientList.setAdapter(ingredient_Adapter);
            }
        });

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddRecipeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //AddRecipeFragment needs to pass name of recipe to AddIngredientFragment to allow addition to firestore db

        ingredients = new ArrayList<Ingredient>();
        ingredient_Adapter = new IngredientAdapter(getActivity(), ingredients);
        ingredientList = getView().findViewById(R.id.recipe_ingredients_list);
        ingredientList.setAdapter(ingredient_Adapter);
        recipeDb = FirebaseFirestore.getInstance();
        collectionReference = recipeDb.collection("Recipes");
        title_recipe = getView().findViewById(R.id.recipe_title_editText);
        prep_time_recipe = getView().findViewById(R.id.recipe_preptime_editText);
        category_recipe = getView().findViewById(R.id.recipe_category_editText);
        servings_recipe = getView().findViewById(R.id.recipe_num_servings_editText);
        comments_recipe = getView().findViewById(R.id.recipe_comments_editText);
        photo_recipe = getView().findViewById(R.id.recipe_photos_editText);
        img_recipe = getView().findViewById(R.id.imageButton);
        img_view_recipe = getView().findViewById(R.id.recipe_image);
        upload_image = getView().findViewById(R.id.uploadimageButton);

        addIngredient = getView().findViewById(R.id.addIngrButton);

        // https://developer.android.com/training/basics/intents/result
        ActivityResultLauncher<Intent> getPhoto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                Bitmap bitMap;
                                bitMap = (Bitmap) result.getData().getExtras().get("data");
                                img_view_recipe.setImageBitmap(bitMap);
                                String bmp = BitMapToString(bitMap);
                                if (bmp != null) {
                                    img_def = bmp;
                                    // photo_recipe.setText(bmp);
                                }
                            }
                        }
                    }
                }
        );

        // https://stackoverflow.com/questions/2314958/using-the-camera-activity-in-android
        img_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getPhoto.launch(Intent.createChooser(camera, "Take Recipe picture"));

            }
        });

        binding.saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rec_name = title_recipe.getText().toString();
                rec_prep = prep_time_recipe.getText().toString();
                if (String.valueOf(servings_recipe.getText()).equals("")) {
                } else {
                    rec_serv = String.valueOf(servings_recipe.getText());
                    rec_serv2 = Integer.valueOf(servings_recipe.getText().toString());
                }
                rec_cate = category_recipe.getText().toString();
                rec_comm = comments_recipe.getText().toString();
                // rec_phot = photo_recipe.getText().toString();
                if (rec_name.length()>0) {
                    if (curr_Recipe == null) {
                        RecipeClass recipe = new RecipeClass(rec_name, rec_prep, rec_serv2,
                                rec_cate, rec_comm, rec_phot);
                        collectionReference.document(rec_name).set(recipe);
                    } else {
                        collectionReference.document(curr_Recipe.getTitle())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Data has been removed successfully!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Data could not be removed!", e);
                                    }
                                });
                        curr_Recipe.setTitle(rec_name);
                        curr_Recipe.setRecipePrepTime(rec_prep);
                        curr_Recipe.setNumServings(rec_serv2);
                        curr_Recipe.setRecipeCategory(rec_cate);
                        curr_Recipe.setComments(rec_comm);
                        curr_Recipe.setPhoto(img_def);
                        collectionReference.document(curr_Recipe.getTitle()).set(curr_Recipe);
                    }
                }
                NavHostFragment.findNavController(AddRecipeFragment.this)
                        .navigate(R.id.action_AddRecipeFragment_to_RecipeFragment);
            }
        });

        binding.deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curr_Recipe != null){
                    collectionReference.document(curr_Recipe.getTitle())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Data has been removed successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Data could not be removed!", e);
                                }
                            });
                }
                NavHostFragment.findNavController(AddRecipeFragment.this)
                        .navigate(R.id.action_AddRecipeFragment_to_RecipeFragment);
            }
        });

        binding.addIngrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_recipe = getView().findViewById(R.id.recipe_title_editText);
                rec_name = title_recipe.getText().toString();
                Bundle caller = new Bundle();
                // To communicate with the AddIngredientFragment that AddRecipeFragment is the caller.
                caller.putString("parentFragment", "AddRecipeFragment");
                //What if curr_Recipe is null, create one and pass as bundle
                if (curr_Recipe == null){
                    if (rec_name.length() > 0) {
                        rec_prep = prep_time_recipe.getText().toString();
                        if (String.valueOf(servings_recipe.getText()).equals("")) {
                        } else {
                            rec_serv = String.valueOf(servings_recipe.getText());
                            rec_serv2 = Integer.valueOf(servings_recipe.getText().toString());
                        }
                        rec_cate = category_recipe.getText().toString();
                        rec_comm = comments_recipe.getText().toString();
                        rec_phot = img_def;
                        curr_Recipe = new RecipeClass(rec_name, rec_prep, rec_serv2,
                                rec_cate, rec_comm, rec_phot);
                        caller.putSerializable("RecipeObj", curr_Recipe);
                        getParentFragmentManager().setFragmentResult("callerKey", caller);
                        NavHostFragment.findNavController(AddRecipeFragment.this)
                                .navigate(R.id.action_AddRecipeFragment_to_AddIngredientFragment);
                    }
                } else {
                    caller.putSerializable("RecipeObj", curr_Recipe);
                    getParentFragmentManager().setFragmentResult("callerKey", caller);
                    NavHostFragment.findNavController(AddRecipeFragment.this)
                            .navigate(R.id.action_AddRecipeFragment_to_AddIngredientFragment);
                }
            }
        });

        binding.recipeIngredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Populate AddIngredientFragment with clicked upon item here
                if (curr_Recipe != null) {
                    Ingredient passedIngredient = ingredients.get(i);
                    ingredients.remove(i);
                    Bundle bundle = new Bundle();
                    bundle.putString("parentFragment", "AddRecipeFragment");
                    bundle.putSerializable("RecipeObj", curr_Recipe);
                    bundle.putSerializable("IngredientObj", passedIngredient);
                    getParentFragmentManager().setFragmentResult("callerKey", bundle);
                    NavHostFragment.findNavController(AddRecipeFragment.this)
                            .navigate(R.id.action_AddRecipeFragment_to_AddIngredientFragment);
                }
            }
        });
    }

    // https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
