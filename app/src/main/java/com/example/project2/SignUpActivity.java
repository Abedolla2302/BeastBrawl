package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "om.example.project2.userIdKey";

    private Button signUpButton;

    private SharedPreferences mPreferences = null;
    private EditText usernameField;
    private EditText passwordField1;
    private EditText passwordField2;

    private int UserId = -1;
    private BeastBrawlDAO mBeastBrawlDAO;

    private ActivitySignUpBinding mSignUpBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = mSignUpBinding.getRoot();
        getDatabase();

        setContentView(view);
        signUpButton = findViewById(R.id.signUpButton);
        usernameField = findViewById(R.id.userNameEditText);
        passwordField1 = findViewById(R.id.passWordEditTextSignUp);
        passwordField2 = findViewById(R.id.passWordEditAgainText);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameField.getText().toString() != null && passwordField1.getText().toString() != null){
                    String userName = usernameField.getText().toString();
                    if(mBeastBrawlDAO.getUserByUsername(userName) != null){
                        Toast.makeText(SignUpActivity.this, "User exist already!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(passwordField1.getText().toString().equals(passwordField2.getText().toString())){

                        String password = passwordField1.getText().toString();
                        User newUser = new User(userName,password,0);
                        mBeastBrawlDAO.insert(newUser);
                        addUserToPreference(newUser.getUserId());

                        Toast.makeText(SignUpActivity.this, "SUCCESS!!", Toast.LENGTH_SHORT).show();
                        Intent intent = loginActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }


    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;

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

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);
    }
}