package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.BeastBrawlDAO;
import com.example.project2.databinding.ActivityAttributesBinding;

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);
        mAttributesBinding = ActivityAttributesBinding.inflate(getLayoutInflater());
        View view = mAttributesBinding.getRoot();
        getDatabase();

        attributeButton = mAttributesBinding.changeAttributeButton;

    }

    private void getDatabase(){
        mBeastBrawlDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().BeastBrawlDAO();
    }


}