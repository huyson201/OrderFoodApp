package com.edu.vn.orderfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtFullName, edtEmail, edtPhone, edtAdress;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        edtFullName=findViewById(R.id.edt_full_name);
        edtEmail=findViewById(R.id.edt_email);
        edtPhone=findViewById(R.id.edt_phone);
        edtAdress=findViewById(R.id.edt_address);
        editBtn=findViewById(R.id.btn_edtProfile);


//        Intent intent=getIntent();
        //get user logged data
        edtFullName.setText(LoginActivity.userProFile.getName());
        edtEmail.setText(LoginActivity.userProFile.getEmail());
        edtPhone.setText(LoginActivity.userProFile.getPhone());
        edtAdress.setText(LoginActivity.userProFile.getAddress());
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
