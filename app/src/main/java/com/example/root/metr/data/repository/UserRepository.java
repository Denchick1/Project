package com.example.root.metr.data.repository;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.root.metr.data.IUserRepository;
import com.example.root.metr.models.DTO.User;
import com.example.root.metr.root.App;
import com.google.gson.Gson;

import polanski.option.Option;

public class UserRepository implements IUserRepository {

    private final String NAME_PREF="user";
    private final String KEY="user";

    @Override
    public User getUser() {
        SharedPreferences sharedPreferences= App.INSTANCE.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        String user=sharedPreferences.getString(KEY,"");
        User _user=new Gson().fromJson(user,User.class);
        return Option.ofObj(_user).orDefault(User::new);
    }

    @Override
    public void addUser(User user ) {
        App.setToken(user.getToken());
        String _user= new Gson().toJson(user);
        SharedPreferences sharedPreferences= App.INSTANCE.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY,_user);
        editor.apply();
    }

    @Override
    public boolean hasUser() {
        SharedPreferences sharedPreferences= App.INSTANCE.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        boolean hasUser = sharedPreferences.contains(KEY);
        if(hasUser){
            User user=getUser();
            App.setToken(user.getToken());
        }
        return hasUser;
    }

    @Override
    public void deleteUser() {
        SharedPreferences sharedPreferences= App.INSTANCE.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(KEY);
        editor.apply();
    }
}
