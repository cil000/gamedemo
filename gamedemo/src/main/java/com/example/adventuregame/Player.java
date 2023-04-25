package com.example.adventuregame;
import static java.lang.Math.random;

public class Player {

    private boolean isFighting;
    private int healthPoints;
    private int maxHealthPoints;
    private int goldCount;
    private int strStat;
    private int dexStat;
    private int intelStat;

    public Player() {
        this.isFighting = false;
        this.healthPoints = 20;
        this.maxHealthPoints = 20;
        this.goldCount = 0;
        this.strStat = rollStatDice();
        this.dexStat = rollStatDice();
        this.intelStat = rollStatDice();
    }

    private int rollStatDice() {
        int rollCount = 0;
        int stat = 0;
        while (rollCount <= 3){
            int roll = (int) ((Math.random()) * 6) + 1;
            rollCount++;
            stat += roll;

        }
        return stat;
    }

    public boolean isFighting() {
        return isFighting;
    }

    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }

    public int getStrStat() {
        return strStat;
    }

    public void setStrStat(int strStat) {
        this.strStat = strStat;
    }

    public int getDexStat() {
        return dexStat;
    }

    public void setDexStat(int dexStat) {
        this.dexStat = dexStat;
    }

    public int getIntelStat() {
        return intelStat;
    }

    public void setIntelStat(int intStat) {
        this.intelStat = intStat;
    }

    public void foundGold(int goldFound){
        this.goldCount += goldFound;
    }

    public int playerSleep(){
        healthPoints = 20;
        return healthPoints;
    }

    public void takeDamage(int damagePoints){
        healthPoints -= damagePoints;
    }
}
