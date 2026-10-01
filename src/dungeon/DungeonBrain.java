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
    private final GameUI gameUI = new GameUI();
    private final Scanner keyboard = new Scanner(System.in);
    private final DungeonGenerator generator = new DungeonGenerator();
    private final GameState gameState = new GameState();
    private final HighScoreDB highScoreDB = new HighScoreDB();
    private final PlayerFunctions playerFunctions = new PlayerFunctions();

    //------------------Create constructor that passes in gameState

    public void dungeonOperator(){
        Player player = gameState.getPlayer();
        Fight fight = new Fight(gameState);
        highScoreDB.initializeDatabase();

        generator.createLevel(gameState);
        generator.setRooms(gameState);
        player.setCurrentRoom(gameState.getDungeon().getHead());

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
        Node<Room> currentRoomNode;
        Room currentRoom;
        Player player = gameState.getPlayer();

        //Force the user into a bathroom break state, has the potential to cause damage and lets enemies move
        if(player.getRoomsTraversed() % 15 == 0 && player.getRoomsTraversed() != 0){
            tinkleBreak();
        }

        //Check for loot
        currentRoomNode = player.getCurrentRoom();
        currentRoom = currentRoomNode.getValue();
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
        if(player.getPotionsConsumed() > 3){
            player.setPotionsConsumed(0);
            gameUI.prettyPrintln("[BLD]Wow, that hurt![BRK] \nYou just passed a kidney stone, [R]you have lost 10 health points :([BRK]\n");
            player.setHealth(player.getHealth() - 10);
        }
    }

    /**
     * Clears the current dungeon and generates a new one based on the level the player has reached.
     * Enemy health and damage is increased, the player is rewarded with coins for clearing a level.
     */
    void exitRoomHandler(){
        Node<Room> currentRoomNode = gameState.getPlayer().getCurrentRoom();
        Room currentRoom = currentRoomNode.getValue();
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
            for(Enemy enemy : enemyRoster){
                enemy.setHealth(enemy.getHealthValue() + enemyScaling);
            }
            for(Enemy enemy: enemyRoster){
                enemy.setAttackValue(enemy.getAttackValue() + enemyScaling);
            }

            gameState.getPlayer().setCurrentRoom(gameState.getDungeon().getHead());
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
        if(playAgain.trim().equalsIgnoreCase("y")){
            gameUI.prettyPrintln("\n\n\n\nLet's play again!");
            dungeonOperator();
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
        Random random = new Random();
        PerkLibrary perkLibrary = new PerkLibrary();
        Player player = gameState.getPlayer();

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
        gameUI.prettyPrintln(
                "[BLD][C]Choose a perk[BRK]" +
                "\n[C]1.[BRK] :[BLD]" + speedPerk.getPerkName() + "[BRK] - [ITL]" + speedPerk.getDescription() + "[BRK]" +
                "\n[C]2.[BRK] :[BLD]" + defensePerk.getPerkName() + "[BRK] - [ITL]" + defensePerk.getDescription() + "[BRK]" +
                "\n[C]3.[BRK] :[BLD]" + healthPerk.getPerkName() + "[BRK] - [ITL]" + healthPerk.getDescription() + "[BRK]" +
                "\n[C]4.[BRK] :[BLD]" + damagePerk.getPerkName() + "[BRK] - [ITL]" + damagePerk.getDescription() + "[BRK]" +
                "\n[C]5.[BRK] :[BLD]" + staminaPerk.getPerkName() + "[BRK] - [ITL]" + staminaPerk.getDescription() + "[BRK]" +
                "\n[C]6.[BRK] :[BLD]Reroll for [C]100[BRK][BLD] coins[BRK]\n\n [ITL]" +
                "\nYou have [BLD][Y]" + player.getCoins() + "[BRK] coins");

        gameUI.prettyPrint("Enter your choice: [C](1-6)[BRK] [R][BLD]'0 to exit'[BRK]: ");
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();

        //Update stats based on user entry or reroll perks
        if(choice == 1 && checkBread(player, speedPerk)){
            player.setSpeedValue((int) (player.getSpeed() + speedPerk.getValue()));
        }else if(choice == 2 && checkBread(player, defensePerk)){
            if(player.getTotalDefense() == 0.8){
                gameUI.prettyPrintln("[BLD][R]You have already reached maximum defense, choose a different perk or move on[BRK]");
                choosePerk();
            }
            player.setTotalDefense(player.getArmorDefense() , (player.getPerkDefense() + defensePerk.getValue()));
            if(player.getTotalDefense() > 0.8){
                player.setTotalDefense(0.8, 0);
                gameUI.prettyPrintln("[BLD][C]Your defense value would exceed 80%, you have been set to 80% :([BRK]");
            }
        }else if(choice == 3 && checkBread(player, healthPerk)){
            player.setMaxHealth((player.getMaxHealth() + healthPerk.getValue()));
        }else if(choice == 4 && checkBread(player, damagePerk)) {
            player.setAttackValue((int) (player.getAttack() + damagePerk.getValue()));
        }else if(choice == 5 && checkBread(player, staminaPerk)){
            player.setStamina((int) (player.getStamina() + staminaPerk.getValue()));
        }else if(choice == 6 && player.getCoins() >= 100){
            player.setCoins(player.getCoins() - 100);
            choosePerk();
        }else if(choice == 0){
            gameUI.prettyPrintln("Moving on then, good luck!\n\n");
        }else{
            gameUI.prettyPrintln("[INVALID]");
            choosePerk();
        }

    }

    /**
     * Helper class to determine if the player has enough coins to complete their current selection in the perk menu
     * @param character player character
     * @param perk perk object
     */
    boolean checkBread(Character character, Perks perk){
        if(character.getCoins() >= perk.getCost()){
            character.setCoins(character.getCoins() - perk.getCost());
            return true;
        }else{
            gameUI.prettyPrintln("[BLD][R]You do not have enough coins to buy this perk![BRK]");
            return false;
        }
    }

    

}


