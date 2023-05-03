package com.example.project2.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2.Beast;
import com.example.project2.User;
import com.example.project2.teamLog;

@Database(entities= {teamLog.class, User.class, Beast.class}, version = 6)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "BeastBrawl.db";
    public static final String TEAMLOG_TABLE = "teamLog_table";
    public static final String BEAST_TABLE = "beast_table";
    public static final String USER_TABLE = "USER_TABLE";
    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();


    public abstract  BeastBrawlDAO BeastBrawlDAO();

    public static AppDataBase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
