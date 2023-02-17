package com.example.registrationfirebase;

import java.util.HashMap;
import java.util.Map;

public class User {
    String id, username, email, password;

    public User() {
        // required default constructor
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    // hashmap object that will be saved to firebase
    public Map<String, String> toMap(){
        HashMap<String, String> details = new HashMap<>();
        details.put("username", username);
        details.put("email", email);
        details.put("password", password);
        return details;
    }
}
