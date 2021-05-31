package com.edu.vn.orderfoodapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button loginBtn;
    private ProgressBar progressBar;
    private TextView lblSignUp;
    private CheckBox chkRemember;
    public static User user;
    public static int SIGN_UP_REQUEST_CODE = 1;
    public static String REMEMBER_LOGIN_TAG = "rememberLogin";
    public static String USER_LOGGED_IN = "userLogged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        loginBtn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.login_progress_bar);
        lblSignUp = findViewById(R.id.sign_up_link);
        chkRemember = findViewById(R.id.chk_remember);
        // hiding progressbar
        progressBar.setVisibility(View.INVISIBLE);

        //check logged in
        SharedPreferences sharedPref = getSharedPreferences(REMEMBER_LOGIN_TAG,MODE_PRIVATE);
        String userLoggedJson = sharedPref.getString(USER_LOGGED_IN, "");
        if(! userLoggedJson.isEmpty()){
            Gson gson = new Gson();
            user = gson.fromJson(userLoggedJson, User.class);
            goNextActivity(user);
        }


        //process when clicked to sign up link

        lblSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(intent, SIGN_UP_REQUEST_CODE);
            }
        });

        //process clicked login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                // check fields is empty

                if(email.isEmpty()){
                    loginBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Email is empty.", Toast.LENGTH_LONG).show();
                    edtEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    loginBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Password is empty.", Toast.LENGTH_LONG).show();
                    edtPassword.requestFocus();
                    return;
                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fbUser = mAuth.getCurrentUser();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                            db.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(task.isSuccessful()){

                                        user = task.getResult().getValue(User.class);

                                        // check remember login
                                        if(chkRemember.isChecked()){

                                            Gson gson = new Gson();
                                            SharedPreferences sharedPref = getSharedPreferences(REMEMBER_LOGIN_TAG, MODE_PRIVATE);

                                            //save user to sharedPref
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            String json = gson.toJson(user);
                                            editor.putString(USER_LOGGED_IN, json);
                                            editor.apply();
                                        }

                                        goNextActivity(user);
                                    }
                                }
                            });

                        }else{
                            loginBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"Email / Password is invalid.",Toast.LENGTH_LONG).show();
                            edtPassword.setText("");
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_UP_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            String email = data.getStringExtra(User.EMAIL_KEY);
            edtEmail.setText(email);
        }
    }

    private void goNextActivity(User user){
        // check role
        if(user.getRole() == User.USER_ROLE){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }else if(user.getRole() == User.ADMIN_ROLE){
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
        }
        finish();
    }
}