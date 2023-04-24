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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
* desc: This is the Landing Screen. Accidentally names options but cant refactor without issues
* */

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.databinding.ActivityOptionsBinding;

import java.util.List;

public class optionsActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "om.example.project2.userIdKey";
    private int UserId = -1;

    private SharedPreferences mPreferences = null;
    private BeastBrawlDAO mBeastBrawlDAO;
    ActivityOptionsBinding optionsBinding;
    Button fightButton;
    Button changeTeamButton;
    Button editAttributesButton;
    TextView helloUserText;
    ImageView attributeImage;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        getDatabase();
        checkForUser();
        addUserToPreference(UserId);
        loginUser(UserId);
        createBeast();

        optionsBinding = ActivityOptionsBinding.inflate(getLayoutInflater());
        View view = optionsBinding.getRoot();

        setContentView(view);

        fightButton = optionsBinding.fightButton;
        changeTeamButton = optionsBinding.changeTeamScreenButton;
        editAttributesButton = optionsBinding.changeAttributesButton;
        attributeImage = optionsBinding.imageView4;

        if(mUser.getIsAdmin() == 1){
            editAttributesButton.setVisibility(View.VISIBLE);
            attributeImage.setVisibility(View.VISIBLE);

        }else{
            editAttributesButton.setVisibility(View.INVISIBLE);
            attributeImage.setVisibility(View.INVISIBLE);
        }

        helloUserText = optionsBinding.titlep;
        helloUserText.setText("Hello "+mUser.getUserName()+ "!");

        changeTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(optionsActivity.this, "Change Team pressed!!", Toast.LENGTH_SHORT).show();
                Intent intent = TeamSwitchActivity.intentFactory(getApplicationContext(), UserId);
                startActivity(intent);
            }
        });
    }

    private void createBeast() {
        List<Beast> beast = mBeastBrawlDAO.getAllBeast();
        if(beast.size() <= 0){
            Beast lobo = new Beast(Beast.lobo,15,4,7);
            Beast mouse = new Beast(Beast.mouse,10,10,6);
            mBeastBrawlDAO.insert(lobo, mouse);
        }
    }

    private void loginUser(int userId) {
        mUser = mBeastBrawlDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mUser != null){
            MenuItem item = menu.findItem(R.id.item1);
            item.setTitle(mUser.getUserName());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //TODO
    private void addUserToPreference(int userId) {
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, optionsActivity.class);
        return intent;

    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, optionsActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
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
            mBeastBrawlDAO.insert(defaultUser);
        }

        Intent intent = MainActivity.intentFactory(this,-1);
        startActivity(intent);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);
    }

    private void getDatabase(){
        mBeastBrawlDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().BeastBrawlDAO();
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

}