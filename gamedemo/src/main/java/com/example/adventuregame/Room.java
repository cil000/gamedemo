package com.example.adventuregame;

import java.util.Random;

import static java.util.Random.*;

public class Room {
    Random random = new Random();
    private boolean roomVisited;
    private boolean roomSearched;
    private boolean roomHasNPC;
    private boolean roomBlocked;
    private int goldInRoom;

    public Room(boolean roomVisited, boolean roomSearched, boolean roomHasNPC, boolean roomBlocked, int goldInRoom) {

        this.roomVisited = roomVisited;
        this.roomSearched = roomSearched;
        this.roomBlocked = (random.nextInt(100)> 80);
        this.roomHasNPC = (random.nextInt(100)> 50);
        this.goldInRoom = goldInRoom;


    }
    public boolean isRoomVisited() {
        return roomVisited;
    }

    public void setRoomVisited(boolean roomVisited) {
        this.roomVisited = roomVisited;
    }

    public boolean isRoomSearched() {
        return roomSearched;
    }

    public void setRoomSearched(boolean roomSearched) {
        this.roomSearched = roomSearched;
    }
    public boolean isRoomBlocked() {
        return roomBlocked;
    }

    public void setRoomBlocked(boolean roomBlocked) {
        this.roomBlocked = roomBlocked;
    }

    public boolean roomHasNPC() {
        return roomHasNPC;
    }

    public void setRoomHasNPC(boolean roomHasNPC) {
        this.roomHasNPC = roomHasNPC;
    }

    public int getGoldInRoom() {
        return goldInRoom;
    }

    public void setGoldInRoom(int goldInRoom) {
        this.goldInRoom = goldInRoom;
    }


}