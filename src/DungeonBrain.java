import lib.Node;
import lib.RobertCircularlyLinkedList;

import java.util.Random;
import java.util.Scanner;


public class DungeonBrain {
    public void dungeon(){
        DungeonGenerator generator = new DungeonGenerator();
        Rob rob = new Rob();
        Scanner keyboard = new Scanner(System.in);
        RobertCircularlyLinkedList<Room> dungeon = new RobertCircularlyLinkedList<>();
        int roomCount = 7;
        int levelCount = 1;

        generator.create(dungeon, roomCount);
        generator.setRooms(dungeon);
        rob.setCurrentRoom(dungeon.getHead());

        System.out.println("You have entered the dungeon");

        while(rob.getHealth() > 0){
            rob.move(levelCount, keyboard);
            levelCount = conditionCheck(dungeon, rob, levelCount);
            for (Enemy enemy : EnemyLibrary.ENEMIES) {
                enemy.move();
            }
        }
    }






    int conditionCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, int levelCount){
        Node currentRoomNode = character.getCurrentRoom();
        Room currentRoom = (Room) currentRoomNode.getValue();
        DungeonGenerator generator = new DungeonGenerator();
        DungeonBrain dungeonBrain = new DungeonBrain();


        if(currentRoom.getIsExit()){
            System.out.println("You have found the exit");
            System.out.println("welcome to the next level.\n");
            System.out.println("You have gained nothing and your opponents are now stronger!");
            levelCount++;


            for(int i = EnemyLibrary.ENEMIES.length - 1; i > 0; i--){
                EnemyLibrary.ENEMIES[i].setHealthValue(EnemyLibrary.ENEMIES[i].getHealthValue() + (10 * levelCount));
            }
            System.out.println("The enemy has gained " + (10 * levelCount) + " health points");

            for(int i = EnemyLibrary.ENEMIES.length - 1; i > 0; i--){
                EnemyLibrary.ENEMIES[i].setAttackValue(EnemyLibrary.ENEMIES[i].getAttackValue() + (10 * levelCount));
            }
            System.out.println("...and " + (10 * levelCount)+ " attack points!\n");


            generator.create(dungeon, 7 * levelCount);
            generator.setRooms(dungeon);
            character.setCurrentRoom(dungeon.getHead());

            return levelCount;
        }

        if (currentRoom.getEnemyCharacter() != null) {
            dungeonBrain.fight(dungeon, character, (Enemy) currentRoom.getEnemyCharacter());
        }

        if(currentRoom.getItem() != null){
            System.out.println("You found a chest!");
            ChestOpener.openChest(character.getCurrentRoom(), character);
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
                chooseToStart(character);
            }

        }else{
            character.setHealthValue(character.getHealth() - trueAttack);
            System.out.println(enemy.getName() + " hit you, he did " + trueAttack + " damage\n");
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


    void chooseToStart(Character character){
        DungeonGenerator generator = new DungeonGenerator();
        RobertCircularlyLinkedList<Room> dungeon = new RobertCircularlyLinkedList<>();
        Scanner keyboard = new Scanner(System.in);
        String choice;


        System.out.println("You are dead...");
        System.out.print("\nWould you like to play again? (y/n): ");
        choice = keyboard.nextLine();
        if(choice.trim().equalsIgnoreCase("y")){
            generator.create(dungeon, 7);
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

    void removeRoom(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy){
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


