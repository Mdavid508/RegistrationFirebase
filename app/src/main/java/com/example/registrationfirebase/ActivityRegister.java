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
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityRegister extends AppCompatActivity {

    // declare views
    EditText edtName, edtEmail, edtPassword, edtCPassword;
    Button btnRegister;
    TextView tvLogin;
    String name, email, password, cPassword;
    FirebaseAuth mAuth;
    ProgressDialog loader;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // initialize views
        edtName = findViewById(R.id.username);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        edtCPassword = findViewById(R.id.cPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        loader = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        // listen for btnRegister click
        btnRegister.setOnClickListener(v -> {
            // get user inputs
            name = edtName.getText().toString();
            email = edtEmail.getText().toString();
            password = edtPassword.getText().toString();
            cPassword = edtCPassword.getText().toString();

            // check for empty fields
            if (name.isEmpty()){
                edtName.setError("Username is required");
                edtName.requestFocus();
                return;
            }
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
            if (cPassword.isEmpty()){
                edtCPassword.setError("Confirm Password is required");
                edtCPassword.requestFocus();
                return;
            }
            // check if passwords match
            if (!password.equals(cPassword)){
                edtCPassword.setError("Passwords do not match");
                edtCPassword.requestFocus();
                return;
            }
            // register user
            // show progress indicator
            loader.setMessage("Creating account. Please wait...");
            loader.setCanceledOnTouchOutside(false);
            loader.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                // check if account created successfully
                if (!task.isSuccessful()){
                    Toast.makeText(this, "Unable to create account. "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    // hide loader if showing
                    if (loader.isShowing()) loader.dismiss();
                    return;
                };

                // to save additional details of a user, you need to save this data to a Firestore database
                // create new user instance
                User user = new User();
                user.username = name;
                user.email = email;
                user.password = password;

                db.collection("Users").add(user.toMap()).addOnCompleteListener(task2->{
                    // hide loader if showing
                    if (loader.isShowing()) loader.dismiss();
                    if (!task2.isSuccessful()){
                        Toast.makeText(this, "Unable to save user to firebase: "+task2.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // save successful, move to mainActivity
                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityRegister.this, MainActivity.class);
                    startActivity(intent);
                });
            });
        });

        // move to login activity if the user already has an account
        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
            startActivity(intent);
        });

    }
}