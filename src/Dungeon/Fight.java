package Dungeon;
import Characters.*;
import Characters.Character;
import lib.Node;
import lib.RobertCircularlyLinkedList;
import lib.RobertHolder;

import java.util.Random;
import java.util.Scanner;

public class Fight {
    /**
     * Handles enemy encounters, sets the stage and prompts the user
     * @param dungeon
     * @param character
     * @param enemy
     * @param levelCount
     */
    void battle(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, int levelCount){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("You have encountered " + enemy.getName() + ", hit them with all you got\n");
        attackSelect(dungeon, keyboard, character, enemy, levelCount);
    }

    /**
     * Method for encounter interaction, giving the user a chance to fight or flee
     * @param dungeon
     * @param keyboard
     * @param character
     * @param enemy
     * @param levelCount
     */
    void attackSelect(RobertCircularlyLinkedList<Room> dungeon, Scanner keyboard, Character character, Enemy enemy, int levelCount){
        String choice = "A";
        while ((enemy.getHealth() > 0 && character.getHealth() > 0) && !choice.equalsIgnoreCase("F")) {
            System.out.println("Enter I to display inventory");
            System.out.print("Would you like to attack or flee? (A/F): ");
            choice = keyboard.nextLine();

            if (choice.trim().equalsIgnoreCase("A")) {
                speedCheck(dungeon, character, enemy, keyboard);
                //for now the user has a 100% flee rate with no consequence, a later update may provide a chance meter
            } else if (choice.trim().equalsIgnoreCase("F")) {
                System.out.println("Get out of here!\n");
                enemy.move();
                character.move(levelCount, keyboard);
            }else if(choice.trim().equalsIgnoreCase("I")){
                character.displayInventory(keyboard);
            }else{
                System.out.println("Invalid choice!");
                attackSelect(dungeon, keyboard,  character, enemy, levelCount);
            }
        }
    }

    /**
     * Comparison method to decide whether the enemy or the user attacks first
     * @param dungeon
     * @param character
     * @param enemy
     * @param keyboard
     */
    void speedCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, Scanner keyboard){
        if(character.getSpeed() > enemy.getSpeed()){
            System.out.println("\nYou hit " + enemy.getName() + " dealing " + character.getTotalAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealth() - character.getTotalAttack());

            if(isEnemyAlive(dungeon, character, enemy,  keyboard)){
                enemyAttackOutput(enemy, character);
            }

        } else {
            System.out.println("\n" + enemy.getName() + " is faster than you and attacks first\n");
            enemyAttackOutput(enemy, character);

            if(isPlayerAlive(character)){
                System.out.println("You hit " + enemy.getName() + " dealing " + character.getTotalAttack() + " damage\n");
                enemy.setHealthValue(enemy.getHealth() - character.getTotalAttack());
            }
        }
        System.out.println("You have " + character.getHealth() + " health remaining");
        zeroHealth(enemy);
        System.out.println(enemy.getName() + " has " + enemy.getHealth() + " remaining\n");
    }

    /**
     * Helper method to determine if the enemy is alive and able to attack again
     * @param dungeon
     * @param character
     * @param enemy
     * @param keyboard
     * @return
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
            return false;
        }else{
            return true;
        }
    }

    /**
     * Helper method to determine if the user is still alive to continue the game
     * @param character
     * @return
     */
    Boolean isPlayerAlive(Character character){
        if(character.getHealth() <= 0){
            System.out.println("oh no...");
            return false;
        }else{
            return true;
        }
    }

    /**
     * Helper method, mainly for formatting to prevent negative values from being displayed in outputs
     * @param enemy
     */
    void zeroHealth(Enemy enemy){
        if(enemy.getHealth() < 0){
            enemy.setHealthValue(0);
        }
    }

    /**
     * Randomly selects a move for the enemy for use in battles
     * @param enemy
     * @return
     */
    Move enemyAttackChoice(Enemy enemy){
        RobertHolder currentMoves = enemy.getMoves();
        Random moveIndex = new Random();
        int i = moveIndex.nextInt(currentMoves.size());
        return (Move) currentMoves.getAtIndex(i);
    }

    /**
     * Generates formatted output to display enemy move name and damage dealt, also updates players health
     * @param enemy
     * @param character
     */
    void enemyAttackOutput(Enemy enemy, Character character){
        Move currentMove = enemyAttackChoice(enemy);
        System.out.println(enemy.getName() + " used " + currentMove.getMoveName());
        System.out.println(currentMove.getDescription());
        double damage = damageCalc(currentMove.getDamage(), enemy, character);
        System.out.println(enemy.getName() + " dealt " + damage + " damage\n\n");
        character.setHealthValue(character.getHealth() - damage);
    }

    /**
     * Helper method to determine the enemies true damage value based on the scaling (dungeon level reached)
     * @param damage
     * @param enemy
     * @param character
     * @return
     */
    double damageCalc(int damage, Enemy enemy, Character character){
        double leveledDamage = damage + enemy.getAttackValue();
        return leveledDamage - (leveledDamage * character.getTotalDefense());
    }

    /**
     * Removes the current room from the dungeon if the player has defeated an enemy in that room
     * @param dungeon
     * @param character
     * @param enemy
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
