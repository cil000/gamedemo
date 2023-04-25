package com.example.adventuregame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.Random;


public class HelloController {

    @FXML
    private Button upButton, rightButton, downButton, leftButton, fightButton, sleepButton, searchButton, runButton;
    @FXML
    private TextArea playerHitPointsTextArea, npcHealthTextArea,
            gameplayTextArea, playerStatsTextArea, npcStatsTextArea, playerPositionTextArea;
    @FXML
    private Text healthLabel, playerStatsLabel, npcHPLabel, npcStatsLabel, playerPositionLabel;

    private Room[][] rooms;
    private NPC npc;
    private Player player;
    private boolean playersTurn;
    private final int runChance = 0;
    public int playerCurrentX;
    public int playerCurrentY;
    private int goldFound;
    private final Random random = new Random();

    public HelloController() {
    }

    @FXML
    public void initialize() {
        //https://stackoverflow.com/questions/51392203/what-does-initialize-mean-in-javafx
        gameplayTextArea.appendText("You wake up in a small dungeon room. You stand up and check your pockets. " +
                "You find a small blade and a note that says 'find gold.'. " +
                "Use directional buttons to navigate the dungeon. If you encounter any foes, use the attack button to fight" +
                " or the run button. Use the search button to find gold in any room. If you need to heal, use the sleep button." +
                " Good luck!\n");
        gameStart();
    }

    public void gameStart() {
        player = new Player();
        rooms = new Room[10][10];

        playerCurrentX = random.nextInt(10);
        playerCurrentY = random.nextInt(10);
        buildRoomArray();
        //spawnNPCInRooms();
        updateStats();
        updatePlayerLocation();

    }

    private Room[][] buildRoomArray() {
        rooms = new Room[10][10];
        for (int rowIndex = 0; rowIndex < rooms.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < rooms.length; columnIndex++) {
                boolean roomHasNPC = false;
                boolean roomVisited = false;
                boolean roomSearched = false;
                boolean roomBlocked = false;
                int goldInRoom = random.nextInt(51);

                rooms[rowIndex][columnIndex] = new Room(roomVisited, roomSearched, roomHasNPC, roomBlocked, goldInRoom);
            }
        }
        return rooms;
    }

    private void spawnNPCInRooms() {
        for (int rowIndex = 0; rowIndex < rooms.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < rooms.length; columnIndex++) {
                if ((random.nextInt(100) > 50)) {
                    rooms[rowIndex][columnIndex].setRoomHasNPC(true);
                }
            }
        }


    }

    public void updatePlayerLocation() {
        playerPositionTextArea.setText("");
        playerPositionTextArea.appendText(Integer.toString(playerCurrentX));
        playerPositionTextArea.appendText(",");
        playerPositionTextArea.appendText(Integer.toString(playerCurrentY));


    }

    public void updateStats() {
        playerHitPointsTextArea.setText(Integer.toString(player.getHealthPoints()));
        playerStatsTextArea.appendText("Strength: " + player.getStrStat() + "\n");
        playerStatsTextArea.appendText("Int: " + player.getIntelStat() + "\n");
        playerStatsTextArea.appendText("Dexterity: " + player.getDexStat());

    }

    private void playerSleep() {
        if (player.isFighting()) {
            gameplayTextArea.appendText("You can't sleep! You are in combat!\n");
        } else if (player.getHealthPoints() == 20) {
            gameplayTextArea.appendText("You are already fully healed!\n");
        } else {
            player.setHealthPoints(20);
            gameplayTextArea.appendText("You slept and healed to 20hp \n");
            playerHitPointsTextArea.setText(Integer.toString(player.getHealthPoints()));
        }
    }




    @FXML
    public void upButtonClicked(ActionEvent actionEvent) {

        if (!player.isFighting()) {
            if ((playerCurrentY - 1 >= 0) && !rooms[playerCurrentX][playerCurrentY - 1].isRoomBlocked()) {
                playerCurrentY -= 1;
                gameplayTextArea.appendText("You moved up one room.\n");
                rooms[playerCurrentX][playerCurrentY].setRoomVisited(true);
                updatePlayerLocation();
                checkForNPC();
            } else {
                gameplayTextArea.appendText("This room is blocked!\n");
            }
        } else {
            gameplayTextArea.appendText("You cannot move during a fight!\n");
        }
    }

    @FXML
    public void rightButtonClicked(ActionEvent actionEvent) {

        if (!player.isFighting()) {
            if ((playerCurrentX + 1 <= 9) && !rooms[playerCurrentX + 1][playerCurrentY].isRoomBlocked()) {
                playerCurrentX += 1;
                gameplayTextArea.appendText("You moved right one room.\n");
                rooms[playerCurrentX][playerCurrentY].setRoomVisited(true);
                updatePlayerLocation();
                checkForNPC();
            } else {
                gameplayTextArea.appendText("This room is blocked!\n");
            }
        } else {
            gameplayTextArea.appendText("You cannot move during a fight!\n");
        }

    }

    @FXML
    public void downButtonClicked(ActionEvent actionEvent) {

        if (!player.isFighting()) {
            if ((playerCurrentY + 1 <= 9) && !rooms[playerCurrentX][playerCurrentY + 1].isRoomBlocked()) {
                playerCurrentY += 1;
                gameplayTextArea.appendText("You moved down one room.\n");
                rooms[playerCurrentX][playerCurrentY].setRoomVisited(true);
                updatePlayerLocation();
                checkForNPC();
            } else {
                gameplayTextArea.appendText("This room is blocked!\n");
            }
        } else {
            gameplayTextArea.appendText("You cannot move during a fight!\n");
        }


    }

    @FXML
    public void leftButtonClicked(ActionEvent actionEvent) {
        if (!player.isFighting()) {
            if ((playerCurrentX - 1 >= 0) && !rooms[playerCurrentX - 1][playerCurrentY].isRoomBlocked()) {
                playerCurrentX -= 1;
                gameplayTextArea.appendText("You moved left one room.\n");
                rooms[playerCurrentX][playerCurrentY].setRoomVisited(true);
                updatePlayerLocation();
                checkForNPC();
            } else {
                gameplayTextArea.appendText("This room is blocked!\n");
            }
        } else {
            gameplayTextArea.appendText("You cannot move during a fight!\n");
        }

    }


    @FXML
    public void fightButtonClicked(ActionEvent actionEvent) {
        fight(npc, player);

    }

    @FXML
    public void sleepButtonClicked(ActionEvent actionEvent) {
        playerSleep();

    }

    @FXML
    public void searchButtonClicked(ActionEvent actionEvent) {
        if (!player.isFighting()) {
            playerSearch(player);
        }
    }

    private void playerSearch(Player player) {
        Room room = rooms[playerCurrentX][playerCurrentY];
        if (room.isRoomSearched()) {
            gameplayTextArea.appendText("You already searched this room and found nothing.\n");
        } else {
            int goldFound = room.getGoldInRoom();
            if ((random.nextInt(20) < player.getIntelStat()) && (goldFound > 0)) {
                gameplayTextArea.appendText("You found " + goldFound + " gold!\n");
                player.setGoldCount(player.getGoldCount() + goldFound);
                gameplayTextArea.appendText("You now have " + player.getGoldCount() + " gold!\n");
                room.setRoomSearched(true);

            } else {
                gameplayTextArea.appendText("There is no gold in this room.\n");
                room.setRoomSearched(true);
            }
        }
    }

    @FXML
    public void runButtonClicked(ActionEvent actionEvent) {
        int runChance = (random.nextInt(20) + 1);
        int npcIntStat = this.npc.getIntelStat();

        if (player.isFighting() && runChance < npcIntStat) {
            int damage = (this.npc.getStrStat() / 3);

                gameplayTextArea.appendText("You try to run but are attacked as you run away!\n");
                player.setHealthPoints((player.getHealthPoints() - damage));
                playerHitPointsTextArea.setText(String.valueOf(player.getHealthPoints()));
                gameplayTextArea.appendText("You took " + damage + " damage in HP!\n");
                gameplayTextArea.appendText("The goblin disappears.\n");
                player.setFighting(false);
                checkPlayerHealth();

        } else if (player.isFighting() && runChance > npcIntStat) {

            player.setFighting(false);
            rooms[playerCurrentX][playerCurrentY].setRoomHasNPC(false);
            gameplayTextArea.appendText("You escape the goblin!\n");
            npcStatsTextArea.clear();
            npcHealthTextArea.clear();

        } else {
            gameplayTextArea.appendText("You don't need to run!\n");
        }
    }
    private void checkForNPC() {
        Room room = rooms[playerCurrentX][playerCurrentY];
        if  (room.roomHasNPC()) {
            this.npc = new NPC();
            player.setFighting(true);
            playersTurn = true;
            gameplayTextArea.appendText("A goblin is attacking you!\n");
            npcStatsTextArea.setText("Strength: " + this.npc.getStrStat() + "\n" +
                    "Int: " + this.npc.getIntelStat() + "\n" + "Dex: " + npc.getDexStat() + "\n");
            npcHealthTextArea.setText(String.valueOf(this.npc.getNPCHealthPoints()));
            gameplayTextArea.appendText("Click attack to fight or run if you want to escape!\n");
        }
    }
    private void fight(NPC npc, Player player) {
        int npcHP = this.npc.getNPCHealthPoints();
        int playerHP = player.getHealthPoints();
        npcStatsTextArea.setText("Strength: " + this.npc.getStrStat() + "\n" +
                "Int: " + this.npc.getIntelStat() + "\n" + "Dex: " + this.npc.getDexStat() + "\n");
        npcHealthTextArea.setText(String.valueOf(this.npc.getNPCHealthPoints()));
        if (playersTurn && player.isFighting()) {
            playerAttacks(npc);
        } else if ((!playersTurn) && (this.npc.getNPCHealthPoints() >= 0) && (player.isFighting())){
            npcAttacks(player, this.npc);
        }
    }

    private void npcAttacks(Player player, NPC npc) {

        int npcAttackRoll = (random.nextInt(20) +1);
        int damage = (this.npc.getStrStat() / 3);

        if (npcAttackRoll >= player.getDexStat()){
            player.setHealthPoints((player.getHealthPoints() - damage));
            playerHitPointsTextArea.setText(String.valueOf(player.getHealthPoints()));
            checkPlayerHealth();
            gameplayTextArea.appendText("The goblin stabbed you!\n");
            gameplayTextArea.appendText("You took " + damage + " damage in HP!\n");

        } else {
            gameplayTextArea.appendText("The goblin missed his attack! Try to attack him!\n");

        }
        playersTurn = true;
        fight(this.npc, player);
    }
    private void playerAttacks(NPC npc) {

        int playerAttackRoll = (random.nextInt(20) + 1);
        int playerDamage = (player.getStrStat() / 3);

        if (playerAttackRoll >= this.npc.getDexStat()) {

            this.npc.setNpcHealthPoints((this.npc.getNPCHealthPoints() - playerDamage));

            if (this.npc.getNPCHealthPoints() <=0) {
                this.npc.setNpcHealthPoints(0);
                gameplayTextArea.appendText("You defeated the goblin after hitting for " + playerDamage
                + " damage!\n");
                player.setFighting(false);
                playersTurn = true;
                clearNPCFields();
                postFightPrompt();
            }
            else if (this.npc.getNPCHealthPoints() > 0) {
                gameplayTextArea.appendText("You attacked the goblin! It now has " +
                        (this.npc.getNPCHealthPoints()) + " health!\n");
                playersTurn = false;
                fight(this.npc, player);
            }
        } else {
            gameplayTextArea.appendText("You try to attack but miss the goblin!\n");
            playersTurn = false;
            fight(this.npc,player);
        }
    }
    private void postFightPrompt() {
        //https://coderanch.com/t/688570/java/Clearing-content-selected-textarea
        gameplayTextArea.appendText("Use directional buttons to navigate the dungeon.\n " +
                "If you encounter any foes, use the attack button to fight\n" +
                " or run button. Use the search button to find gold in any room.\n" +
                " If you need to heal, use the sleep button.\n");
    }

    private void checkPlayerHealth() {
        if (player.getHealthPoints() <= 0) {
            //https://stackoverflow.com/questions/12153622/how-to-close-a-javafx-application-on-window-close

            gameplayTextArea.setText("YOU DIED");

            Platform.exit();
        }


    }
    private void clearNPCFields() {
        npcStatsTextArea.clear();
        npcHealthTextArea.clear();

    }
}
