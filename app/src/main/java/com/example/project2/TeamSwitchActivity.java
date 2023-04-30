package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.MainActivity;
import com.example.project2.R;
import com.example.project2.databinding.ActivityTeamSwitchBinding;

import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;


/*
* desc: Shows team chose screen. Can only choose two monsters and wont let user leave until they do
* */
public class TeamSwitchActivity extends AppCompatActivity {
    ActivityTeamSwitchBinding teamSwitchBinding;

    private static final String USER_ID_KEY = "om.example.project2.userIdKey";
    Button teamSwitchButton;

    private BeastBrawlDAO mBeastBrawlDAO;

    private SharedPreferences mPreferences = null;

    Switch loboSwitch;
    Switch mouseSwitch;

    private int UserId = -1;

    private User mUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_switch);

        teamSwitchBinding = ActivityTeamSwitchBinding.inflate(getLayoutInflater());
        View view = teamSwitchBinding.getRoot();

        getDatabase();
        checkForUser();
        addUserToPreference(UserId);
        loginUser(UserId);

        setContentView(view);

        teamSwitchButton = teamSwitchBinding.changeTeamButton;
        loboSwitch = teamSwitchBinding.switchFirst;
        mouseSwitch = teamSwitchBinding.switchSecond;


        teamSwitchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkForTwoBeast()){
                    Toast.makeText(TeamSwitchActivity.this,"Team switch Success!!",Toast.LENGTH_SHORT).show();
                    addTeamToUser();
                    Intent intent = optionsActivity.intentFactory(getApplicationContext(),UserId);
                    startActivity(intent);
                }else{
                    Toast.makeText(TeamSwitchActivity.this,"please pick two beast",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void addTeamToUser() {
        List<String> beastNames = new ArrayList<>();
        List<Integer> imgNames = new ArrayList<>();
        if(loboSwitch.isChecked()){
            beastNames.add(Beast.lobo);

        }
        if(mouseSwitch.isChecked()){
            beastNames.add(Beast.mouse);
        }

        if(mBeastBrawlDAO.getTeamLogByUserId(UserId) != null){
            mBeastBrawlDAO.delete(mBeastBrawlDAO.getTeamLogByUserId(UserId));
        }
        teamLog newTeam = new teamLog(UserId, beastNames.get(0),beastNames.get(1));
        mBeastBrawlDAO.insert(newTeam);
    }


    private boolean checkForTwoBeast() {
        int totalBeast = 0;
        if(loboSwitch.isChecked()){
            totalBeast++;
        }
        if(mouseSwitch.isChecked()){
            totalBeast++;
        }
        if(totalBeast == 2){
            return true;
        }
        return false;
    }


    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, TeamSwitchActivity.class);
        return intent;

    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, TeamSwitchActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }

    private void getDatabase(){
        mBeastBrawlDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().BeastBrawlDAO();
    }

    private void loginUser(int userId) {
        mUser = mBeastBrawlDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
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
            mBeastBrawlDAO.insert(defaultUser);
        }

        Intent intent = MainActivity.intentFactory(this,-1);
        startActivity(intent);
    }

}
