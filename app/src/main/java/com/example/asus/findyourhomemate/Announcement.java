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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Announcement extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Adapter adapter;
    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;
    ArrayList list;
    ProgressBar progressBar;
    private DrawerLayout mDraverLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    Connection con=null;
    Statement stm = null;
    Boolean rs ;
    ImageView fav ;
    common.Announcement announcement = new common.Announcement();

    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        fav= (ImageView)findViewById(R.id.fav);
        user = new User(Announcement.this);
        mDraverLayout = (DrawerLayout) findViewById(R.id.signUpAcAN);
        mToggle = new ActionBarDrawerToggle(Announcement.this,mDraverLayout,R.string.open,R.string.close);
        mDraverLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigationViewAnnouncement);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.mprofile: {
                        Intent launchActivity= new Intent(Announcement.this,SignUp.class);
                        launchActivity.putExtra("TimeLine", "1");
                        startActivity(launchActivity);
                        break;
                    }
                    case R.id.mlogout: {
                        new User(Announcement.this).removeUser();
                        Intent launchActivity= new Intent(Announcement.this,SignIn.class);
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                    case R.id.mposts: {
                        Intent launchActivity= new Intent(Announcement.this,TimeLine.class);
                        launchActivity.putExtra("MyPosts", "1");
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                    case R.id.mtimeline: {
                        Intent launchActivity= new Intent(Announcement.this,TimeLine.class);
                        startActivity(launchActivity);
                        break;
                    }
                    case R.id.mfav: {
                        Intent launchActivity= new Intent(Announcement.this,TimeLine.class);
                        launchActivity.putExtra("Favorite", "1");
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

        Intent intent = getIntent();

        announcement.id= intent.getStringExtra("userid");
        announcement.owner= intent.getStringExtra("owner");
        announcement.country= intent.getStringExtra("country");
        announcement.city= intent.getStringExtra("city");
        announcement.neighbour= intent.getStringExtra("neighbour");
        announcement.street= intent.getStringExtra("street");
        announcement.buildingnumber= intent.getStringExtra("buildingnumber");
        announcement.zipcode= intent.getStringExtra("zipcode");
        announcement.explanation= intent.getStringExtra("explanation");
        announcement.announcementid= intent.getStringExtra("annoucementid");

        if(announcement.id.equals(user.getUserName())){
            fav.setVisibility(View.INVISIBLE);
        }


        TextView username = (TextView) findViewById(R.id.txtItema);
        username.setText(announcement.owner);

        TextView Country = (TextView) findViewById(R.id.txtItem1a);
        Country.setText(announcement.country);

        TextView City = (TextView) findViewById(R.id.txtItem2a);
        City.setText(announcement.city);

        TextView Neighbour = (TextView) findViewById(R.id.txtItem3a);
        Neighbour.setText(announcement.neighbour);

        TextView Street = (TextView) findViewById(R.id.txtItem4a);
        Street.setText(announcement.street);

        TextView Buildingnumber = (TextView) findViewById(R.id.txtItem5a);
        Buildingnumber.setText(announcement.buildingnumber);

        TextView Zipcode = (TextView) findViewById(R.id.txtItem6a);
        Zipcode.setText(announcement.zipcode);

        TextView Explanation = (TextView) findViewById(R.id.txtItem8a);
        Explanation.setText(announcement.explanation);
        try {
            if(hasFavorite(Integer.parseInt(user.getID().toString()),Integer.parseInt(announcement.announcementid.toString()))){
                fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
            else{
                fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProfile(View view) {
        Intent launchActivity= new Intent(Announcement.this,SignUp.class);
        launchActivity.putExtra("TimeLine", "1");
        launchActivity.putExtra("Announcement", announcement.id);
        startActivity(launchActivity);
    }

    public void addToFavorites(View view) {

        try {
            if(hasFavorite(Integer.parseInt(user.getID().toString()),Integer.parseInt(announcement.announcementid.toString()))){
                fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                addDeleteFavorite(Integer.parseInt(user.getID()),Integer.parseInt(announcement.announcementid),0);
            }else{
                fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                addDeleteFavorite(Integer.parseInt(user.getID()),Integer.parseInt(announcement.announcementid),1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasFavorite(Integer user_id, Integer announcement_id) throws SQLException {
        con = DbConnect.createConnection();
        if (con != null) {
            String query = "EXEC [dbo].[favoriteCheck] @user_id= " + user_id+ ", @announcement_id=" + announcement_id +";";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean addDeleteFavorite(Integer user_id, Integer announcement_id, Integer temp) throws SQLException {
        con = DbConnect.createConnection();
        if (con != null) {
            stm = con.createStatement();
            String query = "EXEC [dbo].[addDeleteFavorite] @user_id= " + user_id+ ", @announcement_id=" + announcement_id + ", @temp=" + temp+";";
            rs = stm.execute(query);

            if (rs) {
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }
}