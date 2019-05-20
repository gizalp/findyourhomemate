package com.example.asus.findyourhomemate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class CreateNewPost extends AppCompatActivity {
    Connection con=null;
    Statement stm = null;
    private DrawerLayout mDraverLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);

        mDraverLayout = (DrawerLayout) findViewById(R.id.signUpAcCNP);
        mToggle = new ActionBarDrawerToggle(CreateNewPost.this,mDraverLayout,R.string.open,R.string.close);
        mDraverLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigationViewCreateNewPost);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.mprofile: {
                        Intent launchActivity= new Intent(CreateNewPost.this,SignUp.class);
                        launchActivity.putExtra("TimeLine", "1");
                        startActivity(launchActivity);
                        break;
                    }
                    case R.id.mlogout: {
                        new User(CreateNewPost.this).removeUser();
                        Intent launchActivity= new Intent(CreateNewPost.this,SignIn.class);
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                    case R.id.mposts: {
                        Intent launchActivity= new Intent(CreateNewPost.this,TimeLine.class);
                        launchActivity.putExtra("MyPosts", "1");
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                    case R.id.mtimeline: {
                        Intent launchActivity= new Intent(CreateNewPost.this,TimeLine.class);
                        startActivity(launchActivity);
                        break;
                    }
                    case R.id.mfav: {
                        Intent launchActivity= new Intent(CreateNewPost.this,TimeLine.class);
                        launchActivity.putExtra("Favorite", "1");
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

    }

    public void createPost(View view) throws SQLException {
        User user = new User(CreateNewPost.this);
        common.Announcement a= new common.Announcement();
        a.country= ((EditText) findViewById(R.id.txtItem1a)).getText().toString();
        a.city = ((EditText) findViewById(R.id.txtItem2a)).getText().toString();
        a.neighbour = ((EditText) findViewById(R.id.txtItem3a)).getText().toString();
        a.street = ((EditText) findViewById(R.id.txtItem4a)).getText().toString();
        a.buildingnumber = ((EditText) findViewById(R.id.txtItem5a)).getText().toString();
        a.zipcode = ((EditText) findViewById(R.id.txtItem6a)).getText().toString();
        a.explanation = ((EditText) findViewById(R.id.txtItem8a)).getText().toString();
        con = DbConnect.createConnection();
        if(con == null){
            Toast.makeText(getApplicationContext(), "We have faced with a problem when tried to connect database :(", Toast.LENGTH_LONG).show();
        }else{
            boolean rs = false;
            stm = con.createStatement();
            String query ="EXEC [dbo].[createaPost]  @country='"+a.country.toString()+"', @city='"+a.city.toString()+"', @neighbour='"+a.neighbour.toString()+"', @street='"+a.street.toString()+"', @explanation='"+a.explanation.toString()+"', @buildingnumber='"+a.buildingnumber.toString()+"', @zipcode='"+a.zipcode.toString()+"', @user_id="+ 8+";";
            rs = stm.execute(query);
            if(!rs) {
                Intent launchActivity= new Intent(CreateNewPost.this,Announcement.class);
                launchActivity.putExtra("owner", user.getName());
                launchActivity.putExtra("city", a.city);
                launchActivity.putExtra("neighbour", a.neighbour);
                launchActivity.putExtra("explanation", a.explanation);
                launchActivity.putExtra("country", a.country);
                launchActivity.putExtra("street", a.street);
                launchActivity.putExtra("buildingnumber", a.buildingnumber);
                launchActivity.putExtra("zipcode", a.zipcode);
                launchActivity.putExtra("userid", user.getUserName());
                Toast.makeText(getApplicationContext(), "The announcement created succesfully.", LENGTH_LONG).show();
                startActivity(launchActivity);

            }
            else{
                Toast.makeText(getApplicationContext(), "We have faced with a problem while trying to create your post.", LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
