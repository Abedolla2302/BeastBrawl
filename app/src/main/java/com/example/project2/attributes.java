package com.example.project2;

public class attributes {
    static int loboHealth = 10;
    static int loboAttack = 13;
    static int loboDefense = 8;

    static int mouseHealth = 12;
    static int mouseAttack = 10;
    static int mouseDefense = 8;

    static int snakeHealth = 9;
    static int snakeAttack = 10;
    static int snakeDefense = 10;

    static int birdHealth = 14;
    static int birdAttack = 12;
    static int birdDefense = 7;

    public static int getBirdHealth() {
        return birdHealth;
    }

    public static void setBirdHealth(int birdHealth) {
        attributes.birdHealth = birdHealth;
    }

    public static int getBirdAttack() {
        return birdAttack;
    }

    public static void setBirdAttack(int birdAttack) {
        attributes.birdAttack = birdAttack;
    }

    public static int getBirdDefense() {
        return birdDefense;
    }

    public static void setBirdDefense(int birdDefense) {
        attributes.birdDefense = birdDefense;
    }

    public static int getSnakeHealth() {
        return snakeHealth;
    }

    public static void setSnakeHealth(int snakeHealth) {
        attributes.snakeHealth = snakeHealth;
    }

    public static int getSnakeAttack() {
        return snakeAttack;
    }

    public static void setSnakeAttack(int snakeAttack) {
        attributes.snakeAttack = snakeAttack;
    }

    public static int getSnakeDefense() {
        return snakeDefense;
    }

    public static void setSnakeDefense(int snakeDefense) {
        attributes.snakeDefense = snakeDefense;
    }

    public int getLoboHealth() {
        return loboHealth;
    }

    public static void setLoboHealth(int loboHealth) {
        attributes.loboHealth = loboHealth;
    }

    public int getLoboAttack() {
        return loboAttack;
    }

    public static void setLoboAttack(int loboAttack) {
        attributes.loboAttack = loboAttack;
    }

    public int getLoboDefense() {
        return loboDefense;
    }

    public static void setLoboDefense(int loboDefense) {
        attributes.loboDefense = loboDefense;
    }

    public int getMouseHealth() {
        return mouseHealth;
    }

    public static void setMouseHealth(int mouseHealth) {
        attributes.mouseHealth = mouseHealth;
    }

    public int getMouseAttack() {
        return mouseAttack;
    }

    public static void setMouseAttack(int mouseAttack) {
        attributes.mouseAttack = mouseAttack;
    }

    public int getMouseDefense() {
        return mouseDefense;
    }

    public static void setMouseDefense(int mouseDefense) {
        attributes.mouseDefense = mouseDefense;
    }

    public static int getBeastHealth(String beast){
        if(beast.equals(Beast.lobo)){
            return loboHealth;
        }else if(beast.equals(Beast.mouse)){
            return mouseHealth;
        }else if(beast.equals(Beast.snake)){
            return snakeHealth;
        } else if (beast.equals(Beast.bird)) {
            return birdHealth;

        }
        return 0;
    }

    public static int getBeastAttack(String beast){
        if(beast.equals(Beast.lobo)){
            return loboAttack;
        }else if(beast.equals(Beast.mouse)){
            return mouseAttack;
        }else if(beast.equals(Beast.snake)){
            return snakeAttack;
        }
        return 0;
    }

    public static int getBeastDefense(String beast){
        if(beast.equals(Beast.lobo)){
            return loboDefense;
        }else if(beast.equals(Beast.mouse)){
            return mouseDefense;
        }else if(beast.equals(Beast.snake)){
            return snakeDefense;
        }
        return 0;
    }

}
