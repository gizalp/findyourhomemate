package com.example.asus.findyourhomemate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.findyourhomemate.DbConnect;

import java.sql.Connection;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    Connection con=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_sign_up);

    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config =new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    public void changeLanguageTR(View view) {
        setLocale("tr");
        recreate();
    }

    public void changeLanguageEN(View view) {
        setLocale("en");
        recreate();
    }

    public void  createDBConnection(View view){
        con = DbConnect.createConnection();
        if(con == null){
            Toast.makeText(getApplicationContext(), "Hata :(", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Başarılı:)!!!", Toast.LENGTH_LONG).show();
        }
    }
}
