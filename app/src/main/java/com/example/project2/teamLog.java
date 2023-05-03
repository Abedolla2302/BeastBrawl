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
    public int monster1Img;

    private String monster2;
    public int monster2Img;

    public teamLog(int userId, String monster1,String monster2) {
        this.userId = userId;
        this.monster1 = monster1;
        this.monster2 = monster2;
        setMonsterImgs();

    }

    private void setMonsterImgs() {
        if(monster1.equals(Beast.lobo)){
            monster1Img = Beast.loboImg;
        }else if(monster1.equals(Beast.mouse)){
            monster1Img = Beast.mouseImg;
        }else if(monster1.equals(Beast.snake)){
            monster1Img = Beast.SnakeImg;
        } else if (monster1.equals(Beast.bird)) {
            monster1Img = Beast.birdImage;
        }

        if(monster2.equals(Beast.lobo)){
            monster2Img = R.drawable.wolf;
        }else if(monster2.equals(Beast.mouse)){
            monster2Img = R.drawable.boxingmouse;
        }else if(monster2.equals(Beast.snake)){
            monster2Img = R.drawable.snake;
        }

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

    public int getMonster1Img() {
        return monster1Img;
    }

    public void setMonster1Img(int monster1Img) {
        this.monster1Img = monster1Img;
    }

    public int getMonster2Img() {
        return monster2Img;
    }

    public void setMonster2Img(int monster2Img) {
        this.monster2Img = monster2Img;
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
