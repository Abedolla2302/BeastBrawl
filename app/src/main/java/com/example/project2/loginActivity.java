package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.databinding.ActivityLoginBinding;

import java.util.List;

/*
 * Desc:LOGIN ACTIVITY
 * */
public class loginActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "om.example.project2.userIdKey";
    private SharedPreferences mPreferences = null;

    private EditText mUserNameEdittext;
    private EditText mPassWordEditText;
    private Button mLoginButton;

    private int UserId = -1;
    private BeastBrawlDAO mBeastBrawlDAO;
    private String mUsernameString;
    private String mPasswordString;

    private User mUser;


    ActivityLoginBinding mLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = mLoginBinding.getRoot();

        setContentView(view);

        wireupDisplay();
        getDatabase();



        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Toast.makeText(loginActivity.this,"Success!!",Toast.LENGTH_SHORT).show();

                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(validatePassword()){
                        Intent intent = optionsActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                        startActivity(intent);
                    }else{
                        Toast.makeText(loginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    private void getValuesFromDisplay(){
        mUsernameString = mUserNameEdittext.getText().toString();
        mPasswordString = mPassWordEditText.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        mUser = mBeastBrawlDAO.getUserByUsername(mUsernameString);
        if(mUser == null){
            Toast.makeText(this, "no user " + mUsernameString + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword(){
        return mUser.getPassword().equals(mPasswordString);
    }

    private void loginUser(int userId) {
        mUser = mBeastBrawlDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item2:
                logoutUser();
            default:

        }
        return true;
    }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Are you sure you want to log out?");

        alertBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        UserId = -1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton("no",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alertBuilder.create().show();
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY,-1);
    }
    private void clearUserFromPref(){
        addUserToPreference(-1);

    }
    private void addUserToPreference(int userId) {
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }
    private void getPrefs() {
        mPreferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);
    }
    private void checkForUser() {
        //do we have user in intent?
        UserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        //do we have user in preferences?
        if(UserId != -1){
            return;
        }
        if(mPreferences != null){
            getPrefs();
        }
        UserId= mPreferences.getInt(USER_ID_KEY, -1);

        if(UserId != -1){
            return;
        }

        //do we have users?
        List<User> users = mBeastBrawlDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("testuser1", "testuser1",0);
            User defaultUser2 = new User("admin2", "admin2",1);
            mBeastBrawlDAO.insert(defaultUser,defaultUser2);
        }

        Intent intent = MainActivity.intentFactory(this,-1);
        startActivity(intent);
    }

//    private void logoutUser(){
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//
//        alertBuilder.setMessage("logging out");
//
//        alertBuilder.setPositiveButton((getString("yes")))
//    }

    private void wireupDisplay(){
        mUserNameEdittext = findViewById(R.id.userNameEditText);
        mPassWordEditText= findViewById(R.id.passWordEditText);
        mLoginButton = findViewById(R.id.loginButton);
    }

    private void getDatabase(){
        mBeastBrawlDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().BeastBrawlDAO();
    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, loginActivity.class);
        return intent;

    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, loginActivity.class);

        return intent;
    }
}