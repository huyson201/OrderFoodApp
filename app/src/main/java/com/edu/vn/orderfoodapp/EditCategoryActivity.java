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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.models.Category;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class EditCategoryActivity extends AppCompatActivity {
    //properties
    private TextView title;
    private Button uploadImgBtn, addCategoryBtn;
    private EditText edtCategoryName;
    private ImageView imgCategory;
    private ProgressBar progressBar;
    private ImageButton backBtn;
    private Toolbar toolbar;
    private static final int  REQUEST_PERMISSION_CODE = 10;
    private static final int IMG_REQUEST_CODE = 11;
    private String randomKey;
    private StorageReference storage;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("categories");
    private  Uri imgPath;
    public static String CATE_NAME_TAG = "cateName";
    public static String CATE_ID_TAG = "cateId";
    public static String CATE_IMG_TAG = "cateImage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_layout);

        uploadImgBtn = findViewById(R.id.upload_img_btn);
        addCategoryBtn = findViewById(R.id.add_category_btn);
        edtCategoryName = findViewById(R.id.category_name);
        imgCategory = findViewById(R.id.img_category);
        progressBar = findViewById(R.id.progress_bar);
        backBtn = findViewById(R.id.back_btn);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.lbl_title);
        //get intent value
        addCategoryBtn.setText("Edit Category");
        title.setText("Edit Category");
        edtCategoryName.setText(getIntent().getStringExtra(CATE_NAME_TAG));
        String cateId = getIntent().getStringExtra(CATE_ID_TAG);
        String cateImg = getIntent().getStringExtra(CATE_IMG_TAG);

        Glide.with(this).load(cateImg).fitCenter().into(imgCategory);

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

        randomKey = UUID.randomUUID().toString();
        storage = FirebaseStorage.getInstance().getReference().child("categories/"+randomKey);
        // hide progress bar

        progressBar.setVisibility(View.INVISIBLE);
        //processing click upload image btn
        uploadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 boolean check = checkPermissionStorage(Manifest.permission.READ_EXTERNAL_STORAGE);
                 if(check){
                     openGallery();
                 }else {
                     String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                         requestPermissions(permission, REQUEST_PERMISSION_CODE);
                     }
                 }
            }


        });

        // processing add category
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategoryBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                String categoryName = edtCategoryName.getText().toString();
                //check input values
                imgPath = Uri.parse(cateImg);
                if(!categoryName.isEmpty() && !imgPath.equals(Uri.parse(cateImg))){
                    Log.d("imgpath",imgPath.toString());
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
                                updateCategory(cateId,categoryName,uri.toString());
                            }
                        }
                    });
                }else if(imgPath.equals(Uri.parse(cateImg))){
                    updateCategory(cateId,categoryName,cateImg);
                }else{
                    Toast.makeText(EditCategoryActivity.this, "Category's name must not be empty", Toast.LENGTH_SHORT).show();
                    addCategoryBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });
    }
    private void updateCategory(String cateId, String cateName, String cateImg){
        Category category = new Category(cateName, cateId, cateImg);
        db.child(cateId).setValue(category);
        addCategoryBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(EditCategoryActivity.this, "Edit category Successfully.", Toast.LENGTH_SHORT).show();
        resetField();
        Intent intent = new Intent(EditCategoryActivity.this, CategoryListActivity.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }

    }

    // check permission read storage
    private boolean checkPermissionStorage(String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(permission);
            return check == PackageManager.PERMISSION_GRANTED;
        }else{
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
            if(imgPath != null){
                Glide.with(this).load(imgPath).fitCenter().into(imgCategory);
            }
        }
    }

    private void resetField(){
        edtCategoryName.setText("");
        imgCategory.setImageDrawable(getResources().getDrawable(R.drawable.ic_picture));
    }


}