import lib.CircularlyLinkedList;
import java.util.Scanner;
import java.util.Random;
import lib.CircularlyLinkedList;
import lib.Node;

public class Brawl {
    public void fight(CircularlyLinkedList<Room> dungeon,Character character, Enemy enemy){


        System.out.println("You have encountered a enemy, hit him with all you got\n");
        if(character.getSpeed() >= enemy.getSpeed()){
            enemy.setHealthValue(enemy.getHealth() - character.getAttack());
            System.out.println("You have hit the enemy and did " + character.getAttack() + " damage\n");
            character.setHealthValue(character.getHealth() - enemy.getAttack());
            System.out.println("He managed to get a hit in and did " + enemy.getAttack() + " damage\n\n");

            if(character.getHealth() <= 0){
                chooseToStart(character);
            }

        }else{
            character.setHealthValue(character.getHealth() - enemy.getAttack());
            System.out.println("The enemy hit you, he did " + enemy.getAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealth() - character.getAttack());

            if(character.getHealth() <= 0){
                chooseToStart(character);
            }

            System.out.println("Luckily you swiped back, dealing " + character.getAttack() + " damage\n\n");
        }

        if(enemy.getHealth() <= 0){
            System.out.println("You have defeated the enemy, move along\n\n");

        }else{
            System.out.println("He ran off, better get him, he has " + enemy.getHealth() + " health remaining\n\n");
        }
    }


    public void chooseToStart(Character character){
        CircularlyLinkedList<Room> dungeon = new CircularlyLinkedList<>();
        CreateDungeon dungeonCreate = new CreateDungeon();
        Scanner keyboard = new Scanner(System.in);
        String choice;


        System.out.println("You are dead...");
        System.out.print("\nWould you like to play again? (y/n): ");
        choice = keyboard.nextLine();
        if(choice.equalsIgnoreCase("y")){
            dungeonCreate.create(dungeon, 7);
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

    public void removeRoom(CircularlyLinkedList<Room> dungeon, Character character, Enemy enemy){
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
