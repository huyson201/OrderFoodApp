package com.edu.vn.orderfoodapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtFullName, edtEmail, edtPhone, edtAdress;
    private ImageView imageView;
    private Button editBtn, uploadImgBtn;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
    private static final int REQUEST_PERMISSION_CODE = 10;
    private static final int IMG_REQUEST_CODE = 11;
    public static boolean CHECK_EDIT = false;
    private Uri imgPath=Uri.parse(LoginActivity.userProFile.getImgUrl());
    private StorageReference storage;
    private String randomKey;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        progressBar= findViewById(R.id.progress_bar);
        edtFullName = findViewById(R.id.edt_full_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtAdress = findViewById(R.id.edt_address);
        editBtn = findViewById(R.id.btn_edtProfile);
        uploadImgBtn = findViewById(R.id.upload_img_btn);
        imageView = findViewById(R.id.imageView);

        randomKey = UUID.randomUUID().toString();
        storage = FirebaseStorage.getInstance().getReference().child("users/" + randomKey);

        //get user logged data
        progressBar.setVisibility(View.INVISIBLE);
        edtFullName.setText(LoginActivity.userProFile.getName());
        edtEmail.setText(LoginActivity.userProFile.getEmail());
        edtPhone.setText(LoginActivity.userProFile.getPhone());
        edtAdress.setText(LoginActivity.userProFile.getAddress());
        if (!LoginActivity.userProFile.getImgUrl().isEmpty()) {
            Glide.with(imageView.getContext()).load(LoginActivity.userProFile.getImgUrl()).fitCenter().into(imageView);
        }
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                String name = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAdress.getText().toString();
                String userId = LoginActivity.userProFile.getId();
                String img = LoginActivity.userProFile.getImgUrl();
                //check input values
                if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !address.isEmpty()) {
                    if (imgPath.equals(Uri.parse(img))) {
                        updateProfile(name, email, phone, address, userId, imgPath.toString());
                        updateJson();
                        editBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        afterEdit();
                    } else {
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
                                    Log.d("userURI",uri.toString());
                                    updateProfile(name, email, phone, address, userId, uri.toString());
                                    afterEdit();
                                }
                            }
                            

                        });
                    }

                }
            }
        });

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

    }

    private void updateJson() {
        SharedPreferences sharedPref = getSharedPreferences(LoginActivity.REMEMBER_LOGIN_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String userLoggedJson = sharedPref.getString(LoginActivity.USER_LOGGED_IN, "");
        if (!userLoggedJson.isEmpty()) {
            Gson gson = new Gson();
            String json = gson.toJson(LoginActivity.userProFile);
            editor.putString(LoginActivity.USER_LOGGED_IN, json);
            editor.apply();
        }
    }

    private void afterEdit() {
        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    //update profile
    private void updateProfile(String fullName, String email, String phone, String address, String userId, String image) {
        if (fullName.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "FullName is empty.", Toast.LENGTH_LONG).show();
            edtFullName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Email is empty.", Toast.LENGTH_LONG).show();
            edtEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Phone number is empty.", Toast.LENGTH_LONG).show();
            edtPhone.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Address number is empty.", Toast.LENGTH_LONG).show();
            edtAdress.requestFocus();
            return;
        }
        User user = new User(userId, fullName, email, phone, address);
        if (!image.isEmpty()) {
            user = new User(userId, fullName, email, phone, address, image);
        }
        database.child(userId).setValue(user);
        LoginActivity.userProFile=user;

        //update data to auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();
        fbUser.updateEmail(user.getEmail());
        mAuth.updateCurrentUser(fbUser);
        CHECK_EDIT = true;
        updateJson();
        editBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(EditProfileActivity.this, "Edited successfully!", Toast.LENGTH_SHORT).show();
    }


    // check permission read storage
    private boolean checkPermissionStorage(String permission) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int check = checkSelfPermission(permission);
            return check == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
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
                Glide.with(this).load(imgPath).fitCenter().into(imageView);
            }
        }
    }

}
