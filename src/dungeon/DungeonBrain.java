package dungeon;

import characters.Character;
import characters.Enemy;
import lib.RobertCircularlyLinkedList;
import lib.Node;
import characters.*;
import ui.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class DungeonBrain {
    /**
     * Essentially the heart of the entire game, creating the objects and database for use throughout
     */
    public void dungeonOperator(){
        DungeonGenerator generator = new DungeonGenerator();
        Scanner keyboard = new Scanner(System.in);
        RobertCircularlyLinkedList<Room> dungeon = new RobertCircularlyLinkedList<>();
        HighScoreDB highScoreDB = new HighScoreDB();
        highScoreDB.initializeDatabase();
        GameUI gameUI = new GameUI();
        int roomCount = 7;
        int levelCount = 1;

        Player player = new Player();
        ArrayList<Enemy> enemyRoster = generator.enemyRosterGenerator();

        generator.createLevel(dungeon, roomCount);
        generator.setRooms(dungeon);
        player.setCurrentRoom(dungeon.getHead());

        //Intro output
        //gameUI.prettyPrint("\n\n[P][BLD]You have entered the dungeon[BRK]");
        System.out.println("\n\nYou have entered the dungeon");
        System.out.print("Enter your name challenger : ");
        player.setName(keyboard.nextLine().trim());

        //Loop input methods until the player has died
        while(player.getHealth() > 0){
            levelCount = conditionCheck(dungeon, player, levelCount, keyboard, enemyRoster);
            if(player.getHealth() > 0){
                player.move(levelCount, keyboard);
                moveEnemies(enemyRoster);
            }
        }

        //Output for game completion
        System.out.println("You have died...\n");
        System.out.println("You have defeated " + player.getEnemiesDefeated() + " enemies");
        System.out.println("You have survived for " + levelCount + " levels");
        System.out.println("You have traveled " + player.getRoomsTraversed() + " rooms");
        System.out.println("Better luck next time!");

        //Calculate and display the player's final score
        int finalPoints = pointsCount(levelCount, player);
        System.out.println("You have earned " + finalPoints + " points\n\n");

        //Adds score to the database and displays the top three scores and names of players
        highScoreDB.saveStats(player.getName(), finalPoints);
        highScoreDB.printHighScores();

        playAgain(keyboard);
    }


    /**
     * Checks various conditions to move the game along, calls methods needed for certain conditions met
     * @param dungeon the current dungeon being used
     * @param character player character
     * @param levelCount current dungeon level
     * @param keyboard scanner object for user input
     * @param enemyRoster arraylist of enemies in the current dungeon
     * @return int value of the current dungeon level
     */
    int conditionCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, int levelCount, Scanner keyboard, ArrayList<Enemy> enemyRoster){
        Node currentRoomNode = character.getCurrentRoom();
        Room currentRoom = (Room) currentRoomNode.getValue();
        Fight fight = new Fight();

        //Force the user into a bathroom break state, has the potential to cause damage and lets enemies move
        if(character.getRoomsTraversed() % 15 == 0 && character.getRoomsTraversed() != 0){
            tinkleBreak(character, enemyRoster);
        }

        //Handle exit room
        if(currentRoom.getIsExit()){
            levelCount = exitRoom(dungeon, character, levelCount, enemyRoster);
            return levelCount;
        }

        //Begin battle if an enemy is encountered
        if (currentRoom.getEnemyCharacter() != null && !currentRoom.getIsExit()) {
            fight.battle(dungeon, character, currentRoom.getEnemyCharacter(), levelCount);
        }

        //Check for loot
        currentRoomNode = character.getCurrentRoom();
        currentRoom = (Room) currentRoomNode.getValue();
        if(currentRoom.getItem() != null && character.getHealth() > 0){
            System.out.println("You found a chest!");
            character.openChest(keyboard);
        }
        return levelCount;
    }

    /**
     * Moves all existing enemies throughout the dungeon randomly
     * @param enemyRoster arraylist of enemies in the current dungeon
     */
    void moveEnemies(ArrayList<Enemy> enemyRoster){
        for (Enemy enemy : enemyRoster) {
            enemy.move();
        }
    }

    /**
     * Forces the user into a bathroom break.
     * This allows enemies to move while the player is stuck, and if the user
     * has consumed more than three potions by this break, they will take ten points of damage
     * @param character player character
     * @param enemyRoster arraylist of enemies in the current dungeon
     */
    void tinkleBreak(Character character, ArrayList<Enemy> enemyRoster){
        System.out.println(character.getName() + " had to tinkle, stopping for a break...\n");
        moveEnemies(enemyRoster);
        if(character.getPotionsConsumed() > 3){
            character.setPotionsConsumed(0);
            System.out.println("Wow, that hurt! You just passed a kidney stone, you have lost 10 health points :(\n");
            character.setHealthValue(character.getHealth() - 10);
        }
    }

    /**
     * Clears the current dungeon and generates a new one based on the level the player has reached.
     * Enemy health and damage is increased, the player is rewarded with coins for clearing a level.
     * @param dungeon the current dungeon being used
     * @param character player character
     * @param levelCount current dungeon level
     * @param enemyRoster arraylist of enemies in the current dungeon
     * @return int value of the current dungeon level
     */
    int exitRoom(RobertCircularlyLinkedList<Room> dungeon, Character character, int levelCount, ArrayList<Enemy> enemyRoster){
        Node currentRoomNode = character.getCurrentRoom();
        Room currentRoom = (Room) currentRoomNode.getValue();
        DungeonGenerator generator = new DungeonGenerator();

        if(currentRoom.getIsExit()){
            System.out.println("You have found the exit");
            System.out.println("welcome to the next level.\n");
            System.out.println("You have gained 100 coins and your opponents are now stronger!");

            //Increase level count for score keeping and logic such as enemy count, enemy damage and enemy health.
            levelCount++;
            character.setCoins(character.getCoins() + 100);

            //Display enemy buffs
            System.out.println("The enemy has gained " + (5 * levelCount) + " health points");
            System.out.println("...and " + (5 * levelCount) + " attack points!\n");

            //Create a new dungeon level and set enemy buffs
            generator.createLevel(dungeon, (7 + (5 * levelCount)));
            generator.setRooms(dungeon);
            for(Enemy enemy : enemyRoster){
                enemy.setHealthValue(enemy.getHealthValue() + (5 * levelCount));
            }
            for(Enemy enemy: enemyRoster){
                enemy.setAttackValue(enemy.getAttackValue() + (5 * levelCount));
            }
            character.setCurrentRoom(dungeon.getHead());
        }

        //If the level count is a multiple of five, display the perk selection screen
        if(levelCount % 5 == 0){
            choosePerk(character);
        }

        return levelCount;
    }

    /**
     * Point calculator for a completed run
     * @param levelCount current dungeon level
     * @param character player character
     * @return int value of the player's total points
     */
    int pointsCount(int levelCount, Character character){
        int points = 0;
        points += levelCount * 100;
        points += character.getEnemiesDefeated() * 1000;
        points += character.getRoomsTraversed() * 50;
        return points;
    }

    /**
     * Gives the player the choice to start over or end the program
     * @param keyboard scanner object for user input
     */
    void playAgain(Scanner keyboard){
        System.out.print("\n\nWould you like to play again? (y/n) : ");
        String playAgain = keyboard.nextLine();
        if(playAgain.trim().equalsIgnoreCase("y")){
            System.out.println("\n\n\n\nLet's play again!");
            dungeonOperator();
        }else if(playAgain.trim().equalsIgnoreCase("n")){
            System.out.println("Thanks for playing!");
            System.exit(0);
        }else{
            System.out.println("Invalid input!");
        }
    }

    /**
     * Perk screen handler
     * Displays random perks from the perk library
     * @param character player character
     */
    void choosePerk(Character character){
        Random random = new Random();
        PerkLibrary perkLibrary = new PerkLibrary();

        //Determine which perks will be available
        int speedIndex = random.nextInt(perkLibrary.SPEED_PERKS.length);
        Perks speedPerk = perkLibrary.SPEED_PERKS[speedIndex];
        int defenseIndex = random.nextInt(perkLibrary.DEFENSE_PERKS.length);
        Perks defensePerk = perkLibrary.DEFENSE_PERKS[defenseIndex];
        int healthIndex = random.nextInt(perkLibrary.HEALTH_PERKS.length);
        Perks healthPerk = perkLibrary.HEALTH_PERKS[healthIndex];
        int damageIndex = random.nextInt(perkLibrary.DAMAGE_PERKS.length);
        Perks damagePerk = perkLibrary.DAMAGE_PERKS[damageIndex];
        int staminaIndex = random.nextInt(perkLibrary.STAMINA_PERKS.length);
        Perks staminaPerk = perkLibrary.STAMINA_PERKS[staminaIndex];

        //Output for player to aid in selection
        System.out.println("Choose a perk");
        System.out.println("1. :" + speedPerk.getPerkName() + " - " + speedPerk.getDescription());
        System.out.println("2. :" + defensePerk.getPerkName() + " - " + defensePerk.getDescription());
        System.out.println("3. :" + healthPerk.getPerkName() + " - " + healthPerk.getDescription());
        System.out.println("4. :" + damagePerk.getPerkName() + " - " + damagePerk.getDescription());
        System.out.println("5. :" + staminaPerk.getPerkName() + " - " + staminaPerk.getDescription());
        System.out.println("6. :Reroll for 100 coins\n\n");
        System.out.println("You have " + character.getCoins() + " coins");
        System.out.print("Enter your choice: (1-6) '0 to exit': ");
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();

        //Update stats based on user entry or reroll perks
        if(choice == 1 && checkBread(character, speedPerk)){
            character.setSpeedValue((int) (character.getSpeed() + speedPerk.getValue()));
        }else if(choice == 2 && checkBread(character, defensePerk)){
            if(character.getTotalDefense() == 0.8){
                System.out.println("You have already reached maximum defense, choose a different perk or move on");
                choosePerk(character);
            }
            character.setTotalDefense(character.getArmorDefense() , (character.getPerkDefense() + defensePerk.getValue()));
            if(character.getTotalDefense() > 0.8){
                character.setTotalDefense(0.8, 0);
                System.out.println("Your defense value would exceed 80%, you have been set to 80% :(");
            }
        }else if(choice == 3 && checkBread(character, healthPerk)){
            character.setMaxHealth((character.getMaxHealth() + healthPerk.getValue()));
        }else if(choice == 4 && checkBread(character, damagePerk)) {
            character.setAttackValue((int) (character.getAttack() + damagePerk.getValue()));
        }else if(choice == 5 && checkBread(character, staminaPerk)){
            character.setStamina((int) (character.getStamina() + staminaPerk.getValue()));
        }else if(choice == 6 && character.getCoins() >= 100){
            character.setCoins(character.getCoins() - 100);
            choosePerk(character);
        }else if(choice == 0){
            System.out.println("Moving on then, good luck!\n\n");
        }else{
            System.out.println("Invalid choice");
            choosePerk(character);
        }

    }

    /**
     * Helper class to determine if the player has enough coins to complete their current selection in the perk menu
     * @param character player character
     * @param perk perk object
     * @return boolean value of whether the player has enough coins to buy the perk or not
     */
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


