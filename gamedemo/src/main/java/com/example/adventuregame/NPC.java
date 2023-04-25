package com.example.adventuregame;


import java.util.Random;

public class NPC {
    private Random random = new Random();
    private int npcHealthPoints;
    private int strStat;
    private int dexStat;
    private int intelStat;

    public void setNpcHealthPoints(int npcHealthPoints) {
        this.npcHealthPoints = npcHealthPoints;
    }

    public NPC() {
        this.npcHealthPoints = (int) ((Math.random() * 6) + 1);
        this.strStat = this.npcHealthPoints * 2;
        this.dexStat = this.npcHealthPoints * 2;
        this.intelStat = this.npcHealthPoints * 2;
    }
    public int getNPCHealthPoints() {
        return npcHealthPoints;
    }

    public int getStrStat() {
        return strStat;
    }

    public int getDexStat() {
        return dexStat;
    }

    public int getIntelStat() {
        return intelStat;
    }


}
