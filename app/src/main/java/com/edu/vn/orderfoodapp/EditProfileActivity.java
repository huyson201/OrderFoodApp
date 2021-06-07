package com.edu.vn.orderfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtFullName, edtEmail, edtPhone, edtAdress;
    private Button editBtn;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

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
                updateProfile( edtFullName.getText().toString(),edtEmail.getText().toString(),edtPhone.getText().toString(),edtAdress.getText().toString(),LoginActivity.userProFile.getId());
                Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateProfile(String fullName,String email,String phone,String address,String userId){
        User user = new User(userId,fullName,email,phone,address);
        database.child(userId).setValue(user);
    }
}
