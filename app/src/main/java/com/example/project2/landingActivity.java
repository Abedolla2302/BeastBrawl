package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
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
import com.example.project2.databinding.ActivityLandingBinding;

import java.util.List;

public class landingActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "om.example.project2.userIdKey";
    private int UserId = -1;
    private int mTeamId = -1;

    private SharedPreferences mPreferences = null;
    private BeastBrawlDAO mBeastBrawlDAO;
    ActivityLandingBinding optionsBinding;
    Button fightButton;
    Button changeTeamButton;
    Button editAttributesButton;
    TextView helloUserText;
    ImageView attributeImage;
    TextView teamTextView;
    private User mUser;
    private teamLog mUserTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getDatabase();
        checkForUser();
        addUserToPreference(UserId);
        loginUser(UserId);
        createBeast();
        checkForUserTeam();
        createNotificationChannel();

        optionsBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        View view = optionsBinding.getRoot();

        setContentView(view);

        wireUpDisplay();
//
        teamTextView.setText("Your current team is " + mUserTeam.getMonster1() + " and " + mUserTeam.getMonster2());

        if (mUser.getIsAdmin() == 1) {
            editAttributesButton.setVisibility(View.VISIBLE);
            attributeImage.setVisibility(View.VISIBLE);

        } else {
            editAttributesButton.setVisibility(View.INVISIBLE);
            attributeImage.setVisibility(View.INVISIBLE);
        }

        helloUserText = optionsBinding.titlep;
        helloUserText.setText("Hello " + mUser.getUserName() + "!");

        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(landingActivity.this, "myChannel")
                        .setSmallIcon(Beast.loboImg)
                        .setContentTitle("BeastBrawl")
                        .setContentText("Don't Leave the Fight")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(landingActivity.this);
                if (ActivityCompat.checkSelfPermission(landingActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                notificationManager.notify(123, builder.build());
                askUserToFlipScreen();

            }
        });
        changeTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(landingActivity.this, "Change Team pressed!!", Toast.LENGTH_SHORT).show();
                Intent intent = TeamSwitchActivity.intentFactory(getApplicationContext(), UserId);
                startActivity(intent);
                finish();
            }
        });

        editAttributesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(landingActivity.this, "Change Attributes pressed!!", Toast.LENGTH_SHORT).show();
                Intent intent = attributesActivity.intentFactory(getApplicationContext(), UserId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void wireUpDisplay() {
        fightButton = optionsBinding.fightButton;
        changeTeamButton = optionsBinding.changeTeamScreenButton;
        editAttributesButton = optionsBinding.changeAttributesButton;
        attributeImage = optionsBinding.imageView4;
        teamTextView = optionsBinding.teamTextView;
    }

    private void createBeast() {
        List<Beast> beast = mBeastBrawlDAO.getAllBeast();
        if(beast.size() <= 0){
            Beast lobo = new Beast(Beast.lobo,attributes.loboHealth,attributes.loboDefense,attributes.loboAttack);

            Beast mouse = new Beast(Beast.mouse,attributes.mouseHealth,attributes.mouseDefense,attributes.mouseAttack);

            Beast snake = new Beast(Beast.snake,attributes.snakeHealth,attributes.snakeDefense,attributes.snakeAttack);

            Beast bird = new Beast(Beast.bird,attributes.birdHealth,attributes.birdDefense,attributes.birdAttack);

            mBeastBrawlDAO.insert(lobo, mouse, snake,bird);
        }
        attributes.setLoboDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.lobo).getDefense());
        attributes.setLoboHealth(mBeastBrawlDAO.getBeastByBeastName(Beast.lobo).getHealth());
        attributes.setLoboDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.lobo).getDefense());

        attributes.setMouseDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.mouse).getDefense());
        attributes.setMouseHealth(mBeastBrawlDAO.getBeastByBeastName(Beast.mouse).getHealth());
        attributes.setMouseDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.mouse).getDefense());

        attributes.setSnakeDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.snake).getDefense());
        attributes.setSnakeHealth(mBeastBrawlDAO.getBeastByBeastName(Beast.snake).getHealth());
        attributes.setSnakeDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.snake).getDefense());


        attributes.setBirdDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.bird).getDefense());
        attributes.setBirdHealth(mBeastBrawlDAO.getBeastByBeastName(Beast.bird).getHealth());
        attributes.setBirdDefense(mBeastBrawlDAO.getBeastByBeastName(Beast.bird).getDefense());

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
        Intent intent = new Intent(context, landingActivity.class);
        return intent;

    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, landingActivity.class);
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
        finish();
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);
    }

    private void getDatabase(){
        mBeastBrawlDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().BeastBrawlDAO();
    }

    private void checkForUserTeam() {
        //is there a team for this user already?
        teamLog userTeam = mBeastBrawlDAO.getTeamLogByUserId(UserId);
        if(userTeam == null){
            teamLog newTeam = new teamLog(UserId, Beast.mouse,Beast.lobo);
            mBeastBrawlDAO.insert(newTeam);
        }
        mUserTeam = mBeastBrawlDAO.getTeamLogByUserId(UserId);
        mTeamId = mUserTeam.getLogId();

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
    private void askUserToFlipScreen(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Please rotate screen");

        alertBuilder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = fightActivity.intentFactory(getApplicationContext(), UserId);
                        startActivity(intent);
                        finish();
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BeastBrawl";
            String description = "Ready to fight...";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("myChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}