package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.databinding.ActivityFightBinding;

import java.util.List;
import java.util.Random;

import io.github.muddz.styleabletoast.StyleableToast;

public class fightActivity extends AppCompatActivity {

    ActivityFightBinding mFightBinding;

    private static final String USER_ID_KEY = "om.example.project2.userIdKey";
    Button returnButton;
    Button fightButton;
    Button defendButton;
    Button potionButton;

    ImageView userBeast;
    ImageView opponentBeast;

    TextView userHealth;

    TextView opponentHealth;

    TextView userBeastName;

    TextView opponentBeastName;

    ProgressBar userHpBar;
    ProgressBar opHpBar;

    private BeastBrawlDAO mBeastBrawlDAO;

    private SharedPreferences mPreferences = null;

    private int UserId = -1;

    private User mUser;

    private teamLog mUserTeam;

    private Beast userBeast1;
    private Beast userBeast2;
    private Beast opponentBeast1;
    Random randomChoice = new Random();
    int potionUses = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mFightBinding = ActivityFightBinding.inflate(getLayoutInflater());
        View view = mFightBinding.getRoot();
        setContentView(view);

        getDatabase();
        checkForUser();
        addUserToPreference(UserId);
        loginUser(UserId);
        checkForUserTeam();

        randomChoice.setSeed(System.currentTimeMillis());
        wireUpDisplay();
        setBeasts();
        setBeastNames();
        setBeastHealth();

        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attackTurn(false,false);
                if(!checkHealth(opponentBeast1)){
                    Toast.makeText(fightActivity.this,"YOU WON", Toast.LENGTH_SHORT).show();
                }

            }
        });

        defendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attackTurn(true,false);
                if(!checkHealth(opponentBeast1)){
                    Toast.makeText(fightActivity.this,"YOU WON", Toast.LENGTH_SHORT).show();
                }
                if(!checkHealth(userBeast1)){
                    Toast.makeText(fightActivity.this,"YOU LOST", Toast.LENGTH_SHORT).show();
                }
            }
        });

        potionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(potionUses > 0){
                    potionUses--;
                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            StyleableToast.makeText(fightActivity.this,"You only have "+ potionUses+ " potions left", Toast.LENGTH_SHORT,R.style.mytoast).show();
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(r, 1000);
                }else{
                    return;
                }
                attackTurn(false,true);

            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });



    }

    private void wireUpDisplay() {
        fightButton = mFightBinding.attackButton;
        returnButton = mFightBinding.leaveButton;
        defendButton = mFightBinding.defendButton;
        potionButton = mFightBinding.potionButton;
        userHpBar = mFightBinding.userHpBar;
        opHpBar = mFightBinding.opHpBar;

        userHealth = mFightBinding.hpLabel;
        userBeastName = mFightBinding.userBeastNameLabel;

        opponentHealth = mFightBinding.opponentHpLabel;
        opponentBeastName = mFightBinding.opponentBeastNameLabel;

        userBeast = mFightBinding.userBeast;

        opponentBeast = mFightBinding.opponentBeast;
    }

    private boolean checkForWinner(){
        Runnable r = new Runnable() {
            @Override
            public void run(){
                StyleableToast.makeText(fightActivity.this,"Please leave fight to restart...", Toast.LENGTH_SHORT,R.style.mytoast).show();
            }
        };
        Handler h = new Handler();

        if(!checkHealth(opponentBeast1)){
            StyleableToast.makeText(fightActivity.this,"YOU WON", Toast.LENGTH_SHORT,R.style.mytoast).show();

            h.postDelayed(r, 1000);
            return true;
        }
        if(!checkHealth(userBeast1)){
            StyleableToast.makeText(fightActivity.this,"YOU LOST", Toast.LENGTH_SHORT,R.style.mytoast).show();
            h.postDelayed(r, 1000);
            return true;
        }
        return false;
    }

    private void attackTurn(Boolean defend, Boolean Item){
        if(Item){
            int potionHeal = attributes.getBeastHealth(userBeast1.getBeastName())/4;
            StyleableToast.makeText(fightActivity.this, "You used a potion to heal " + potionHeal+ " health", Toast.LENGTH_SHORT,R.style.mytoast).show();
            userBeast1.setHealth(userBeast1.getHealth() + potionHeal);
            setBeastHealth();
            Runnable r = new Runnable() {
                @Override
                public void run(){
                }
            };
            Handler h = new Handler();
            h.postDelayed(r, 1000);
        }


        //if damageTaken is 1, then that means opponent guarded and didn't attack you
        int opponentAttack = opponentTurn(defend);

        int userAttackDamage = 0;

        if(opponentAttack == 0){
            opponentAttack = 1;
            StyleableToast.makeText(fightActivity.this, "Opponent guarded and only attacked you for "+(opponentAttack)+" damage", Toast.LENGTH_SHORT,R.style.mytoast).show();
            userAttackDamage = attack.attackTarget(userBeast1, opponentBeast1,true);
        }else{
            StyleableToast.makeText(fightActivity.this, "Opponent attacked you for "+(opponentAttack)+" damage", Toast.LENGTH_SHORT,R.style.mytoast).show();
            userAttackDamage = attack.attackTarget(userBeast1, opponentBeast1,false);
        }

        userBeast1.setHealth(userBeast1.getHealth()-opponentAttack);
        setBeastHealth();

        int finalUserAttackDamage = userAttackDamage;
        Runnable r = new Runnable() {
            @Override
            public void run(){
                if(!defend){
                    if(Item){

                    }else{
                        StyleableToast.makeText(fightActivity.this, "You attacked opponent for "+(finalUserAttackDamage)+" damage", Toast.LENGTH_SHORT,R.style.mytoast).show();
                        opponentBeast1.setHealth(opponentBeast1.getHealth()- finalUserAttackDamage);
                    }
                }else{
                    StyleableToast.makeText(fightActivity.this, "You gaurded and only did "+(1)+" damage", Toast.LENGTH_SHORT,R.style.mytoast).show();
                    opponentBeast1.setHealth(opponentBeast1.getHealth()- 1);
                }

                setBeastHealth();
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 1500);
    }

    private boolean checkHealth(Beast b){
        return b.getHealth()> 0;
    }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Are you sure you want to quit the match?");

        alertBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = landingActivity.intentFactory(getApplicationContext(),UserId);
                        startActivity(intent);
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

    private void setBeastNames() {
        userBeastName.setText("Name: " + userBeast1.getBeastName());
        opponentBeastName.setText("Name: " + opponentBeast1.getBeastName());

    }

    private void setBeastHealth() {
        userHpBar.setMax(attributes.getBeastHealth(userBeast1.getBeastName()));
        opHpBar.setMax(attributes.getBeastHealth(opponentBeast1.getBeastName()));
        if(userBeast1.getHealth() > attributes.getBeastHealth(userBeast1.getBeastName())){
            userHealth.setText("HP: "+ attributes.getBeastHealth(userBeast1.getBeastName()) + "/"+ attributes.getBeastHealth(userBeast1.getBeastName()));
            userHpBar.setProgress(attributes.getBeastHealth(userBeast1.getBeastName()));
        }
        else if(userBeast1.getHealth() >0){
            userHealth.setText("HP: "+ userBeast1.getHealth() + "/"+ attributes.getBeastHealth(userBeast1.getBeastName()));
            userHpBar.setProgress(userBeast1.getHealth(),true);
        }else{
            userHealth.setText("HP: 0/"+ attributes.getBeastHealth(userBeast1.getBeastName()));
            userHpBar.setProgress(0,true);
        }

        if(opponentBeast1.getHealth() > 0){
            opponentHealth.setText("HP: "+ opponentBeast1.getHealth() + "/"+ attributes.getBeastHealth(opponentBeast1.getBeastName()));
            opHpBar.setProgress(opponentBeast1.getHealth(),true);
        }else{
            opponentHealth.setText("HP: 0/" + attributes.getBeastHealth(opponentBeast1.getBeastName()));
            opHpBar.setProgress(0,true);
        }
    }

    private void setBeasts() {
        userBeast.setImageResource(mUserTeam.getMonster1Img());
        opponentBeast.setImageResource(Beast.SnakeImg);

        int newHealth = attributes.getBeastHealth(mUserTeam.getMonster1());
        int newDefense = attributes.getBeastDefense(mUserTeam.getMonster1());
        int newAttack = attributes.getBeastAttack(mUserTeam.getMonster1());
        userBeast1 = new Beast(mUserTeam.getMonster1(),newHealth,newDefense,newAttack);

        newHealth = attributes.getBeastHealth(mUserTeam.getMonster2());
        newDefense = attributes.getBeastDefense(mUserTeam.getMonster2());
        newAttack = attributes.getBeastAttack(mUserTeam.getMonster2());
        userBeast2 = new Beast(mUserTeam.getMonster1(),newHealth,newDefense,newAttack);

        opponentBeast1 = new Beast(mBeastBrawlDAO.getBeastByBeastName(Beast.snake));
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
            teamLog newTeam = new teamLog(UserId, Beast.mouse, Beast.lobo);
            mBeastBrawlDAO.insert(newTeam);
        }
        mUserTeam = mBeastBrawlDAO.getTeamLogByUserId(UserId);


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
    private void loginUser(int userId) {
        mUser = mBeastBrawlDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
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

    private int opponentTurn(Boolean uGuard){
        int attackDam = attack.attackTarget(opponentBeast1, userBeast1, uGuard);
        if(userBeast1.getHealth() == attributes.getBeastHealth(userBeast1.getBeastName())){
            return( attackDam);
        }
        else if(userBeast1.getHealth() - attackDam <= 0){
            return( attackDam);
        }
        else if(opponentBeast1.getHealth() <= 4){
            return(0);
        }else{
            int choice = randomChoice.nextInt(2);
            if(choice == 0){
                return(attackDam);
            }else {
                return 0;
            }
        }
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, fightActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }

}