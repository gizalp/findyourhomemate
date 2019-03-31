package com.example.asus.findyourhomemate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.findyourhomemate.DbConnect;
import com.example.asus.findyourhomemate.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    Connection con=null;
    boolean rs = false;
    Statement stm = null;
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

    public void  signUp(View view) throws SQLException {
        createDbConnection();
        if(con != null){
            stm = con.createStatement();
            common.User userclass =new common.User();
            userclass.user_name = ((EditText) findViewById(R.id.signUpNameEditText)).getText().toString();
            userclass.username = ((EditText) findViewById(R.id.signUpUserNameEditText)).getText().toString();
            userclass.email = ((EditText) findViewById(R.id.signUpEmailEditText)).getText().toString();
            userclass.address = ((EditText) findViewById(R.id.signUpAddressEditText)).getText().toString();
            userclass.password = ((EditText) findViewById(R.id.signUpPasswordEditText)).getText().toString();

            if(true){
                if(true) {
                    String query = "EXEC [dbo].[addUser] @user_name= " + userclass.user_name + ", @user_surname=" + userclass.user_surname + ", @user_email =" + userclass.email + ",@user_nickname=" + userclass.username + ",@user_address=" + userclass.address + " , @user_password=" + userclass.password + ";";
                    rs = stm.execute(query);

                    if (rs) {
                        Toast.makeText(getApplicationContext(), "Sign up successfull!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sign up unsuccessfull!", Toast.LENGTH_LONG).show();
                    }
                }
            }
                con.close();
        }
    }

    public void createDbConnection(){
        con = DbConnect.createConnection();
        if(con == null){
            Toast.makeText(getApplicationContext(), "We have faced with a problem while trying to connect database :(", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isUserInfosOK(common.User userclass){
        if(userclass.user_name==null || userclass.user_name.equals("")){
            Toast.makeText(getApplicationContext(), "Name field can not be null!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userclass.user_surname==null || userclass.user_surname.equals("")){
            Toast.makeText(getApplicationContext(), "Surname field can not be null!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userclass.username==null || userclass.username.equals("")){
            Toast.makeText(getApplicationContext(), "Username field can not be null!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userclass.email==null || userclass.email.equals("")){
            Toast.makeText(getApplicationContext(), "Email field can not be null!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userclass.password==null || userclass.password.equals("")){
            Toast.makeText(getApplicationContext(), "Password field can not be null!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userclass.address==null || userclass.address.equals("")){
            Toast.makeText(getApplicationContext(), "Address field can not be null!", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }

    }

    public boolean isUserExist(String username) throws SQLException {
        String query ="EXEC [dbo].[isUserExist] @user_nickname="+username+";";
        ResultSet rs = stm.executeQuery(query);
        if(rs.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void logIn(View view){
        Intent launchActivity= new Intent(SignUp.this,SignIn.class);
        startActivity(launchActivity);
    }


}
