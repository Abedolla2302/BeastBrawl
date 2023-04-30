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

    public void setLoboHealth(int loboHealth) {
        this.loboHealth = loboHealth;
    }

    public int getLoboAttack() {
        return loboAttack;
    }

    public void setLoboAttack(int loboAttack) {
        this.loboAttack = loboAttack;
    }

    public int getLoboDefense() {
        return loboDefense;
    }

    public void setLoboDefense(int loboDefense) {
        this.loboDefense = loboDefense;
    }

    public int getMouseHealth() {
        return mouseHealth;
    }

    public void setMouseHealth(int mouseHealth) {
        this.mouseHealth = mouseHealth;
    }

    public int getMouseAttack() {
        return mouseAttack;
    }

    public void setMouseAttack(int mouseAttack) {
        this.mouseAttack = mouseAttack;
    }

    public int getMouseDefense() {
        return mouseDefense;
    }

    public void setMouseDefense(int mouseDefense) {
        this.mouseDefense = mouseDefense;
    }

    public static int getBeastHealth(String beast){
        if(beast.equals(Beast.lobo)){
            return loboHealth;
        }else if(beast.equals(Beast.mouse)){
            return mouseHealth;
        }else if(beast.equals(Beast.snake)){
            return snakeHealth;
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
