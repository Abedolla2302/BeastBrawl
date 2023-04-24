package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.databinding.ActivityMainBinding;

import java.util.List;

/*
* Desc:Main ACTIVITY
* */
public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "om.example.project2.userIdKey";

    private Button mLoginButton;
    private Button mSignUpButton;

    private User mUser;

    private int UserId = -1;
    private BeastBrawlDAO mBeastBrawlDAO;

    ActivityMainBinding mMainBinding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        checkForUser();

        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mMainBinding.getRoot();

        setContentView(view);

        wireupDisplay();

        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Success!!",Toast.LENGTH_SHORT).show();
                Intent intent = loginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Sign Up Success!!",Toast.LENGTH_SHORT).show();
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    private void checkForUser() {
        //do we have user in intent?
        UserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        //do we have user in preferences?
        if(UserId != -1){
            Intent intent = optionsActivity.intentFactory(getApplicationContext(), UserId);
            startActivity(intent);
            return;
        }

        SharedPreferences preferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);

        UserId= preferences.getInt(USER_ID_KEY, -1);
        if(UserId != -1){
            Intent intent = optionsActivity.intentFactory(getApplicationContext(), UserId);
            startActivity(intent);
            return;
        }

        //do we have users?
        List<User> users = mBeastBrawlDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("testuser1", "testuser1",0);
            User defaultUser2 = new User("admin2", "admin2",1);
            mBeastBrawlDAO.insert(defaultUser,defaultUser2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void wireupDisplay(){
        mLoginButton = findViewById(R.id.loginButton);
        mSignUpButton = findViewById(R.id.signUpButton);
    }
//    private void loginUser(int userId) {
//        mUser = mBeastBrawlDAO.getUserByUserId(userId);
//        invalidateOptionsMenu();
//    }

    private void getDatabase(){
        mBeastBrawlDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().BeastBrawlDAO();
    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;

    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}