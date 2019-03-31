package com.example.asus.findyourhomemate;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;

    public void removeUser()
    {
        sharedPreferences.edit().clear();
    }

    public String getName() {
        name = sharedPreferences.getString("userdata","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("userdata",name).commit();
    }

    private String name;
    SharedPreferences sharedPreferences;

    public User(Context context){
        this.context=context;
        sharedPreferences = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    }
}
