package com.edu.vn.orderfoodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.User;
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

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtFullName, edtEmail, edtPhone, edtAdress;
    private Button editBtn;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        edtFullName = findViewById(R.id.edt_full_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtAdress = findViewById(R.id.edt_address);
        editBtn = findViewById(R.id.btn_edtProfile);


//        Intent intent=getIntent();
        //get user logged data

        edtFullName.setText(LoginActivity.userProFile.getName());
        edtEmail.setText(LoginActivity.userProFile.getEmail());
        edtPhone.setText(LoginActivity.userProFile.getPhone());
        edtAdress.setText(LoginActivity.userProFile.getAddress());
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAdress.getText().toString();
                String userId = LoginActivity.userProFile.getId();

                updateProfile(name, email, phone, address, userId);
                transformData(userId);

                Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

    }
//update profile
    private void updateProfile(String fullName, String email, String phone, String address, String userId) {

        if (fullName.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "FullName is empty.", Toast.LENGTH_LONG).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Email is empty.", Toast.LENGTH_LONG).show();
            edtEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Phone number is empty.", Toast.LENGTH_LONG).show();
            return;
        }

        if (address.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Address number is empty.", Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User(userId, fullName, email, phone, address);
        database.child(userId).setValue(user);
    //update data to auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();
        fbUser.updateEmail(user.getEmail());
        mAuth.updateCurrentUser(fbUser);
}

//Transform data to loginactivity
    private void transformData(String userId) {
        database.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue(User.class)));
                    User user = task.getResult().getValue(User.class);
                    LoginActivity.userProFile.setName(user.getName());
                    LoginActivity.userProFile.setEmail(user.getEmail());
                    LoginActivity.userProFile.setPhone(user.getPhone());
                    LoginActivity.userProFile.setAddress(user.getAddress());

                }
            }
        });
    }
}
