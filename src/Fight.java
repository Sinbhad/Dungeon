import lib.Node;
import lib.RobertCircularlyLinkedList;
import lib.RobertHolder;

import java.util.Random;
import java.util.Scanner;

public class Fight {


    void battle(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, int levelCount){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("You have encountered " + enemy.getName() + ", hit them with all you got\n");
        attackSelect(dungeon, keyboard, character, enemy, levelCount);
    }

    void attackSelect(RobertCircularlyLinkedList<Room> dungeon, Scanner keyboard, Character character, Enemy enemy, int levelCount){
        String choice = "A";
        while ((enemy.getHealth() > 0 && character.getHealth() > 0) && !choice.equalsIgnoreCase("F")) {
            System.out.println("Enter I to display inventory");
            System.out.print("Would you like to attack or flee? (A/F): ");
            choice = keyboard.nextLine();

            if (choice.trim().equalsIgnoreCase("A")) {
                speedCheck(dungeon, character, enemy, keyboard);
            } else if (choice.trim().equalsIgnoreCase("F")) {
                System.out.println("Get out of here!");
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

    void speedCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, Scanner keyboard){
        if(character.getSpeed() > enemy.getSpeed()){
            System.out.println("\nYou hit " + enemy.getName() + " dealing " + character.getTotalAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealth() - character.getTotalAttack());

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

            //System.out.println(enemy.getName() + " hit you back\n");
            //character.setHealthValue(character.getHealth() - trueEnemyAttack);
            enemyAttackOutput(enemy, character);


            if(character.getHealth() <= 0){
                System.out.println("oh no...");
                return;
            }
            System.out.println("You have " + character.getHealth() + " health remaining");
            System.out.println(enemy.getName() + " has " + enemy.getHealth() + " remaining\n");

        } else {
            System.out.println("\n" + enemy.getName() + " is faster than you and attacks first\n");
            //System.out.println(enemy.getName() + " hit you dealing " + trueEnemyAttack + " damage\n");
            //character.setHealthValue(character.getHealth() - trueEnemyAttack);
            enemyAttackOutput(enemy, character);

            if(character.getHealth() <= 0){
                System.out.println("oh no...");
                return;
            }

            System.out.println("You hit " + enemy.getName() + " dealing " + character.getTotalAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealth() - character.getTotalAttack());

            if(enemy.getHealth() <= 0){
                System.out.println("For defeating " + enemy.getName() + " you have gained " + enemy.getCoins() + " coins\n");
                character.setCoins(character.getCoins() + enemy.getCoins());
                character.setEnemiesDefeated(character.getEnemiesDefeated() + 1);
                removeRoom(dungeon, character, enemy);
                return;
            }

            System.out.println("You have " + character.getHealth() + " health remaining");
            System.out.println(enemy.getName() + " has " + enemy.getHealth() + " remaining\n");
        }
    }

    Move enemyAttackChoice(Enemy enemy){
        RobertHolder currentMoves = enemy.getMoves();
        Random moveIndex = new Random();
        int i = moveIndex.nextInt(currentMoves.size());
        return (Move) currentMoves.getValueAtIndex(i);
    }

    void enemyAttackOutput(Enemy enemy, Character character){
        Move currentMove = enemyAttackChoice(enemy);
        System.out.println(enemy.getName() + " used " + currentMove.getMoveName());
        System.out.println(currentMove.getDescription());
        double damage = damageCalc(currentMove.getDamage(), character);
        System.out.println(enemy.getName() + " dealt " + damage + " damage\n\n");
        character.setHealthValue(character.getHealth() - damage);
    }

    double damageCalc(int damage, Character character){
        return damage - (damage * character.getTotalDefense());
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
