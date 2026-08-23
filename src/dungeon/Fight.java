package dungeon;
import characters.*;
import characters.Character;
import items.Weapon;
import lib.Node;
import lib.RobertCircularlyLinkedList;
import lib.RobertHolder;

import java.util.Random;
import java.util.Scanner;

public class Fight {
    /**
     * Handles enemy encounters, sets the stage, and prompts the user
     * @param dungeon dungeon being used
     * @param character player character
     * @param enemy current enemy
     * @param levelCount current level count
     */
    void battle(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, int levelCount){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("You have encountered " + enemy.getName() + ", hit them with all you got\n");
        startBattleSelection(dungeon, keyboard, character, enemy, levelCount);
    }

    /**
     * Method for encounter interaction, giving the user a chance to fight or flee
     * @param dungeon dungeon being used
     * @param keyboard user input
     * @param character player character
     * @param enemy current enemy
     * @param levelCount current level count
     */
    void startBattleSelection(RobertCircularlyLinkedList<Room> dungeon, Scanner keyboard, Character character, Enemy enemy, int levelCount){
        String choice = "A";
        while ((enemy.getHealth() > 0 && character.getHealth() > 0) && !choice.equalsIgnoreCase("F")) {
            System.out.println("Enter I to display inventory");
            System.out.print("Would you like to attack or flee? (A/F): ");
            choice = keyboard.nextLine();

            if (choice.trim().equalsIgnoreCase("A")) {
                Move move = character.chooseMove((Weapon) character.getWeapon(), keyboard);
                if (staminaCheck(character, move)) {
                    speedCheck(dungeon, character, enemy, keyboard, move);
                }else if (!staminaCheck(character, move)){
                    System.out.println("You do not currently have enough stamina to use that move,\nplease select another");
                }else {
                    System.out.println("No move selected. Try again.");
                }
            } else if (choice.trim().equalsIgnoreCase("F")) {
                System.out.println("Get out of here!\n");
                fleeCheck(character, enemy, keyboard, levelCount);
                enemy.move();
            } else if (choice.trim().equalsIgnoreCase("I")) {
                character.displayInventory(keyboard);
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private boolean staminaCheck(Character character, Move move){
        return move != null && move.getStaminaCost() <= character.getStamina();
    }

    /**
     * Helper method to check if the flee number is in the fleeNums array
     * @param num random number generated, passed in from the fleeCheck method
     * @param fleeNums array of "flee" numbers, extracted from the enemy's fleeNums array
     * @return boolean value indicating if the "flee" number was found in the fleeNums array
     */
    boolean checkFleeNums(int num, int[] fleeNums){
        for (int fleeNum : fleeNums) {
            if (num == fleeNum) {return true;}
        }

        return false;
    }

    /**
     * Used to determine if the user was able to flee battle safely or if they get hurt trying
     * @param character player character
     * @param enemy current enemy
     * @param keyboard user input
     * @param levelCount current level count
     */
    void fleeCheck(Character character, Enemy enemy, Scanner keyboard, int levelCount){
        int random = new Random().nextInt(9);
        int[] enemyFleeNums = enemy.getFleeNum();
        if(checkFleeNums(random, enemyFleeNums)){
            System.out.println("You got away safely!");
            character.move(levelCount, keyboard);
        }else{
            System.out.println("You got away but you got hurt in the process!");
            enemyAttackChoice(enemy);
            enemyAttackOutput(enemy, character);
        }
        character.setStamina(character.getMaxStamina());
    }

    /**
     * Comparison method to decide whether the enemy or the user attacks first
     * @param dungeon dungeon being used
     * @param character player character
     * @param enemy current enemy
     * @param keyboard user input
     */
    void speedCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, Scanner keyboard, Move move){
        if(character.getSpeed() > enemy.getSpeed()){
            attackOutput(character, enemy, move);
            if(isEnemyAlive(dungeon, character, enemy,  keyboard)){
                enemyAttackOutput(enemy, character);
            }

        } else {
            System.out.println("\n" + enemy.getName() + " is faster than you and attacks first\n");
            enemyAttackOutput(enemy, character);
            if(isPlayerAlive(character)){
                System.out.println("You hit " + enemy.getName() + " dealing " + character.getTotalAttack() + " damage\n");
                enemy.setHealth(enemy.getHealth() - character.getTotalAttack());
            }
        }
        zeroHealth(character);
        System.out.println("You have " + character.getHealth() + " health remaining");
        zeroHealth(enemy);
        System.out.println(enemy.getName() + " has " + enemy.getHealth() + " remaining\n");
    }

    void attackOutput(Character character, Enemy enemy, Move move){
        System.out.println("You used " + move.getMoveName());
        System.out.println(move.getDescription() + "dealing " + (character.getTotalAttack() + move.getDamage()) + " damage\n");
        enemy.setHealth(enemy.getHealth() - (character.getTotalAttack() + move.getDamage()));
        if(enemy.getHealth() > 0) zeroHealth(enemy);
        System.out.println(enemy.getName() + " has " + enemy.getHealth() + " health remaining");

    }

    /**
     * Helper method to determine if the enemy is alive and able to attack again
     * @param dungeon dungeon being used
     * @param character player character
     * @param enemy current enemy
     * @param keyboard user input
     * @return boolean used to determine certain actions in the game based on enemy health
     */
    Boolean isEnemyAlive(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, Scanner keyboard){
        if(enemy.getHealth() <= 0){
            System.out.println("Success! You have beaten " + enemy.getName());
            System.out.println("For defeating " + enemy.getName() + " you have gained " + enemy.getCoins() + " coins\n");

            //Reward player with coins for defeating an enemy
            character.setCoins(character.getCoins() + enemy.getCoins());

            //Increase player enemies defeated counter for score keeping
            character.setEnemiesDefeated(character.getEnemiesDefeated() + 1);

            //Check room for loot before removing it from the dungeon
            Room currentRoom = (Room)character.getCurrentRoom().getValue();
            if(currentRoom.getItem() != null){
                character.openChest(keyboard);
            }
            removeRoom(dungeon, character, enemy);
            character.setStamina(character.getMaxStamina());
            return false;
        }else{
            return true;
        }
    }

    /**
     * Helper method to determine if the user is still alive to continue the game
     * @param character player character
     * @return boolean used to determine certain actions in the game based on player health
     */
    Boolean isPlayerAlive(Character character){
        if(character.getHealth() <= 0){
            System.out.println("oh no...");
            return false;
        }else{return true;}
    }

    /**
     * Helper method, mainly for formatting to prevent negative values from being displayed in outputs
     * @param character current character
     */
    void zeroHealth(Character character){
        if(character.getHealth() < 0){character.setHealth(0);}
    }

    /**
     * Randomly selects a move for the enemy for use in battles
     * @param enemy current enemy
     * @return Move object to be used in with enemyAttackOutput
     */
    Move enemyAttackChoice(Enemy enemy){
        RobertHolder currentMoves = enemy.getMoves();
        Random moveIndex = new Random();
        int i = moveIndex.nextInt(currentMoves.size());
        return (Move) currentMoves.getAtIndex(i);
    }

    /**
     * Generates formatted output to display enemy move name and damage dealt, also updates players' health
     * @param enemy current enemy
     * @param character player character
     */
    void enemyAttackOutput(Enemy enemy, Character character){
        Move currentMove = enemyAttackChoice(enemy);
        System.out.println(enemy.getName() + " used " + currentMove.getMoveName());
        System.out.println(currentMove.getDescription());
        double damage = calculateDamageAfterDefense(currentMove.getDamage(), enemy, character);
        System.out.println(enemy.getName() + " dealt " + damage + " damage\n\n");
        character.setHealth(character.getHealth() - damage);
    }

    /**
     * Helper method to determine the enemies true damage value based on the scaling (dungeon level reached)
     * @param damage damage value before scaling
     * @param enemy current enemy
     * @param character player character
     * @return double value of damage after defense
     */
    double calculateDamageAfterDefense(int damage, Enemy enemy, Character character){
        double leveledDamage = damage + enemy.getAttackValue();
        return leveledDamage - (leveledDamage * character.getTotalDefense());
    }

    /**
     * Removes the current room from the dungeon if the player has defeated an enemy in that room
     * @param dungeon dungeon being used
     * @param character player character
     * @param enemy current enemy
     */
    void removeRoom(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy){
        int random = new Random().nextInt(2);

        //Move the character randomly to the left or right before removing the room
        if(random == 0){
            character.setCurrentRoom(character.getCurrentRoom().getNextNode());
            System.out.println("The room you once knew has disappeared!\nYou have been moved to the right.\n");
        }else{
            character.setCurrentRoom(character.getCurrentRoom().getLastNode());
            System.out.println("The room you once knew has disappeared!\nYou have been moved to the left.\n");
        }
        Node enemyRoomNode = enemy.getCurrentRoom();
        dungeon.remove((Room)enemyRoomNode.getValue());
    }
}
