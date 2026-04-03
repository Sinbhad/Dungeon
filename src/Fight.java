import lib.Node;
import lib.RobertCircularlyLinkedList;

import java.util.Random;
import java.util.Scanner;

public class Fight {


    void battle(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, int levelCount){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("You have encountered " + enemy.getName() + ", hit them with all you got\n");
        System.out.print("Would you like to attack or flee? (1/2): ");
        attackSelect(dungeon, keyboard, character, enemy, levelCount);
    }

    void attackSelect(RobertCircularlyLinkedList<Room> dungeon, Scanner keyboard, Character character, Enemy enemy, int levelCount){
        String choice = "A";
        while ((enemy.getHealth() > 0 && character.getHealth() > 0) && !choice.equalsIgnoreCase("F")) {
            System.out.print("Would you like to attack or flee? (A/F): ");
            choice = keyboard.nextLine();

            if (choice.trim().equalsIgnoreCase("A")) {
                speedCheck(dungeon, character, enemy, keyboard);
            } else if (choice.trim().equalsIgnoreCase("F")) {
                System.out.println("Get out of here!");
                enemy.move();
                character.move(levelCount, keyboard);
            }else{
                System.out.println("Invalid choice!");
                attackSelect(dungeon, keyboard,  character, enemy, levelCount);
            }
        }
    }

    void speedCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, Scanner keyboard){
        double trueAttack = enemy.getAttack() - (character.getTotalDefense() * enemy.getAttack());

        if(character.getSpeed() > enemy.getSpeed()){
            System.out.println("\nYou hit " + enemy.getName() + " dealing " + character.getAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealth() - character.getAttack());

            if(enemy.getHealth() <= 0){
                System.out.println("Success! You have beaten " + enemy.getName());
                System.out.println("For defeating " + enemy.getName() + " you have gained " + enemy.getCoins() + " coins\n");
                character.setCoins(character.getCoins() + enemy.getCoins());
                character.setEnemiesDefeated(character.getEnemiesDefeated() + 1);
                Room currentRoom = (Room)character.getCurrentRoom().getValue();
                if(currentRoom.getItem() != null){
                    character.openChest(keyboard);
                }
                removeRoom(dungeon, character, enemy);
                return;
            }

            System.out.println(enemy.getName() + " hit you back dealing " + enemy.getAttackValue() + " damage\n");
            character.setHealthValue(character.getHealth() - trueAttack);
            System.out.println("You have " + character.getHealth() + " remaining");
            System.out.println(enemy.getName() + "has " + enemy.getHealth() + " remaining");

            if(character.getHealth() <= 0){
                System.out.println("oh no...");
            }

        } else {
            System.out.println("\n" + enemy.getName() + " is faster than you and attacks first\n");
            System.out.println(enemy.getName() + " hit you dealing " + enemy.getAttackValue() + " damage\n");
            character.setHealthValue(character.getHealth() - trueAttack);

            if(character.getHealth() <= 0){
                System.out.println("oh no...");
                return;
            }

            System.out.println("You hit " + enemy.getName() + " dealing " + character.getAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealth() - character.getAttack());
            System.out.println("You have " + character.getHealth() + " remaining");
            System.out.println(enemy.getName() + "has " + enemy.getHealth() + " remaining");


            if(enemy.getHealth() <= 0){
                System.out.println("For defeating " + enemy.getName() + " you have gained " + enemy.getCoins() + " coins\n");
                character.setCoins(character.getCoins() + enemy.getCoins());
                removeRoom(dungeon, character, enemy);
            }
        }
    }

    void removeRoom(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy){
        int random = new Random().nextInt(2);

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
