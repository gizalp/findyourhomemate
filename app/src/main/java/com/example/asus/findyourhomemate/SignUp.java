package com.example.asus.findyourhomemate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.findyourhomemate.DbConnect;
import com.example.asus.findyourhomemate.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SignUp extends AppCompatActivity {

    Connection con=null;
    boolean rs = false;
    Statement stm = null;
    private DrawerLayout mDraverLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    String message;
    String message2;
    common.User u=new common.User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        message = intent.getStringExtra("TimeLine");
        message2 = intent.getStringExtra("Announcement");




        if(message!=null ){
            mDraverLayout = (DrawerLayout) findViewById(R.id.signUpAc);
            mToggle = new ActionBarDrawerToggle(SignUp.this,mDraverLayout,R.string.open,R.string.close);
            mDraverLayout.addDrawerListener(mToggle);
            mToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            navigationView = (NavigationView) findViewById(R.id.navigationViewSignUp);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.mprofile: {
                            Intent launchActivity= new Intent(SignUp.this,SignUp.class);
                            launchActivity.putExtra("TimeLine", "1");
                            startActivity(launchActivity);
                            break;
                        }
                        case R.id.mlogout: {
                            new User(SignUp.this).removeUser();
                            Intent launchActivity= new Intent(SignUp.this,SignIn.class);
                            startActivity(launchActivity);
                            finish();
                            break;
                        }

                        case R.id.mposts: {
                            Intent launchActivity= new Intent(SignUp.this,TimeLine.class);
                            launchActivity.putExtra("MyPosts", "1");
                            startActivity(launchActivity);
                            finish();
                            break;
                        }

                        case R.id.mtimeline: {
                            Intent launchActivity= new Intent(SignUp.this,TimeLine.class);
                            startActivity(launchActivity);
                            break;
                        }
                        case R.id.mnewpost: {
                            Intent launchActivity= new Intent(SignUp.this,CreateNewPost.class);
                            startActivity(launchActivity);
                            finish();
                            break;
                        }
                        case R.id.mfav: {
                            Intent launchActivity= new Intent(SignUp.this,TimeLine.class);
                            launchActivity.putExtra("Favorite", "1");
                            startActivity(launchActivity);
                            finish();
                            break;
                        }
                    }
                    return false;
                }
            });
            if(message2!=null){
                try {
                    getUser(message2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            showProfile();

        }else{
            final User user = new User(SignUp.this);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(user.getName()!=""){
                        Intent intent = new Intent(SignUp.this, TimeLine.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },10);
            ImageView ımageViewUs = (ImageView) findViewById(R.id.us);
            ımageViewUs.getLayoutParams().height = 0;
            ımageViewUs.getLayoutParams().width = 0;
        }

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

        public void signUp(View view) throws SQLException {
            if(message==null) {
                createDbConnection();
                if (con != null) {
                    stm = con.createStatement();
                    common.User userclass = new common.User();
                    userclass.user_name = ((EditText) findViewById(R.id.signUpFullEditText)).getText().toString();
                    userclass.username = ((EditText) findViewById(R.id.signUpUserNameEditText)).getText().toString();
                    userclass.email = ((EditText) findViewById(R.id.signUpEmailEditText)).getText().toString();
                    userclass.address = ((EditText) findViewById(R.id.signUpAddressEditText)).getText().toString();
                    //userclass.password = ((EditText) findViewById(R.id.signUpPasswordEditText)).getText().toString();

                    if (true) {
                        if (true) {
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
                else{
                    createDbConnection();
                    if (con != null) {
                        stm = con.createStatement();
                        common.User userclass = new common.User();
                        User user = new User(SignUp.this);
                        userclass.username = user.getName();
                        userclass.user_name = ((EditText) findViewById(R.id.signUpFullEditText)).getText().toString();
                        userclass.address = ((EditText) findViewById(R.id.signUpAddressEditText)).getText().toString();

                        boolean rs = false;
                                String query = "EXEC [dbo].[updateTableUser] @full_name= " + userclass.user_name + ", @address=" + userclass.address + ", @id =" + user.getID() + ";";
                                rs = stm.execute(query);

                                if (!rs) {
                                    Toast.makeText(getApplicationContext(), "Update successfull!", Toast.LENGTH_LONG).show();

                                    user.setName(userclass.user_name);
                                    user.setAddress(userclass.address);
                                    Intent launchActivity= new Intent(SignUp.this,TimeLine.class);
                                    startActivity(launchActivity);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Update unsuccessfull!", Toast.LENGTH_LONG).show();
                                }

                        con.close();
                    }
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

    public void showProfile(){

        TextView textView = (TextView) findViewById(R.id.signUP);
        textView.setText("PROFILE");
        ImageView ımageView = (ImageView) findViewById(R.id.profilepicon);
        ımageView.setImageResource(R.drawable.profile);
        ImageView ımageViewUs = (ImageView) findViewById(R.id.us);
        ımageViewUs.setImageResource(R.drawable.nopicture);

        ımageViewUs.setMaxHeight(50);
        ımageViewUs.setMaxHeight(50);

        Button signupbutton = (Button)findViewById(R.id.btn1);
        signupbutton.setText(R.string.update);
        Button goToLoginButton = (Button)findViewById(R.id.btn2);
        goToLoginButton.setVisibility(View.INVISIBLE);
        User user = new User(SignUp.this);

        EditText username = (EditText) findViewById(R.id.signUpUserNameEditText);


        EditText email = (EditText) findViewById(R.id.signUpEmailEditText);


        EditText address = (EditText) findViewById(R.id.signUpAddressEditText);


        EditText name = (EditText) findViewById(R.id.signUpFullEditText);


        if(message2!=null && !u.username.equals(user.getUserName())){
            username.setEnabled(false);
            username.setText(u.username);
            address.setEnabled(false);
            address.setText(u.address);
            email.setEnabled(false);
            email.setText(u.email);
            name.setEnabled(false);
            name.setText(u.user_name);
            signupbutton.setVisibility(View.INVISIBLE);
            textView.setText(u.user_name.toUpperCase()+"'s PROFILE");
        }
        else{
            username.setEnabled(false);
            username.setText(user.getUserName());
            email.setEnabled(false);
            email.setText(user.getEmail());
            address.setText(user.getAddress());
            name.setText(user.getName());
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void getUser(String username) throws SQLException {
        String query ="EXEC [dbo].[isUserExist] @user_nickname="+username+";";
        con = DbConnect.createConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            Toast.makeText(getApplicationContext(), "Yesss!!!", Toast.LENGTH_LONG).show();
            u.user_name =rs.getString("user_name");
            u.username =rs.getString("user_nickname");
            u.address =rs.getString("user_address");
            u.email =rs.getString("user_email");
        }
        else {
            Toast.makeText(getApplicationContext(), "Nooo!!!", Toast.LENGTH_LONG).show();
        }
    }
}
