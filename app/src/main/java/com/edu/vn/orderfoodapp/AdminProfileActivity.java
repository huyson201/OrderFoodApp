package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminProfileActivity extends AppCompatActivity {
    private TextView userName, fullName,phone,address,email;
    private ImageView userImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile_layout);
        userName=findViewById(R.id.nameUser);
        fullName = findViewById(R.id.lbl_name);
        phone = findViewById(R.id.lbl_phone);
        address = findViewById(R.id.lbl_address);
        email = findViewById(R.id.lbl_email);

        userName.setText(LoginActivity.userProFile.getName());
        fullName.setText(LoginActivity.userProFile.getName());
        phone.setText(LoginActivity.userProFile.getPhone());
        address.setText(LoginActivity.userProFile.getAddress());
        email.setText(LoginActivity.userProFile.getEmail());


    }
}