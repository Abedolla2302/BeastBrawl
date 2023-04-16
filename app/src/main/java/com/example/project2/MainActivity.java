package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    EditText mUserNameEdittext;
    EditText mPassWordEditText;
    Button mLoginButton;
    Button mSignUpButton;

     ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mMainBinding.getRoot();

        setContentView(view);

        mUserNameEdittext = mMainBinding.userNameEditText;
        mPassWordEditText= mMainBinding.passWordEditText;
        mLoginButton = mMainBinding.loginButton;
        mSignUpButton = mMainBinding.signUpButton;

        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Success!!",Toast.LENGTH_SHORT).show();
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Sign Up Success!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}