package dungeon;

import characters.Character;
import characters.Enemy;
import lib.CustomArrayList;
import lib.Node;
import characters.*;
import ui.*;
import java.util.Random;
import java.util.Scanner;


public class DungeonBrain {
    /**
     * Essentially the heart of the entire game, creating the objects and database for use throughout
     */
    private final DungeonGenerator generator;
    private final Fight fight;
    private final GameState gameState;
    private final GameUI gameUI;
    private final HighScoreDB highScoreDB;
    private final PlayerFunctions playerFunctions;
    private final Scanner keyboard;



    public DungeonBrain(){
        this(new GameState());
    }

    public DungeonBrain(GameState gameState){
        this.generator = new DungeonGenerator();
        this.fight = new Fight(gameState);
        this.gameState = gameState;
        this.gameUI = new GameUI(gameState);
        this.highScoreDB = new HighScoreDB();
        this.playerFunctions = new PlayerFunctions(gameState);
        this.keyboard = new Scanner(System.in);
    }

    public void dungeonOperator(){
        Player player = gameState.getPlayer();
        highScoreDB.initializeDatabase();

        generator.createLevel(gameState);
        generator.setRooms(gameState);
        gameState.movePlayerToStart();

        //Intro output
        gameUI.prettyPrint("\n\nYou have entered the dungeon \nEnter your name challenger : ");

        player.setName(keyboard.nextLine().trim());

        //Loop input methods until the player has died
        while(player.getHealth() > 0){
            conditionCheck();
            if(player.getHealth() > 0){
                playerFunctions.move();
                moveEnemies();
            }
        }


        //Calculate the player's final score
        int finalPoints = pointsCount();

        //Output for game completion
        gameUI.prettyPrintln(
                "[BLD][R]You have died...[BRK]\n" +
                 "You have defeated [BLD][P]" + player.getEnemiesDefeated() + "[BRK] enemies\n" +
                 "You have survived for [BLD][P]" + gameState.getLevelCount() + "[BRK] levels\n" +
                 "You have traveled [BLD][P]" + player.getRoomsTraversed() + "[BRK] rooms\n" +
                 "You have earned [BLD][P]" + finalPoints + "[BRK] points\n" +
                 "[ITL]Better luck next time![BRK]\n\n"
        );


        //Adds score to the database and displays the top three scores and names of players
        highScoreDB.saveStats(player.getName(), finalPoints);
        highScoreDB.printHighScores();

        playAgain();
    }


    /**
     * Checks various conditions to move the game along, calls methods needed for certain conditions met
     */
    void conditionCheck(){
        Room currentRoom = gameState.getCurrentRoom();
        Player player = gameState.getPlayer();

        //Force the user into a bathroom break state, has the potential to cause damage and lets enemies move
        if(player.getRoomsTraversed() % 15 == 0 && player.getRoomsTraversed() != 0){
            tinkleBreak();
        }

        //Check for loot
        if(currentRoom.getItem() != null && player.getHealth() > 0){
            gameUI.prettyPrintln("[G]You found a chest![BRK]");
            playerFunctions.openChest();
        }

        //Handle exit room
        if(currentRoom.getIsExit()){
            exitRoomHandler();
        }

        //Begin battle if an enemy is encountered
        if (currentRoom.getEnemyCharacter() != null && !currentRoom.getIsExit()) {
            fight.battle();
        }
    }

    /**
     * Moves all existing enemies throughout the dungeon randomly
     */
    void moveEnemies(){
        for (Enemy enemy : gameState.getEnemyRoster()) {
            enemy.move();
        }
    }

    /**
     * Forces the user into a bathroom break.
     * This allows enemies to move while the player is stuck, and if the user
     * has consumed more than three potions by this break, they will take ten points of damage
     */
    void tinkleBreak(){
        Player player = gameState.getPlayer();
        gameUI.prettyPrintln(player.getName() + " had to tinkle, stopping for a break...\n");
        moveEnemies();

        //Reduce players health if they have drank more than three potions before passing a kidney stone
        if(player.getPotionsConsumed() > 3){
            player.clearPotionsConsumed();
            gameUI.prettyPrintln("[BLD]Wow, that hurt![BRK] \nYou just passed a kidney stone, [R]you have lost 10 health points :([BRK]\n");
            player.reduceHealthByStone();
        }
    }

    /**
     * Clears the current dungeon and generates a new one based on the level the player has reached.
     * Enemy health and damage is increased, the player is rewarded with coins for clearing a level.
     */
    void exitRoomHandler(){
        Room currentRoom = gameState.getCurrentRoom();
        int coinsPerLevel = 100 * gameState.getLevelCount();
        int enemyScaling = (gameState.getLevelCount() * 5);

        if(currentRoom.getIsExit()){
            gameUI.prettyPrintln(
                    "[BLD][G]You have found the exit![BRK]" +
                    "\nWelcome to the next level.\n" +
                    "\nYou have gained [BLD][Y]" + coinsPerLevel + "[BRK] coins and your opponents are now stronger!");

            //Increase level count for score keeping and logic such as enemy count, enemy damage and enemy health.
            gameState.advanceLevel();
            gameState.getPlayer().addCoins(coinsPerLevel);

            //Display enemy buffs
            gameUI.prettyPrintln("The enemy has gained " + (enemyScaling) + " health points\n" +
                                  "...and " + (enemyScaling) + " attack points!\n");

            //Create a new dungeon level and set enemy buffs
            generator.createLevel(gameState);
            generator.setRooms(gameState);

            CustomArrayList<Enemy> enemyRoster = gameState.getEnemyRoster();

            //Set enemy scaling
            for (Enemy enemy : gameState.getEnemyRoster()) {
                enemy.setHealth(enemy.getHealthValue() + enemyScaling);
                enemy.setAttackValue(enemy.getAttackValue() + enemyScaling);
            }

            gameState.movePlayerToStart();
        }

        //If the level count is a multiple of five, display the perk selection screen
        if(gameState.getLevelCount() % 5 == 0){
            choosePerk();
        }
    }

    /**
     * Point calculator for a completed run
     */
    int pointsCount(){
        int points = 0;
        points += gameState.getLevelCount() * 100;
        points += gameState.getPlayer().getEnemiesDefeated() * 1000;
        points += gameState.getPlayer().getRoomsTraversed() * 50;
        return points;
    }

    /**
     * Gives the player the choice to start over or end the program
     */
    void playAgain(){
        gameUI.prettyPrint("\n\nWould you like to play again? [C](Y/N)[BRK]: ");
        String playAgain = keyboard.nextLine();
        if (playAgain.trim().equalsIgnoreCase("y")) {
            gameUI.prettyPrintln("\nLet's play again!");
            DungeonBrain newGame = new DungeonBrain();
            newGame.dungeonOperator();
        }else if(playAgain.trim().equalsIgnoreCase("n")){
            gameUI.prettyPrintln("[BLD][ITL]Thanks for playing![BRK]");
            System.exit(0);
        }else{
            gameUI.prettyPrintln("[INVALID]");
        }
    }

    /**
     * Perk screen handler
     * Displays random perks from the perk library
     */
    void choosePerk(){
        boolean correctChoice = false;
        PerkShop perkShop = new PerkShop(gameState);

        //Keeps the player in a loop until they select a valid input, then applies changes if any.
        while(!correctChoice){
            perkShop.displayChoices();
            int choice = keyboard.nextInt();
            keyboard.nextLine();
            correctChoice = perkShop.applyChoice(choice);
        }
    }

}


