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
        System.out.print("Enter your name challenger : ");
        player.setName(keyboard.nextLine().trim());

        while(player.getHealth() > 0){
            levelCount = conditionCheck(dungeon, player, levelCount, keyboard, enemyRoster);
            if(player.getHealth() > 0){
                player.move(levelCount, keyboard);
                moveEnemies(enemyRoster);
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
        Fight fight = new Fight();

        if(character.getRoomsTraversed() % 15 == 0 && character.getRoomsTraversed() != 0){
            tinkleBreak(character, enemyRoster);
        }

        if(currentRoom.getIsExit()){
            levelCount = exitRoom(dungeon, character, levelCount, enemyRoster);
            return levelCount;
        }

        if (currentRoom.getEnemyCharacter() != null) {
            fight.battle(dungeon, character, (Enemy) currentRoom.getEnemyCharacter(), levelCount);
        }

        currentRoomNode = character.getCurrentRoom();
        currentRoom = (Room) currentRoomNode.getValue();
        if(currentRoom.getItem() != null && character.getHealth() > 0){
            System.out.println("You found a chest!");
            character.openChest(keyboard);
        }
        return levelCount;
    }

    void moveEnemies(ArrayList<Enemy> enemyRoster){
        for (Enemy enemy : enemyRoster) {
            enemy.move();
        }
    }

    void tinkleBreak(Character character, ArrayList<Enemy> enemyRoster){
        System.out.println(character.getName() + " had to tinkle, stopping for a break...\n");
        moveEnemies(enemyRoster);
        if(character.getPotionsConsumed() > 3){
            character.setPotionsConsumed(0);
            System.out.println("Wow, that hurt! You just passed a kidney stone, you have lost 10 health points :(\n");
            character.setHealthValue(character.getHealth() - 10);
        }
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

    int pointsCount(int levelCount, Character character){
        int points = 0;
        points += levelCount * 100;
        points += character.getEnemiesDefeated() * 1000;
        points += character.getRoomsTraversed() * 50;
        return points;
    }

    void playAgain(Scanner keyboard){
        System.out.print("Would you like to play again? (y/n) : ");
        String playAgain = keyboard.nextLine();
        if(playAgain.trim().equals("y")){
            System.out.println("Let's play again!");
            dungeonOperator();
        }else{
            System.out.println("Thanks for playing!");
        }
    }

    void choosePerk(Character character){
        Random random = new Random();
        PerkLibrary perkLibrary = new PerkLibrary();
        int speedIndex = random.nextInt(perkLibrary.SPEED_PERKS.length);
        Perks speedPerk = perkLibrary.SPEED_PERKS[speedIndex];
        int defenseIndex = random.nextInt(perkLibrary.DEFENSE_PERKS.length);
        Perks defensePerk = perkLibrary.DEFENSE_PERKS[defenseIndex];
        int healthIndex = random.nextInt(perkLibrary.HEALTH_PERKS.length);
        Perks healthPerk = perkLibrary.HEALTH_PERKS[healthIndex];
        int damageIndex = random.nextInt(perkLibrary.DAMAGE_PERKS.length);
        Perks damagePerk = perkLibrary.DAMAGE_PERKS[damageIndex];
        System.out.println("Choose a perk");
        System.out.println("1. :" + speedPerk.getPerkName() + " - " + speedPerk.getDescription());
        System.out.println("2. :" + defensePerk.getPerkName() + " - " + defensePerk.getDescription());
        System.out.println("3. :" + healthPerk.getPerkName() + " - " + healthPerk.getDescription());
        System.out.println("4. :" + damagePerk.getPerkName() + " - " + damagePerk.getDescription());
        System.out.println("5. :Reroll for 100 coins\n\n");
        System.out.println("You have " + character.getCoins() + " coins");
        System.out.print("Enter your choice: (1-5) '0 to exit': ");
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();

        if(choice == 1 && checkBread(character, speedPerk)){
            character.setSpeedValue((int) (character.getSpeed() + speedPerk.getValue()));
        }else if(choice == 2 && checkBread(character, defensePerk)){
            if(character.getArmorDefense() == 0.8){
                System.out.println("You have already reached maximum defense, choose a different perk or move on");
                choosePerk(character);
            }
            character.setArmorDefenseValue((character.getPerkDefense() + defensePerk.getValue()));
            if(character.getTotalDefense() > 0.8){
                character.setArmorDefenseValue(0.8);
                System.out.println("Your defense value would exceed 80%, you have been set to 80% :(");
            }
        }else if(choice == 3 && checkBread(character, healthPerk)){
            character.setHealthValue((int) (character.getHealth() + healthPerk.getValue()));
        }else if(choice == 4 && checkBread(character, damagePerk)){
            character.setAttackValue((int) (character.getAttack() + damagePerk.getValue()));
        }else if(choice == 5 && character.getCoins() >= 100){
            character.setCoins(character.getCoins() - 100);
            choosePerk(character);
        }else if(choice == 0){
            System.out.println("Moving on then, good luck!\n\n");
        }else{
            System.out.println("Invalid choice");
            choosePerk(character);
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


