package com.example.project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.Beast;
import com.example.project2.User;
import com.example.project2.teamLog;

import java.util.List;

@Dao
public interface BeastBrawlDAO {

    @Insert
    void insert(teamLog... teamLogs);

    @Update
    void update(teamLog... teamLogs);

    @Delete
    void delete(teamLog teamLog);

    @Query("SELECT * FROM "+ AppDataBase.TEAMLOG_TABLE + " WHERE userId = :userId")
    teamLog getTeamLogByUserId(int userId);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM "+ AppDataBase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM "+ AppDataBase.USER_TABLE + " WHERE userName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM "+ AppDataBase.USER_TABLE + " WHERE userId = :userID")
    User getUserByUserId(int userID);

    @Insert
    void insert(Beast... beasts);

    @Update
    void update(Beast... beasts);

    @Delete
    void delete(Beast beasts);

    @Query("SELECT * FROM "+ AppDataBase.BEAST_TABLE + " WHERE beastName = :BeastName")
    Beast getBeastByBeastName(String BeastName);

    @Query("SELECT * FROM "+ AppDataBase.BEAST_TABLE)
    List<Beast> getAllBeast();


}
