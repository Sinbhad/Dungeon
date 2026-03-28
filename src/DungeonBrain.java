import lib.RobertCircularlyLinkedList;
import lib.Node;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class DungeonBrain {
    public void dungeonOperator(){
        DungeonGenerator generator = new DungeonGenerator();
        Scanner keyboard = new Scanner(System.in);
        RobertCircularlyLinkedList<Room> dungeon = new RobertCircularlyLinkedList<>();
        int roomCount = 7;
        int levelCount = 1;

        Rob rob = new Rob();

        ArrayList<Enemy> enemyRoster = generator.enemyRosterGenerator();

        generator.create(dungeon, roomCount);
        generator.setRooms(dungeon);
        rob.setCurrentRoom(dungeon.getHead());

        System.out.println("You have entered the dungeon");

        while(rob.getHealth() > 0){
            rob.move(levelCount, keyboard);
            levelCount = conditionCheck(dungeon, rob, levelCount, keyboard, enemyRoster);
            for (Enemy enemy : enemyRoster) {
                enemy.move();
            }
        }
    }






    int conditionCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, int levelCount, Scanner keyboard, ArrayList<Enemy> enemyRoster){
        Node currentRoomNode = character.getCurrentRoom();
        Room currentRoom = (Room) currentRoomNode.getValue();
        DungeonBrain dungeonBrain = new DungeonBrain();


        if(currentRoom.getIsExit()){
            levelCount = exitRoom(dungeon, character, levelCount, enemyRoster);
            return levelCount;
        }

        if (currentRoom.getEnemyCharacter() != null) {
            dungeonBrain.fight(dungeon, character, (Enemy) currentRoom.getEnemyCharacter());
        }

        currentRoomNode = character.getCurrentRoom();
        currentRoom = (Room) currentRoomNode.getValue();
        if(currentRoom.getItem() != null){
            System.out.println("You found a chest!");
            character.openChest(keyboard);
        }
        return levelCount;
    }

    int exitRoom(RobertCircularlyLinkedList<Room> dungeon, Character character, int levelCount, ArrayList<Enemy> enemyRoster){
        Node currentRoomNode = character.getCurrentRoom();
        Room currentRoom = (Room) currentRoomNode.getValue();
        DungeonGenerator generator = new DungeonGenerator();

        if(currentRoom.getIsExit()){
            System.out.println("You have found the exit");
            System.out.println("welcome to the next level.\n");
            System.out.println("You have gained nothing and your opponents are now stronger!");
            levelCount++;

            for(int i = enemyRoster.size() - 1; i > 0; i--){
                enemyRoster.get(i).setHealthValue(enemyRoster.get(i).getHealthValue() + (5 * levelCount));
            }
            System.out.println("The enemy has gained " + (5 * levelCount) + " health points");

            for(int i = enemyRoster.size() - 1; i > 0; i--){
                enemyRoster.get(i).setAttackValue(enemyRoster.get(i).getAttackValue() + (5 * levelCount));
            }
            System.out.println("...and " + (5 * levelCount)+ " attack points!\n");


            generator.create(dungeon, 7 * levelCount);
            generator.setRooms(dungeon);
            character.setCurrentRoom(dungeon.getHead());
        }
        return levelCount;
    }

    void fight(RobertCircularlyLinkedList<Room> dungeon,Character character, Enemy enemy){

        double trueAttack = enemy.getAttack() - (character.getDefense() * enemy.getAttack());
        System.out.println("You have encountered " + enemy.getName() + ", hit him with all you got\n");
        if(character.getSpeed() >= enemy.getSpeed()){
            enemy.setHealthValue(enemy.getHealthValue() - character.getAttack());
            System.out.println("You have hit " + enemy.getName() + " and did " + character.getAttack() + " damage\n");

            character.setHealthValue(character.getHealth() - trueAttack);
            System.out.println(enemy.getName() + " managed to get a hit in and did " + trueAttack + " damage\n\n");

            if(character.getHealth() <= 0){
                System.out.println("You have died...");
            }

        }else{
            character.setHealthValue(character.getHealth() - trueAttack);
            System.out.println(enemy.getName() + " hit you, he did " + trueAttack + " damage\n");
            enemy.setHealthValue(enemy.getHealthValue() - character.getAttack());

            if(character.getHealth() <= 0){
                System.out.println("You have died...");
            }else{
                System.out.println("Luckily you swiped back, dealing " + character.getAttack() + " damage\n\n");
            }
        }

        if(enemy.getHealthValue() <= 0){
            System.out.println("You have defeated " + enemy.getName() + ", move along\n\n");
            removeRoom(dungeon, character, enemy);

        }else{
            System.out.println("He ran off, better get them, " + enemy.getName() + " has " + enemy.getHealthValue() + " health remaining\n\n");
        }
    }

    void removeRoom(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy){
        int random = new Random().nextInt(2);

        if(random == 0){
            character.setCurrentRoom(character.getCurrentRoom().getNextNode());
            System.out.println("The room you once knew has disappeared!\nYou have been moved to the right.");
        }else{
            character.setCurrentRoom(character.getCurrentRoom().getLastNode());
            System.out.println("The room you once knew has disappeared!\nYou have been moved to the left.");
        }

        Node enemyRoomNode = enemy.getCurrentRoom();
        dungeon.remove((Room)enemyRoomNode.getValue());
    }

}


