package dungeon;
import characters.*;
import characters.Character;
import items.Weapon;
import lib.Node;
import lib.CustomCircularlyLinkedList;
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

    public Fight(){
        this.gameUI = new GameUI();
        this.gameState = new GameState();
        this.keyboard = new Scanner(System.in);
        this.playerFunctions = new PlayerFunctions();
        this.inventoryManager = new InventoryManager();
    }

    public Fight(GameState gameState){
        this.gameUI = new GameUI();
        this.gameState = gameState;
        this.keyboard = new Scanner(System.in);
        this.playerFunctions = new PlayerFunctions(gameState);
        this.inventoryManager = new InventoryManager(gameState);
    }

    /**
     * Handles enemy encounters, sets the stage, and prompts the user
     */
    void battle(){
        Scanner keyboard = new Scanner(System.in);
        gameUI.prettyPrintln("[BLD]You have encountered [R]" + gameState.getCurrentRoomEnemy().getName() + "[BRK][BLD], hit them with all you got![BRK]\n");
        startBattleSelection();
    }

    /**
     * Method for encounter interaction, giving the user a chance to fight or flee
     */
    void startBattleSelection(){
        String choice = "A";
        Player player = gameState.getPlayer();
        while ((gameState.isEnemyInRoom() && player.getHealth() > 0) && !choice.equalsIgnoreCase("F")) {
            gameUI.prettyPrintln("Enter [C]I[BRK] to display inventory");
            gameUI.prettyPrint("Would you like to attack or flee? [C](A/F)[BRK]: ");
            choice = keyboard.nextLine();

            if (choice.trim().equalsIgnoreCase("A")) {
                Move move = playerFunctions.chooseMove();
                if (staminaCheck(move)) {
                    speedCheck(move);
                }else if (!staminaCheck(move)){
                    gameUI.prettyPrintln("[R]You do not currently have enough [BLD][Y]stamina[BRK] [R]to use that move,\nplease select another[BRK]");
                }else {
                    gameUI.prettyPrintln("[INVALID]");
                }
            } else if (choice.trim().equalsIgnoreCase("F")) {
                gameUI.prettyPrintln("[R]Get out of here!\n");
                fleeCheck();
                gameState.getCurrentRoomEnemy().move();
            } else if (choice.trim().equalsIgnoreCase("I")) {
                inventoryManager.displayInventory();
            } else {
                gameUI.prettyPrintln("[INVALID]");
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
            gameUI.prettyPrintln("[G]You got away safely![BRK]");
            playerFunctions.move();
        }else{
            gameUI.prettyPrintln("[R]You got away but you got hurt in the process![BRK]");
            enemyAttackChoice();
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
            gameUI.prettyPrintln("\n[BLD][R]" + enemy.getName() + "[BRK] is faster than you and attacks first\n");
            enemyAttackOutput();
            if(isPlayerAlive()){
                gameUI.prettyPrintln("You hit [BLD][R]" + enemy.getName() + "[BRK] dealing " + player.getTotalAttack() + " damage\n");
                enemy.setHealth(enemy.getHealth() - player.getTotalAttack());
            }
        }
        zeroHealth(player);
        gameUI.prettyPrintln("You have [G]" + player.getHealth() + "[BRK] health remaining");
        zeroHealth(enemy);
        gameUI.prettyPrintln("[R]" + enemy.getName() + "[BRK] has [R]" + enemy.getHealth() + "[BRK] health remaining\n");
    }

    /**
     * Formats the damage dealt and presents it to the player
     * @param move the player's selected move
     */
    void attackOutput(Move move){
        Player player = gameState.getPlayer();
        Enemy enemy = gameState.getCurrentRoomEnemy();

        gameUI.prettyPrintln("You used [BLD][C]" + move.getMoveName() + "[BRK][ITL] " + move.getDescription() +
                                "[BRK]dealing [R]" + (player.getTotalAttack() + move.getDamage()) + "[BRK] damage\n");

        enemy.setHealth(enemy.getHealth() - (player.getTotalAttack() + move.getDamage()));
        if(enemy.getHealth() > 0) zeroHealth(enemy);
        gameUI.prettyPrintln("[R]" + enemy.getName() + "[BRK] has [R]" + enemy.getHealth() + "[BRK] health remaining");

    }

    /**
     * Helper method to determine if the enemy is alive and able to attack again
     */
    Boolean isEnemyAlive(){
        Player player = gameState.getPlayer();
        Enemy enemy = gameState.getCurrentRoomEnemy();

        if(enemy.getHealth() <= 0){
            gameUI.prettyPrintln("[BLD][G]Success! [BRK]You have beaten [R]" + enemy.getName() +
                                    "[BRK]\nFor defeating [R]" + enemy.getName() + "[BRK] you have gained [Y]" + enemy.getCoins() + "[BRK] coins\n");

            //Reward player with coins for defeating an enemy
            player.setCoins(player.getCoins() + enemy.getCoins());

            //Increase player enemies defeated counter for score keeping
            player.setEnemiesDefeated(player.getEnemiesDefeated() + 1);

            //Check room for loot before removing it from the dungeon
            Room currentRoom = (Room)player.getCurrentRoom().getValue();
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
            gameUI.prettyPrintln("[R]oh no...[BRK]");
            return false;
        }else{return true;}
    }

    /**
     * Helper method, mainly for formatting to prevent negative values from being displayed in outputs
     */
    void zeroHealth(Character character){
        if(character.getHealth() < 0){character.setHealth(0);}
    }

    /**
     * Randomly selects a move for the enemy for use in battles
     */
    Move enemyAttackChoice(){
        CustomArrayList currentMoves = gameState.getCurrentRoomEnemy().getMoves();
        Random moveIndex = new Random();
        int i = moveIndex.nextInt(currentMoves.size());
        return (Move) currentMoves.getAtIndex(i);
    }

    /**
     * Generates formatted output to display enemy move name and damage dealt, also updates players' health
     */
    void enemyAttackOutput(){
        Player player = gameState.getPlayer();
        Enemy enemy= gameState.getCurrentRoomEnemy();
        Move currentMove = enemyAttackChoice();
        gameUI.prettyPrintln("[R]" + enemy.getName() + "[BRK] used [C]" + currentMove.getMoveName() +
                                "[BRK][ITL] " + currentMove.getDescription());
        double damage = calculateDamageAfterDefense(currentMove.getDamage());
        gameUI.prettyPrintln("[R]" + enemy.getName() + "[BRK] dealt [R]" + damage + "[BRK] damage\n\n");
        player.setHealth(player.getHealth() - damage);
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
        Player player = gameState.getPlayer();

        //Store the original room
        Room enemyRoom = gameState.getCurrentRoom();

        //Move the character randomly to the left or right before removing the room
        if(random == 0){
            player.setCurrentRoom(player.getCurrentRoom().getNextNode());
            gameUI.prettyPrintln("[G]The room you once knew has disappeared!\nYou have been moved to the right.[BRK]\n");
        }else{
            player.setCurrentRoom(player.getCurrentRoom().getLastNode());
            gameUI.prettyPrintln("[G]The room you once knew has disappeared!\nYou have been moved to the left.[BRK]\n");
        }
        gameState.getDungeon().remove(enemyRoom);
    }
}
