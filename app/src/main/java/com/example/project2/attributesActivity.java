package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.databinding.ActivityAttributesBinding;

import java.util.List;

public class attributesActivity extends AppCompatActivity {

    ActivityAttributesBinding mAttributesBinding;
    private static final String USER_ID_KEY = "om.example.project2.userIdKey";
    Button attributeButton;
    private BeastBrawlDAO mBeastBrawlDAO;

    private int UserId = -1;

    private User mUser;

    private int mTeamId = -1;

    private SharedPreferences mPreferences = null;

    ActivityAttributesBinding mActivityAttributesBinding;


//    Mouse Buttons
    EditText mouseAttField;
    EditText mouseHpField;
    EditText mouseDefField;

    EditText loboAttField;
    EditText loboHpField;
    EditText loboDefField;


    EditText snakeAttField;
    EditText snakeHpField;
    EditText snakeDefField;

    EditText birdAttfield;
    EditText birdHpField;
    EditText birdDefField;

    Switch loboAltSwitch;
    Switch mouseAltSwitch;
    Switch snakeAltSwitch;

    Switch birdAltSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);
        mAttributesBinding = ActivityAttributesBinding.inflate(getLayoutInflater());
        View view = mAttributesBinding.getRoot();
        setContentView(view);
        getDatabase();
        wireUpDisplay();

        checkForUser();
        addUserToPreference(UserId);
        loginUser(UserId);
        createBeast();

        checkAltForms();


        attributeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkMouseChange();
                checkLoboChangeChange();
                checkSnakeChange();
                checkBirdChange();

                Toast.makeText(attributesActivity.this,"Beast Stats have changed,Going back to landing Page!",Toast.LENGTH_SHORT).show();
                Intent intent = landingActivity.intentFactory(getApplicationContext(),UserId);
                startActivity(intent);

            }

        });
        loboAltSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loboAltSwitch.isChecked()){
                    Beast.setLoboImg(R.drawable.loboalt);
                }else{
                    Beast.setLoboImg(R.drawable.wolf);
                }
            }
        });

        mouseAltSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mouseAltSwitch.isChecked()){
                    Beast.setMouseImg(R.drawable.mousealt);
                }else{
                    Beast.setMouseImg(R.drawable.mouse);
                }
            }
        });

        snakeAltSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(snakeAltSwitch.isChecked()){
                    Beast.setSnakeImg(R.drawable.snakealt);
                }else{
                    Beast.setSnakeImg(R.drawable.snake);
                }
            }
        });

        birdAltSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(birdAltSwitch.isChecked()){
                    Beast.setBirdImage(R.drawable.birdalt);
                }else{
                    Beast.setBirdImage(R.drawable.bird);
                }
            }
        });

    }

    private void checkBirdChange() {
        int newAtt = attributes.birdAttack;
        int newHp = attributes.birdHealth;
        int newDef = attributes.birdDefense;

        int currField = 0;
        try{
            currField = Integer.parseInt(birdAttfield.getText().toString());
        }catch(NumberFormatException e){
            currField = newAtt;
        }


        if(currField != 0 && currField != newAtt){
            newAtt = currField;
            attributes.setBirdAttack(newAtt);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(birdHpField.getText().toString());
        }catch(NumberFormatException e){
            currField = newHp;
        }
        if(currField != 0 && currField != newHp){
            newHp = currField;
            attributes.setBirdHealth(newHp);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(birdDefField.getText().toString());
        }catch(NumberFormatException e){
            currField = newDef;
        }
        if(currField != 0 && currField != newDef){
            newDef= currField;
            attributes.setBirdDefense(newDef);
        }

        mBeastBrawlDAO.delete(mBeastBrawlDAO.getBeastByBeastName(Beast.bird));

        Beast newBird = new Beast(Beast.bird,newHp,newDef,newAtt);
        mBeastBrawlDAO.insert(newBird);

    }

    private void checkAltForms() {
        if(Beast.getLoboImg() == R.drawable.loboalt){
            loboAltSwitch.setChecked(true);
        }
        if(Beast.getMouseImg() == R.drawable.mousealt){
            mouseAltSwitch.setChecked(true);
        }
        if(Beast.getSnakeImg()==R.drawable.snakealt){
            snakeAltSwitch.setChecked(true);
        }
    }

    private void checkSnakeChange() {
        int newAtt = attributes.snakeAttack;
        int newHp = attributes.snakeHealth;
        int newDef = attributes.snakeDefense;

        int currField = 0;
        try{
            currField = Integer.parseInt(snakeAttField.getText().toString());
        }catch(NumberFormatException e){
            currField = newAtt;
        }


        if(currField != 0 && currField != newAtt){
            newAtt = currField;
            attributes.setSnakeAttack(newAtt);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(snakeHpField.getText().toString());
        }catch(NumberFormatException e){
            currField = newHp;
        }
        if(currField != 0 && currField != newHp){
            newHp = currField;
            attributes.setSnakeHealth(newHp);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(snakeDefField.getText().toString());
        }catch(NumberFormatException e){
            currField = newDef;
        }
        if(currField != 0 && currField != newDef){
            newDef= currField;
            attributes.setSnakeDefense(newDef);
        }

        mBeastBrawlDAO.delete(mBeastBrawlDAO.getBeastByBeastName(Beast.snake));

        Beast newSnake = new Beast(Beast.snake,newHp,newDef,newAtt);
        mBeastBrawlDAO.insert(newSnake);
    }

    private void checkLoboChangeChange() {
        int newAtt = attributes.loboAttack;
        int newHp = attributes.loboHealth;
        int newDef = attributes.loboDefense;

        int currField = 0;
        try{
            currField = Integer.parseInt(loboAttField.getText().toString());
        }catch(NumberFormatException e){
            currField = newAtt;
        }


        if(currField != 0 && currField != newAtt){
            newAtt = currField;
            attributes.setLoboAttack(newAtt);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(loboHpField.getText().toString());
        }catch(NumberFormatException e){
            currField = newHp;
        }
        if(currField != 0 && currField != newHp){
            newHp = currField;
            attributes.setLoboHealth(newHp);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(loboDefField.getText().toString());
        }catch(NumberFormatException e){
            currField = newDef;
        }
        if(currField != 0 && currField != newDef){
            newDef= currField;
            attributes.setLoboDefense(newDef);
        }

        mBeastBrawlDAO.delete(mBeastBrawlDAO.getBeastByBeastName(Beast.lobo));

        Beast newLobo = new Beast(Beast.lobo,newHp,newDef,newAtt);
        mBeastBrawlDAO.insert(newLobo);
    }

    private void checkMouseChange() {
        int newAtt = attributes.mouseAttack;
        int newHp = attributes.mouseHealth;
        int newDef = attributes.mouseDefense;

        int currField = 0;
        try{
            currField = Integer.parseInt(mouseAttField.getText().toString());
        }catch(NumberFormatException e){
            currField = newAtt;
        }


        if(currField != 0 && currField != newAtt){
            newAtt = currField;
            attributes.setMouseAttack(newAtt);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(mouseHpField.getText().toString());
        }catch(NumberFormatException e){
            currField = newHp;
        }
        if(currField != 0 && currField != newHp){
            newHp = currField;
            attributes.setMouseHealth(newHp);
        }

        currField = 0;
        try {
            currField = Integer.parseInt(mouseDefField.getText().toString());
        }catch(NumberFormatException e){
            currField = newDef;
        }
        if(currField != 0 && currField != newDef){
            newDef = currField;
            attributes.setMouseDefense(newDef);
        }

        mBeastBrawlDAO.delete(mBeastBrawlDAO.getBeastByBeastName(Beast.mouse));

        Beast newMouse = new Beast(Beast.mouse,newHp,newDef,newAtt);
        mBeastBrawlDAO.insert(newMouse);
    }

    private void wireUpDisplay() {

        mouseAttField = mAttributesBinding.mouseAttackField;
        mouseDefField = mAttributesBinding.mouseDefField;
        mouseHpField = mAttributesBinding.mouseHpField;

        loboAttField = mAttributesBinding.loboAttackField;
        loboDefField = mAttributesBinding.loboDefField;
        loboHpField = mAttributesBinding.loboHpField;

        snakeAttField = mAttributesBinding.snakeAttackField;
        snakeDefField = mAttributesBinding.snakeDefField;
        snakeHpField = mAttributesBinding.snakeHpField;

        loboAltSwitch = mAttributesBinding.loboAltSwitch;
        mouseAltSwitch = mAttributesBinding.mouseAltSwitch;
        snakeAltSwitch = mAttributesBinding.snakeAltSwitch;

        birdAltSwitch = mAttributesBinding.birdAltSwitch;
        birdAttfield = mAttributesBinding.birdAttackField;
        birdDefField = mAttributesBinding.birdDefField;
        birdHpField = mAttributesBinding.birdHpField;

        attributeButton = mAttributesBinding.changeAttributeButton;
    }

    private void getDatabase(){
        mBeastBrawlDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().BeastBrawlDAO();
    }

    private void addUserToPreference(int userId) {
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }
    private void createBeast() {
        List<Beast> beast = mBeastBrawlDAO.getAllBeast();
        if(beast.size() <= 0){
            Beast lobo = new Beast(Beast.lobo,attributes.loboHealth,attributes.loboDefense,attributes.loboAttack);
            Beast mouse = new Beast(Beast.mouse,attributes.mouseHealth,attributes.mouseDefense,attributes.mouseAttack);
            Beast snake = new Beast(Beast.snake,attributes.snakeHealth,attributes.snakeDefense,attributes.snakeAttack);
            mBeastBrawlDAO.insert(lobo, mouse, snake);
        }
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

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, attributesActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }



}