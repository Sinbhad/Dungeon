import lib.RobertCircularlyLinkedList;
import java.util.Scanner;
import java.util.Random;
import lib.Node;

public class Brawl {
    public static void fight(RobertCircularlyLinkedList<Room> dungeon,Character character, Enemy enemy){



        System.out.println("You have encountered " + enemy.getName() + ", hit him with all you got\n");
        if(character.getSpeed() >= enemy.getSpeed()){
            enemy.setHealthValue(enemy.getHealthValue() - character.getAttack());
            System.out.println("You have hit " + enemy.getName() + " and did " + character.getAttack() + " damage\n");
            character.setHealthValue(character.getHealth() - enemy.getAttack());
            System.out.println(enemy.getName() + " managed to get a hit in and did " + enemy.getAttack() + " damage\n\n");

            if(character.getHealth() <= 0){
                chooseToStart(character);
            }

        }else{
            character.setHealthValue(character.getHealth() - enemy.getAttack());
            System.out.println(enemy.getName() + " hit you, he did " + enemy.getAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealthValue() - character.getAttack());

            if(character.getHealth() <= 0){
                chooseToStart(character);
            }

            System.out.println("Luckily you swiped back, dealing " + character.getAttack() + " damage\n\n");
        }

        if(enemy.getHealthValue() <= 0){
            System.out.println("You have defeated " + enemy.getName() + ", move along\n\n");
            removeRoom(dungeon, character, enemy);

        }else{
            System.out.println("He ran off, better get them, " + enemy.getName() + " has " + enemy.getHealthValue() + " health remaining\n\n");
        }
    }


    public static void chooseToStart(Character character){
        RobertCircularlyLinkedList<Room> dungeon = new RobertCircularlyLinkedList<>();
        Scanner keyboard = new Scanner(System.in);
        String choice;


        System.out.println("You are dead...");
        System.out.print("\nWould you like to play again? (y/n): ");
        choice = keyboard.nextLine();
        if(choice.equalsIgnoreCase("y")){
            CreateDungeon.create(dungeon, 7);
            keyboard.close();
        }
        else if(choice.equalsIgnoreCase("n")){
            System.out.println("Lewser");
            System.exit(0);
        }else{
            System.out.println("I know this is hard to wrap your head around but you must choose (y/n) : ");
            chooseToStart(character);
        }
    }

    public static void removeRoom(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy){
        int random = new Random().nextInt(2);

        if(random == 0){
            character.setCurrentRoom(character.getCurrentRoom().getNextNode());
            System.out.println("The room you once knew has disappeared!\n You have been moved to the right.");
        }else{
            character.setCurrentRoom(character.getCurrentRoom().getLastNode());
            System.out.println("The room you once knew has disappeared!\n You have been moved to the left.");
        }

        Node enemyRoomNode = enemy.getCurrentRoom();
        dungeon.remove((Room)enemyRoomNode.getValue());
    }
}
