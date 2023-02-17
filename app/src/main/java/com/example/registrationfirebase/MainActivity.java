package com.example.registrationfirebase;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    UsersAdapter adapter;
    ListView listView;
    ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        users = new ArrayList<>();
        adapter = new UsersAdapter(users, this);
        listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);

        // get data from firebase
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                Toast.makeText(this, "Error getting users: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            QuerySnapshot snapshots = task.getResult();
            // check if any data exists
            if (snapshots.isEmpty()){
                Toast.makeText(this, "No users have been registered", Toast.LENGTH_SHORT).show();
                return;
            }

            // loop through the documents, getting users data
            for (QueryDocumentSnapshot snapshot: snapshots){
                User user = snapshot.toObject(User.class);
                // add to users array
                users.add(user);
            }

            // refresh view with data from the adapter
            adapter.notifyDataSetChanged();
        });
    }


}