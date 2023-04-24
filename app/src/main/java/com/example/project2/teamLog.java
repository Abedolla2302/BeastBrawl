package com.example.project2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;

@Entity(tableName = AppDataBase.TEAMLOG_TABLE)
public class teamLog {

    @PrimaryKey(autoGenerate = true)
    private int logId;
    private int userId;
    private String monster1;

    private String monster2;

    public teamLog(int userId, String monster1, String monster2) {
        this.userId = userId;
        this.monster1 = monster1;
        this.monster2 = monster2;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getMonster1() {
        return monster1;
    }

    public void setMonster1(String monster1) {
        this.monster1 = monster1;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getMonster2() {
        return monster2;
    }

    public void setMonster2(String monster2) {
        this.monster2 = monster2;
    }

    @Override
    public String toString() {
        return "teamLog{" +
                "logId=" + logId +
                ", userId='" + userId + '\'' +
                ", monsters=" + monster1 +
                '}';
    }
}
