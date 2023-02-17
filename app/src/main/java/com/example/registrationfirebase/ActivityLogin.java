package com.example.registrationfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {

    // declare views
    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    String email;
    String password;
    FirebaseAuth mAuth;
    ProgressDialog loader;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // initialize views
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        // listen for btnRegister click
        btnLogin.setOnClickListener(v -> {
            // get user inputs
            email = edtEmail.getText().toString();
            password = edtPassword.getText().toString();

            // check for empty fields
            if (email.isEmpty()){
                edtEmail.setError("Email is required");
                edtEmail.requestFocus();
                return;
            }
            if (password.isEmpty()){
                edtPassword.setError("Password is required");
                edtPassword.requestFocus();
                return;
            }


            // Login user
            loader.setMessage("Login in progress. Please wait...");
            loader.setCanceledOnTouchOutside(false);
            loader.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                // hide loader if is showing
                if (loader.isShowing()) loader.dismiss();
                // check if login is successful
                if (!task.isSuccessful()){
                    Toast.makeText(this, "Unable to Login "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                // Login was successful. Move to main activity
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
            });
        });

        // move to Register activity if user has no account
        tvRegister.setOnClickListener(view->{
            Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
            startActivity(intent);
        });

    }
}