package com.example.asus.findyourhomemate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignIn extends AppCompatActivity {
    Connection con=null;
    ResultSet rs = null;
    Statement stm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void logIn (View view ) throws SQLException {
        createDbConnection();
        String user_name = ((EditText) findViewById(R.id.signInUserNameEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.signInPasswordEditText)).getText().toString();
        ResultSet rs =isUserExist(user_name);
    }

    public void createDbConnection(){
        con = DbConnect.createConnection();
        if(con == null){
            Toast.makeText(getApplicationContext(), "We have faced with a problem when tried to connect database :(", Toast.LENGTH_LONG).show();
        }
    }

    public ResultSet isUserExist(String username) throws SQLException {
        String query ="EXEC [dbo].[isUserExist] @user_nickname="+username+";";
        rs = stm.executeQuery(query);
        if(rs.next()) {
            return rs;
        }
        else {
            return rs;
        }
    }
}
