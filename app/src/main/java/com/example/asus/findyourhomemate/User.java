package com.example.asus.findyourhomemate;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;

    public void removeUser()
    {
        sharedPreferences.edit().clear().commit();
    }

    public String getName() {
        name = sharedPreferences.getString("fullname","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("fullname",name).commit();
    }

    private String name;

    public String getUserName() {
        userName = sharedPreferences.getString("nickname","");
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        sharedPreferences.edit().putString("nickname",userName).commit();
    }

    private String userName;

    public String getEmail() {
        email = sharedPreferences.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email).commit();
    }

    private String email;

    public String getAddress() {
        address = sharedPreferences.getString("address","");
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        sharedPreferences.edit().putString("address",address).commit();
    }

    private String address;

    public String getID() {
        id = sharedPreferences.getString("id","");
        return id;
    }

    public void setID(String id) {
        this.id = id;
        sharedPreferences.edit().putString("id",id).commit();
    }

    private String id;


    SharedPreferences sharedPreferences;

    public User(Context context){
        this.context=context;
        sharedPreferences = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    }
}
