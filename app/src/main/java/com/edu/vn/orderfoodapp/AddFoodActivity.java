package com.edu.vn.orderfoodapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class AddFoodActivity extends AppCompatActivity {
    //properties
    private Button uploadImgBtn, addFoodBtn;
    private EditText edtFoodName, edtFoodDesc, edtFoodPrice;
    private ImageView imgFood;
    private ProgressBar progressBar;
    private ImageButton backBtn;
    private Toolbar toolbar;
    private static final int REQUEST_PERMISSION_CODE = 10;
    private static final int IMG_REQUEST_CODE = 11;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> categories;
    private StorageReference storage;
    private DatabaseReference db_food = FirebaseDatabase.getInstance().getReference("foods");
    private DatabaseReference db_cate = FirebaseDatabase.getInstance().getReference("categories");
    private Uri imgPath;
    private AutoCompleteTextView dropdown_menu;
    private String randomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food_layout);

        uploadImgBtn = findViewById(R.id.upload_img_btn);
        addFoodBtn = findViewById(R.id.add_food_btn);
        edtFoodName = findViewById(R.id.edt_food_name);
        edtFoodDesc = findViewById(R.id.edt_food_desc);
        edtFoodPrice = findViewById(R.id.edt_food_price);
        imgFood = findViewById(R.id.img_food);
        progressBar = findViewById(R.id.progress_bar);
        backBtn = findViewById(R.id.back_btn);
        toolbar = findViewById(R.id.toolbar);
        dropdown_menu = findViewById(R.id.dropdown_item);
        randomKey = UUID.randomUUID().toString();
        storage = FirebaseStorage.getInstance().getReference().child("foods/" + randomKey);
        // set action bar
        setActionBar(toolbar);
        // back btn processing event
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to prev activity
                onBackPressed();
            }
        });
        // category adapter
        categories = new ArrayList<>();
        getCategoryNames();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_item, categories );
        dropdown_menu.setAdapter(arrayAdapter);

        // hide progress bar
        progressBar.setVisibility(View.INVISIBLE);
        //processing click upload image btn
        uploadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = checkPermissionStorage(Manifest.permission.READ_EXTERNAL_STORAGE);
                if (check) {
                    openGallery();
                } else {
                    String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permission, REQUEST_PERMISSION_CODE);
                    }
                }
            }


        });

        // processing add category
        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                //get edit text
                String foodName = edtFoodName.getText().toString();
                String foodDesc = edtFoodDesc.getText().toString();
                String foodPrice = edtFoodPrice.getText().toString();
                String categoryName = dropdown_menu.getText().toString();
                //check input values
                if (!foodName.isEmpty() && imgPath != null && !categoryName.isEmpty()) {
                    UploadTask uploadTask = storage.putFile(imgPath);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            return storage.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri uri = task.getResult();
                                String key = db_food.push().getKey();
                                //get category ID
                                db_cate.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            Category category = dataSnapshot.getValue(Category.class);
                                            if(category.getCategoryName().equalsIgnoreCase(categoryName)){
                                                Food food = new Food(category.getCategoryID(),key, uri.toString(), foodName, foodDesc, Integer.parseInt(foodPrice));
                                                db_food.child(key).setValue(food);
                                                addFoodBtn.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(AddFoodActivity.this, "Add food Successfully.", Toast.LENGTH_SHORT).show();
                                                resetField();
                                                break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                                //return the menus
                                Intent intent = new Intent(AddFoodActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddFoodActivity.this, "Food's name must not be empty", Toast.LENGTH_SHORT).show();
                    addFoodBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }

    }
    private void getCategories() {
        db_cate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category.getCategoryName());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getCategoryNames() {
        db_cate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category.getCategoryName());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // check permission read storage
    private boolean checkPermissionStorage(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(permission);
            return check == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    // open gallery
    private void openGallery() {
        Toast.makeText(this, "Open Gallery", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, IMG_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IMG_REQUEST_CODE && data != null) {
            imgPath = data.getData();
            if (imgPath != null) {
                Glide.with(this).load(imgPath).fitCenter().into(imgFood);
            }
        }
    }

    private void resetField() {
        edtFoodName.setText("");
        edtFoodDesc.setText("");
        edtFoodPrice.setText("");
        imgFood.setImageDrawable(getResources().getDrawable(R.drawable.ic_picture));
        dropdown_menu.setText(null);
    }


}