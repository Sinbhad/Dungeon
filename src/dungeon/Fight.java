package dungeon;
import characters.*;
import lib.CustomArrayList;
import ui.GameUI;

import java.util.Random;
import java.util.Scanner;

public class Fight {
    private final GameUI gameUI;
    private final GameState gameState;
    private final Scanner keyboard;
    private final PlayerFunctions playerFunctions;
    private final InventoryManager inventoryManager;

    private double enemyStartHealth;

    public Fight(){
        this.gameUI = new GameUI();
        this.gameState = new GameState();
        this.keyboard = new Scanner(System.in);
        this.playerFunctions = new PlayerFunctions();
        this.inventoryManager = new InventoryManager();
        this.enemyStartHealth = 0;
    }

    public Fight(GameState gameState){
        this.gameUI = new GameUI(gameState);
        this.gameState = gameState;
        this.keyboard = new Scanner(System.in);
        this.playerFunctions = new PlayerFunctions(gameState);
        this.inventoryManager = new InventoryManager(gameState);
    }

    /**
     * Handles enemy encounters, sets the stage, and prompts the user
     */
    void battle(){
        this.enemyStartHealth = gameState.getCurrentRoomEnemy().getHealth();
        gameUI.logMessage("[BLD]You have encountered [R]" + gameState.getCurrentRoomEnemy().getName() + "[BRK][BLD], hit them with all you got![BRK]");
        startBattleSelection();
    }

    /**
     * Method for encounter interaction, giving the user a chance to fight or flee
     */
    void startBattleSelection(){
        String choice = "A";
        Player player = gameState.getPlayer();
        while ((gameState.isEnemyInRoom() && player.getHealth() > 0) && !choice.equalsIgnoreCase("F")) {
            gameUI.renderScreen();
            gameUI.prettyPrintln("Enter [C]I[BRK] to display inventory");
            gameUI.prettyPrint("Would you like to attack or flee? [C](A/F)[BRK]: ");
            choice = keyboard.nextLine().trim();

            if (choice.equalsIgnoreCase("A")) {
                Move move = playerFunctions.chooseMove();
                if (staminaCheck(move)) {
                    speedCheck(move);
                } else {
                    gameUI.logMessage("[R]You do not currently have enough [BLD][Y]stamina[BRK] [R]to use that move, please select another[BRK]");
                }
            } else if (choice.equalsIgnoreCase("F")) {
                gameUI.logMessage("[R]Get out of here![BRK]");
                Enemy currentEnemy = gameState.getCurrentRoomEnemy();
                fleeCheck();
                currentEnemy.move();
            } else if (choice.equalsIgnoreCase("I")) {
                inventoryManager.displayInventory();
            } else {
                gameUI.logMessage("[INVALID]");
            }
        }
    }

    private boolean staminaCheck(Move move){
        return move != null && move.getStaminaCost() <= gameState.getPlayer().getStamina();
    }

    /**
     * Helper method to check if the flee number is in the fleeNums array
     * @param num random number generated, passed in from the fleeCheck method
     * @param fleeNums array of "flee" numbers, extracted from the enemy's fleeNums array
     */
    boolean checkFleeNums(int num, int[] fleeNums){
        for (int fleeNum : fleeNums) {
            if (num == fleeNum) {return true;}
        }

        return false;
    }

    /**
     * Used to determine if the user was able to flee battle safely or if they get hurt trying
     */
    void fleeCheck(){
        int random = new Random().nextInt(9);
        int[] enemyFleeNums = gameState.getCurrentRoomEnemy().getFleeNum();
        if(checkFleeNums(random, enemyFleeNums)){
            gameUI.logMessage("[G]You got away safely![BRK]");
        }else{
            gameUI.logMessage("[R]You got away but you got hurt in the process![BRK]");
            enemyAttackOutput();
        }
        gameState.getPlayer().setStamina(gameState.getPlayer().getMaxStamina());
    }

    /**
     * Comparison method to decide whether the enemy or the user attacks first
     */
    void speedCheck(Move move){
        Player player = gameState.getPlayer();
        Enemy enemy = gameState.getCurrentRoomEnemy();

        if(player.getSpeed() > enemy.getSpeed()){
            attackOutput(move);
            if(isEnemyAlive()){
                enemyAttackOutput();
            }
        } else {
            gameUI.logMessage("[BLD][R]" + enemy.getName() + "[BRK] is faster than you and attacks first");
            enemyAttackOutput();
            if(isPlayerAlive()){
                attackOutput(move);
                isEnemyAlive();
            }
        }
    }

    /**
     * Formats the damage dealt and presents it to the player
     * @param move the player's selected move
     */
    void attackOutput(Move move){
        Player player = gameState.getPlayer();
        Enemy enemy = gameState.getCurrentRoomEnemy();

        gameUI.logMessage("You used [BLD][C]" + move.getMoveName() + "[BRK][ITL] " + move.getDescription() +
                                "[BRK]dealing [R]" + (player.getTotalAttack() + move.getDamage()) + "[BRK] damage");

        player.setStamina(player.getStamina() - move.getStaminaCost());
        enemy.decreaseHealth((player.getTotalAttack() + move.getDamage()));
    }

    /**
     * Helper method to determine if the enemy is alive and able to attack again
     */
    Boolean isEnemyAlive(){
        Player player = gameState.getPlayer();
        Enemy enemy = gameState.getCurrentRoomEnemy();

        if(enemy.getHealth() <= 0){
            gameUI.logMessage("[BLD][G]Success! [BRK]You have beaten [R]" + enemy.getName() +
                                    "[BRK] and gained [Y]" + enemy.getCoins() + "[BRK] coins");

            //Reward player with coins for defeating an enemy
            player.setCoins(player.getCoins() + enemy.getCoins());

            //Increase player enemies defeated counter for score keeping
            player.setEnemiesDefeated(player.getEnemiesDefeated() + 1);

            //Remove enemy from room
            gameState.getEnemyRoster().remove(enemy);
            gameState.removeEnemyFromCurrentRoom();

            //Check room for loot before removing it from the dungeon
            Room currentRoom = player.getCurrentRoom().getValue();
            if(currentRoom.getItem() != null){
                playerFunctions.openChest();
            }
            removeRoom();
            player.setStamina(player.getMaxStamina());
            return false;
        }else{
            return true;
        }
    }

    /**
     * Helper method to determine if the user is still alive to continue the game
     */
    Boolean isPlayerAlive(){
        if(gameState.getPlayer().getHealth() <= 0){
            gameUI.logMessage("[R]oh no...[BRK]");
            return false;
        }else{return true;}
    }

    /**
     * Selects a move for the enemy in battle.
     * Picks the highest-damage move if health is below 50%; otherwise picks randomly.
     */
    Move enemyAttackChoice() {
        Enemy enemy = gameState.getCurrentRoomEnemy();
        CustomArrayList currentMoves = enemy.getMoves();

        // If below 50% health, find and return the highest-damage move
        if (enemy.getHealth() < enemyStartHealth / 2) {
            Move selectedMove = (Move) currentMoves.getAtIndex(0);
            int maxDamage = selectedMove.getDamage();

            for (int j = 1; j < currentMoves.size(); j++) {
                Move move = (Move) currentMoves.getAtIndex(j);
                if (move.getDamage() > maxDamage) {
                    maxDamage = move.getDamage();
                    selectedMove = move;
                }
            }
            return selectedMove;
        }

        // Otherwise, return a random move
        Random moveIndex = new Random();
        int i = moveIndex.nextInt(currentMoves.size());
        return (Move) currentMoves.getAtIndex(i);
    }

    /**
     * Generates formatted output to display enemy move name and damage dealt, also updates players' health
     */
    void enemyAttackOutput(){
        Player player = gameState.getPlayer();
        Enemy enemy = gameState.getCurrentRoomEnemy();
        Move currentMove = enemyAttackChoice();
        gameUI.logMessage("[R]" + enemy.getName() + "[BRK] used [C]" + currentMove.getMoveName() +
                                "[BRK][ITL] " + currentMove.getDescription().trim());
        double damage = calculateDamageAfterDefense(currentMove.getDamage());
        gameUI.logMessage("[R]" + enemy.getName() + "[BRK] dealt [R]" + damage + "[BRK] damage");
        player.decreaseHealth(damage);
    }

    /**
     * Helper method to determine the enemies true damage value based on the scaling (dungeon level reached)
     * @param damage damage value before scaling
     */
    double calculateDamageAfterDefense(int damage){
        double leveledDamage = damage + gameState.getCurrentRoomEnemy().getAttackValue();
        return leveledDamage - (leveledDamage * gameState.getPlayer().getTotalDefense());
    }

    /**
     * Removes the current room from the dungeon if the player has defeated an enemy in that room
     */
    void removeRoom(){
        int random = new Random().nextInt(2);

        //Store the original room
        Room enemyRoom = gameState.getCurrentRoom();

        //Move the character randomly to the left or right before removing the room
        if(random == 0){
            playerFunctions.moveRight();
            gameUI.logMessage("[G]The room you once knew has disappeared! You have been moved to the right.[BRK]");
        }else{
            playerFunctions.moveLeft();
            gameUI.logMessage("[G]The room you once knew has disappeared! You have been moved to the left.[BRK]");
        }
        gameState.getDungeon().remove(enemyRoom);
    }
}
