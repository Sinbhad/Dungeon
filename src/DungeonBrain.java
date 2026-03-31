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
        HighScoreDB highScoreDB = new HighScoreDB();
        highScoreDB.initializeDatabase();
        int roomCount = 7;
        int levelCount = 1;

        Player player = new Player();

        ArrayList<Enemy> enemyRoster = generator.enemyRosterGenerator();

        generator.create(dungeon, roomCount);
        generator.setRooms(dungeon);
        player.setCurrentRoom(dungeon.getHead());



        System.out.println("You have entered the dungeon");
        System.out.print("Enter your name challenger :");
        player.setName(keyboard.nextLine());

        while(player.getHealth() > 0){
            player.move(levelCount, keyboard);
            levelCount = conditionCheck(dungeon, player, levelCount, keyboard, enemyRoster);
            for (Enemy enemy : enemyRoster) {
                enemy.move();
            }
        }

        System.out.println("You have died...\n");
        System.out.println("You have defeated " + player.getEnemiesDefeated() + " enemies");
        System.out.println("You have survived for " + levelCount + " levels");
        System.out.println("You have traveled " + player.getRoomsTraversed() + " rooms");
        System.out.println("Better luck next time!");

        int finalPoints = pointsCount(levelCount, player);
        System.out.println("You have earned " + finalPoints + " points\n\n");

        highScoreDB.saveStats(player.getName(), finalPoints);
        highScoreDB.printHighScores();

        playAgain(keyboard);
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
        if(currentRoom.getItem() != null && character.getHealth() > 0){
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
            System.out.println("You have gained 100 coins and your opponents are now stronger!");
            levelCount++;
            character.setCoins(character.getCoins() + 100);

            for(int i = enemyRoster.size() - 1; i > 0; i--){
                enemyRoster.get(i).setHealthValue(enemyRoster.get(i).getHealthValue() + (5 * levelCount));
            }
            System.out.println("The enemy has gained " + (5 * levelCount) + " health points");

            for(int i = enemyRoster.size() - 1; i > 0; i--){
                enemyRoster.get(i).setAttackValue(enemyRoster.get(i).getAttackValue() + (5 * levelCount));
            }
            System.out.println("...and " + (5 * levelCount) + " attack points!\n");


            generator.create(dungeon, (7 + (5 * levelCount)));
            generator.setRooms(dungeon);
            character.setCurrentRoom(dungeon.getHead());
        }

        if(levelCount % 5 == 0){
            choosePerk(character);
        }
        return levelCount;
    }

    void fight(RobertCircularlyLinkedList<Room> dungeon,Character character, Enemy enemy){

        double trueAttack = enemy.getAttack() - (character.getDefense() * enemy.getAttack());
        System.out.println("You have encountered " + enemy.getName() + ", hit him with all you got\n");
        if(character.getSpeed() >= enemy.getSpeed()){
            enemy.setHealthValue(enemy.getHealthValue() - character.getAttack());
            System.out.println("You have hit " + enemy.getName() + " and did " + character.getAttack() + " damage\n");
            if(enemy.getHealthValue() <= 0){
                System.out.println("Good job!");
            }else{
                character.setHealthValue(character.getHealth() - trueAttack);
                System.out.println(enemy.getName() + " managed to get a hit in and did " + trueAttack + " damage\n\n");
            }
        }else{
            character.setHealthValue(character.getHealth() - trueAttack);
            System.out.println(enemy.getName() + " hit you, he did " + trueAttack + " damage\n");
            enemy.setHealthValue(enemy.getHealthValue() - character.getAttack());

            if(character.getHealth() <= 0){
                System.out.println("oh no...");
            }else{
                System.out.println("Luckily you swiped back, dealing " + character.getAttack() + " damage\n\n");
            }
        }

        if(enemy.getHealthValue() <= 0){
            System.out.println("You have defeated " + enemy.getName() + ", move along\n\n");
            removeRoom(dungeon, character, enemy);
            character.setEnemiesDefeated(character.getEnemiesDefeated() + 1);

        }else if(character.getHealth() > 0){
            System.out.println("He ran off, better get them, " + enemy.getName() + " has " + enemy.getHealthValue() + " health remaining\n\n");
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

    int pointsCount(int levelCount, Character character){
        int points = 0;
        points += levelCount * 100;
        points += character.getEnemiesDefeated() * 1000;
        points += character.getRoomsTraversed() * 50;
        return points;
    }

    void playAgain(Scanner keyboard){
        System.out.print("Would you like to play again? (y/n) :");
        String playAgain = keyboard.nextLine();
        if(playAgain.trim().equals("y")){
            System.out.println("Let's play again!");
            dungeonOperator();
        }else{
            System.out.println("Thanks for playing!");
        }
    }

    void choosePerk(Character character){
        int random = new Random().nextInt(2);
        PerkLibrary perkLibrary = new PerkLibrary();
        Perks speedPerk = perkLibrary.SPEED_PERKS[random];
        Perks defensePerk = perkLibrary.DEFENSE_PERKS[random];
        Perks healthPerk = perkLibrary.HEALTH_PERKS[random];
        Perks damagePerk = perkLibrary.DAMAGE_PERKS[random];
        System.out.println("Choose a perk");
        System.out.println("1. :" + speedPerk.getPerkName() + " - " + speedPerk.getDescription());
        System.out.println("2. :" + defensePerk.getPerkName() + " - " + defensePerk.getDescription());
        System.out.println("3. :" + healthPerk.getPerkName() + " - " + healthPerk.getDescription());
        System.out.println("4. :" + damagePerk.getPerkName() + " - " + damagePerk.getDescription());
        System.out.println("5. :Reroll for 100 coins");
        System.out.print("Enter your choice: (1-5) '0 to exit':");
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();

        if(choice == 1 && checkBread(character, speedPerk)){
            character.setSpeedValue((int) (character.getSpeed() + speedPerk.getValue()));
        }
        if(choice == 2 && checkBread(character, defensePerk)){
            character.setDefenseValue((character.getDefense() + defensePerk.getValue()));
        }
        if(choice == 3 && checkBread(character, healthPerk)){
            character.setHealthValue((int) (character.getHealth() + healthPerk.getValue()));
        }
        if(choice == 4 && checkBread(character, damagePerk)){
            character.setAttackValue((int) (character.getAttack() + damagePerk.getValue()));
        }
        if(choice == 5 && character.getCoins() >= 100){
            character.setCoins(character.getCoins() - 100);
            choosePerk(character);
        }
        if(choice == 0){
            System.out.println("Moving on then, good luck!\n\n");
        }

    }

    boolean checkBread(Character character, Perks perk){
        if(character.getCoins() >= perk.getCost()){
            character.setCoins(character.getCoins() - perk.getCost());
            return true;
        }else{
            System.out.println("You do not have enough coins to buy this perk");
            return false;
        }
    }

}


