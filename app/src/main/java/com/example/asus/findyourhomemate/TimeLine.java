package com.example.asus.findyourhomemate;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.asus.findyourhomemate.common;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class TimeLine extends AppCompatActivity{
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
    boolean rs = false;
    String message2=null;
    String message3=null;
    ArrayList<common.Announcement> announcements = new ArrayList<common.Announcement>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        mDraverLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDraverLayout,R.string.open,R.string.close);
        mDraverLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar =  (ProgressBar) findViewById(R.id.progress);
        manager = new LinearLayoutManager(this);
        Intent intent = getIntent();
        message2 = intent.getStringExtra("MyPosts");
        message3 = intent.getStringExtra("Favorite");
        try {
            getAllAnnouncements(message2,message3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] a = {"23" , "3" , "66" , "73" , "88" , "9" , "2" , "1" , "41" , "63" , "4" , "5" , "8" , "97" , "89" };
        list = new ArrayList(Arrays.asList(a));
        adapter = new Adapter(announcements,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems =manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                if(isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false;
                    fetchData();
                }
            }
        });
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.mprofile: {
                        Intent launchActivity= new Intent(TimeLine.this,SignUp.class);
                        launchActivity.putExtra("TimeLine", "1");
                        startActivity(launchActivity);
                        break;
                    }
                    case R.id.mlogout: {
                        User user = new User(TimeLine.this);
                        user.removeUser();
                        Intent launchActivity= new Intent(TimeLine.this,SignIn.class);
                        startActivity(launchActivity);
                        break;
                    }
                    case R.id.mposts: {
                        Intent launchActivity= new Intent(TimeLine.this,TimeLine.class);
                        launchActivity.putExtra("MyPosts", "1");
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                    case R.id.mnewpost: {
                        Intent launchActivity= new Intent(TimeLine.this,CreateNewPost.class);
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                    case R.id.mfav: {
                        Intent launchActivity= new Intent(TimeLine.this,TimeLine.class);
                        launchActivity.putExtra("Favorite", "1");
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                    case R.id.mtimeline: {
                        Intent launchActivity= new Intent(TimeLine.this,TimeLine.class);
                        startActivity(launchActivity);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<5 ; i++){
                    list.add(Math.floor(Math.random()*100)+"");
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, 5000);
    }

    public void getAllAnnouncements(String myposts,String fav) throws SQLException {
        createDbConnection();
        String query;
        if (con != null) {


            if(myposts!=null){
                User user = new User(TimeLine.this);
                query ="EXEC [dbo].[getUserAnnouncements] @user_nickname="+user.getUserName()+";";
            }
            else if(fav != null){
                User user = new User(TimeLine.this);
                query ="EXEC [dbo].[getUserFavorites] @user_id="+Integer.parseInt(user.getID())+";";
            }else{
                query ="EXEC [dbo].[getAllAnnouncement] ;";
            }
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                common.Announcement announcement = new common.Announcement();
                announcement.owner = rs.getString("user_name");
                announcement.id = rs.getString("nickname");
                announcement.country = rs.getString("country");
                announcement.city = rs.getString("city");
                announcement.neighbour = rs.getString("neighbour");
                announcement.street = rs.getString("street");
                announcement.buildingnumber = rs.getString("building_number");
                announcement.zipcode = rs.getString("zipcode");
                announcement.explanation = rs.getString("explanation");
                announcement.telno = rs.getString("telno");
                announcement.announcementid = rs.getString("annoucementid");

                announcements.add(announcement);
            }

            con.close();
        }
    }
    public void createDbConnection(){
        con = DbConnect.createConnection();
        if(con == null){
            Toast.makeText(getApplicationContext(), "We have faced with a problem while trying to connect database :(", LENGTH_LONG).show();
        }
    }


    public void announcementDetail(View view) {
    }
}
