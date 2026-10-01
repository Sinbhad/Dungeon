package dungeon;

import items.ItemLibrary;
import characters.*;
import items.Weapon;
import items.weapons.*;
import lib.CustomArrayList;
import lib.Node;
import lib.CustomCircularlyLinkedList;
import java.util.Random;

public class DungeonGenerator {

    /**
     * Creates a dungeon with the same number of rooms as the value passed into roomCount
     * @param gameState GameState object used to pass in various values
     */
    public void createLevel(GameState gameState){
        gameState.getDungeon().clear();
        for(int i = 0; i < gameState.getRoomCount(); i++){
            gameState.getDungeon().add(new Room("Room " + (i + 1), null, null, null, false));
        }
    }

    /**
     * Sets items and enemies in rooms randomly
     * @param gameState GameState object used to pass in various values
     */
    void setRooms(GameState gameState) {
        Random chanceNum = new Random();

        clearRooms(gameState);

        generateEnemyRoster(gameState);
        weaponRosterGenerator(gameState);

        enemyLevelCheck(chanceNum, gameState);

        setWeapon(chanceNum, gameState);
        setPotion(chanceNum, gameState);
        setTrap(chanceNum, gameState);
        setArmor(chanceNum, gameState);
        setExit(chanceNum, gameState);
    }

    void clearRooms(GameState gameState){
        Node<Room> tempNode = gameState.getDungeon().getHead();
        Room tempRoom = tempNode.getValue();
        for (int i = 0; i < gameState.getRoomCount(); i++) {
            tempRoom.setCertain(null, null, null, false);
            tempRoom = tempNode.getNextNode().getValue();
        }
    }

    /**
     * Creates an enemy roster for lower levels
     * @param gameState GameState object, used to alter roster
     */
    void lowEnemyRosterGenerator(GameState gameState) {
        gameState.addEnemy(new Slime());
        gameState.addEnemy(new Jared());
        gameState.addEnemy(new Samir());
    }

    /**
     * Creates an enemy roster for mid-levels
     * @param gameState GameState object, used to alter roster
     */
    void midEnemyRosterGenerator(GameState gameState){
        gameState.addEnemy(new Jenna());
        gameState.addEnemy(new Marc());
        gameState.addEnemy(new Joe());
        gameState.addEnemy(new Daniel());
    }

    /**
     * Creates a roster of the strongest enemies for later levels
     * @param gameState GameState object, used to alter roster
     */
    void highEnemyRosterGenerator(GameState gameState){
        gameState.addEnemy(new Patrick());
        gameState.addEnemy(new Andrew());
        gameState.addEnemy(new Byron());
    }

    /**
     * Creates a roster of enemies based on current progression
     * @param gameState GameState object used to pass in various values
     */
    void generateEnemyRoster(GameState gameState){
        int levelCount = gameState.getLevelCount();
        //Set difficulty based on level count
        if (levelCount == 1) {
            gameState.clearEnemyRoster();
            lowEnemyRosterGenerator(gameState);
        } else if (levelCount > 1 && levelCount <= 4) {
            gameState.clearEnemyRoster();
            lowEnemyRosterGenerator(gameState);
            midEnemyRosterGenerator(gameState);
        } else{
            gameState.clearEnemyRoster();
            lowEnemyRosterGenerator(gameState);
            midEnemyRosterGenerator(gameState);
            highEnemyRosterGenerator(gameState);
        }
    }

    /**
     * Generates an enemy roster based on the weapons in the item library
     * to be used with setRooms to ensure each floor contains a weapon chest
     * @param gameState GameState object used to obtain the weapon roster.
     */
    void weaponRosterGenerator(GameState gameState){
        gameState.clearWeaponRoster();

        gameState.addWeapon(new Dagger());
        gameState.addWeapon(new ShortSword());
        gameState.addWeapon(new BroadSword());
        gameState.addWeapon(new Mace());
        gameState.addWeapon(new Hammer());
        gameState.addWeapon(new TwoHandedHammer());
        gameState.addWeapon(new TacticalWalkingStick());
        gameState.addWeapon(new MagesStaff());
    }

    /**
     * Gets a random weapon from the weapon section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param gameState GameState object used to pass various values
     */
    void setWeapon(Random chanceNum, GameState gameState) {
        //Store weapon roster for easier work and location of room to place weapon in
        CustomArrayList<Weapon> weaponRoster = gameState.getWeaponRoster();
        int weaponRoomIndex = chanceNum.nextInt(gameState.getRoomCount());

        //Prepare the room and the weapon
        Room weaponRoom = gameState.getDungeon().getValAtIndex(weaponRoomIndex);
        int weaponIndex = chanceNum.nextInt(weaponRoster.size());
        Weapon weapon = weaponRoster.getAtIndex(chanceNum.nextInt(weaponIndex));

        //Place the weapon into the room
        weaponRoom.setItem(weapon);
    }

    /**
     * Gets a random potion from the potion section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param gameState GameState object used to pass various values
     */
    void setPotion(Random chanceNum, GameState gameState) {
        //Prepare the item library and grab an index for a random room to place it in
        ItemLibrary itemLibrary = new ItemLibrary();
        int healthPotionRoomIndex = chanceNum.nextInt(gameState.getRoomCount());

        //Prepare the room and place a random potion in it
        Room healthPotionRoom = gameState.getDungeon().getValAtIndex(healthPotionRoomIndex);
        int potionIndex = chanceNum.nextInt(itemLibrary.HEALTH_POTIONS.length);
        healthPotionRoom.setItem(itemLibrary.HEALTH_POTIONS[potionIndex]);
    }

    /**
     * Gets a random trap from the trap section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param gameState GameState object used to pass various values
     */
    void setTrap(Random chanceNum, GameState gameState) {
        //Prepare the item library and grab an index for a random room to place it in
        ItemLibrary itemLibrary = new ItemLibrary();
        int trapRoomIndex = chanceNum.nextInt(gameState.getRoomCount());

        //Prepare the room and place a random trap in it
        Room trapRoom = gameState.getDungeon().getValAtIndex(trapRoomIndex);
        int trapIndex = chanceNum.nextInt(itemLibrary.TRAPS.length);
        trapRoom.setItem(itemLibrary.TRAPS[trapIndex]);
    }

    /**
     * Gets a random armor set from the armor section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param gameState GameState object used to pass various values
     */
    void setArmor(Random chanceNum, GameState gameState) {
        //Prepare the item library and grab an index for a random room to place it in
        ItemLibrary itemLibrary = new ItemLibrary();
        int armorRoomIndex = chanceNum.nextInt(gameState.getRoomCount());

        //Prepare the room and place a random armor in it
        Room armorRoom = gameState.getDungeon().getValAtIndex(armorRoomIndex);
        int armorIndex = chanceNum.nextInt(itemLibrary.ARMOR_PIECES.length);
        armorRoom.setItem(itemLibrary.ARMOR_PIECES[armorIndex]);
    }

    /**
     * Randomly chooses a room number that will be assigned as the exit
     * @param chanceNum random number generator
     * @param gameState GameState object used to pass various values
     */
    void setExit(Random chanceNum, GameState gameState) {
        //Grab a random location to choose as the exit
        int exitRoomIndex = chanceNum.nextInt(gameState.getRoomCount());

        //Prepare the room and set the exit flag to true
        Room exitRoom = gameState.getDungeon().getValAtIndex(exitRoomIndex);
        exitRoom.setIsExit(true);
    }

    /**
     * Randomly chooses the room for an enemy to be placed in.
     * This is called for each enemy in the current roster when the setRooms
     * method is called
     * @param chanceNum random number generator
     * @param gameState GameState object used to pass in other values
     */
    void setEnemies(Random chanceNum, GameState gameState) {
        //Stores a value to use as the location to place a new enemy and prepares the enemy roster
        int enemyRoomIndex = chanceNum.nextInt(gameState.getRoomCount());
        CustomArrayList<Enemy> enemyRoster = gameState.getEnemyRoster();

        //Stores the node the enemy will be placed in
        Node<Room> enemyRoomNode = gameState.getDungeon().getNodeAtIndex(enemyRoomIndex);
        //Prepares the room object
        Room enemyRoom = enemyRoomNode.getValue();
        //Grabs a random enemy from the roster
        Enemy enemy = enemyRoster.getAtIndex(chanceNum.nextInt(enemyRoster.size()));

        //Finally places the enemy in the appropriate room
        enemyRoom.setEnemyCharacter(enemy);
        enemy.setCurrentRoom(enemyRoomNode);
    }

    /**
     * Determines how many enemies will be added to the current floor based on the level count
     * @param chanceNum random number generator
     * @param gameState used to gather various points of data about the current run
     */
    void enemyLevelCheck(Random chanceNum, GameState gameState){
        int levelCount = gameState.getLevelCount();

        if (levelCount == 1) {
            setEnemies(chanceNum, gameState);
        } else if (levelCount > 1 && levelCount < 4) {
            for (int i = 0; i < 2; i++) {
                setEnemies(chanceNum, gameState);
            }
        } else if(levelCount >= 4 && levelCount <= 7){
            for(int i = 0; i < 4; i++){
                setEnemies(chanceNum, gameState);
            }
        }else {
            for (int i = 0; i < gameState.getEnemyRoster().size(); i++) {
                setEnemies(chanceNum, gameState);
            }
        }
    }
}