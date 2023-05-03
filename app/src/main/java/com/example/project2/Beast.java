package com.example.project2;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;

@Entity(tableName = AppDataBase.BEAST_TABLE)
public class Beast {
    @PrimaryKey(autoGenerate = true)
    private int beastId;

    static final String lobo = "lobo";

    static int loboImg = R.drawable.wolf;
    static final String mouse = "mouse";
    static int mouseImg = R.drawable.boxingmouse;

    static final String snake = "snake";
    static int SnakeImg = R.drawable.snake;

    static final String bird = "bird";
    static int birdImage = R.drawable.bird;

    private String beastName;

    private int health;

    private int defense;

    private int attack;

    public Beast(String beastName, int health, int defense, int attack) {
        this.beastName = beastName;
        this.health = health;
        this.defense = defense;
        this.attack = attack;
    }

    public Beast(Beast b){
        this.beastName = b.getBeastName();
        this.health = b.getHealth();
        this.defense = b.getDefense();
        this.attack = b.getAttack();
    }

    public static int getBirdImage() {
        return birdImage;
    }

    public static void setBirdImage(int birdImage) {
        Beast.birdImage = birdImage;
    }

    public static int getLoboImg() {
        return loboImg;
    }

    public static void setLoboImg(int loboImg) {
        Beast.loboImg = loboImg;
    }

    public static int getMouseImg() {
        return mouseImg;
    }

    public static void setMouseImg(int mouseImg) {
        Beast.mouseImg = mouseImg;
    }

    public static int getSnakeImg() {
        return SnakeImg;
    }

    public static void setSnakeImg(int snakeImg) {
        SnakeImg = snakeImg;
    }

    public int getBeastId() {
        return beastId;
    }

    public void setBeastId(int beastId) {
        this.beastId = beastId;
    }

    public String getBeastName() {
        return beastName;
    }

    public void setBeastName(String beastName) {
        this.beastName = beastName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
