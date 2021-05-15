package com.edu.vn.orderfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.vn.orderfoodapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtFullName, edtEmail, editPassword, edtConfirmPw, edtPhone, edtAdress;
    private Button signUpBtn;
    private TextView lblSigIn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        edtFullName = findViewById(R.id.edt_full_name);
        edtEmail = findViewById(R.id.edt_email);
        editPassword = findViewById(R.id.edt_password);
        edtConfirmPw = findViewById(R.id.edt_confirm_pw);
        edtPhone = findViewById(R.id.edt_phone);
        edtAdress = findViewById(R.id.edt_address);
        progressBar = findViewById(R.id.sign_up_progress_bar);
        lblSigIn = findViewById(R.id.sign_in_link);
        signUpBtn = findViewById(R.id.sign_up_btn);

        // hide progress bar
        progressBar.setVisibility(View.INVISIBLE);

        // process when clicked lbl sign in
        lblSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        // process when clicked sign up button
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signUpBtn.setVisibility(View.INVISIBLE);

                String fullName = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = editPassword.getText().toString();
                String confirm = edtConfirmPw.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAdress.getText().toString();

                if(fullName.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "FullName is empty.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(email.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Email is empty.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Password is empty.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(confirm.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Confirm password is empty.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(phone.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Phone number is empty.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(address.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Address number is empty.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(confirm.equals(password)){
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser fbUser = mAuth.getCurrentUser();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                            User user = new User(fbUser.getUid(), fullName, email, phone, address);
                            db.child("users").child(fbUser.getUid()).setValue(user);

                            Intent intent = new Intent();
                            intent.putExtra(User.EMAIL_KEY, email);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    });
                }else{
                    Toast.makeText(SignUpActivity.this, "Confirm password is invalid.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}