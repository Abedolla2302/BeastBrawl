package com.example.project2;

public class attack {
    public static int attackTarget(Beast attacker, Beast opponent,boolean gaurd){
        int totalAttack = attacker.getAttack() - opponent.getDefense();

        if(totalAttack <= 0 && gaurd){
            totalAttack = 1;
        }

        if(totalAttack <= 0 || gaurd){
            totalAttack = 1;
        }

        return totalAttack;
    }

}
