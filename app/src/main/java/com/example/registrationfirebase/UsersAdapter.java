package com.example.registrationfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {
    ArrayList<User> users;
    Context context;

    public UsersAdapter(ArrayList<User> users, Context context){
        this.users = users;
        this.context = context;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // from here, load the user_item and fill in the data
        View v = LayoutInflater.from(context).inflate(R.layout.user_item, null);
        TextView tvUsername, tvEmail, tvPassword;
        tvUsername = v.findViewById(R.id.tvUsername);
        tvEmail = v.findViewById(R.id.tvEmail);
        tvPassword = v.findViewById(R.id.tvPassword);

        // filling in data
        User user = users.get(i);
        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
        tvPassword.setText(user.getPassword());

        // return filled-in view
        return v;
    }
}
