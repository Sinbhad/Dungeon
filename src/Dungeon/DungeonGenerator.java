package Dungeon;

import Items.ItemLibrary;
import Characters.*;
import lib.Node;
import lib.RobertCircularlyLinkedList;

import java.util.Random;
import java.util.ArrayList;

public class DungeonGenerator {

    /**
     * Creates a dungeon with the same number of rooms as the value passed into roomCount
     * @param dungeon currently used dungeon
     * @param roomCount number of rooms in the dungeon
     */
    public void create(RobertCircularlyLinkedList<Room> dungeon, int roomCount){
        dungeon.clear();
        for(int i = 0; i < roomCount; i++){
            dungeon.add(new Room("Room " + (i + 1), null, null, null, false));
        }
    }

    /**
     * Sets items and enemies in rooms randomly
     * @param dungeon currently used dungeon
     */
    void setRooms(RobertCircularlyLinkedList<Room> dungeon) {
        ArrayList<Enemy> enemyRoster = enemyRosterGenerator();

        Random chanceNum = new Random();
        int size = dungeon.getSize();

        Node tempNode = dungeon.getHead();
        Room tempRoom = (Room)tempNode.getValue();
        for (int i = 0; i < size; i++) {
            tempRoom.setCertain(null, null, null, false);
            tempRoom = (Room)tempNode.getNextNode().getValue();
        }

        
        enemyLevelCheck(chanceNum, size, dungeon, enemyRoster);
        setWeapon(chanceNum, size, dungeon);
        setPotion(chanceNum, size, dungeon);
        setTrap(chanceNum, size, dungeon);
        setArmor(chanceNum, size, dungeon);
        setExit(chanceNum, size, dungeon);
    }

    /**
     * Generates an enemy roster based on the enemies in the enemy library
     * to be used in conjunction with the setRooms method to ensure enemies have full health
     * upon entering a new dungeon level
     * @return ArrayList of enemies
     */
    ArrayList<Enemy> enemyRosterGenerator() {
        ArrayList<Enemy> enemyRoster = new ArrayList<>();
        enemyRoster.add(new Slime());
        enemyRoster.add(new Daniel());
        enemyRoster.add(new Jared());
        enemyRoster.add(new Patrick());
        enemyRoster.add(new Andrew());
        enemyRoster.add(new Byron());
        enemyRoster.add(new Jenna());
        enemyRoster.add(new Joe());
        enemyRoster.add(new Marc());
        enemyRoster.add(new Samir());
        return enemyRoster;
    }

    /**
     * Gets a random weapon from the weapon section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param size number of rooms in the dungeon
     * @param dungeon currently used dungeon
     */
    void setWeapon(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int weaponRoomIndex = chanceNum.nextInt(size);
        Room weaponRoom = dungeon.getValAtIndex(weaponRoomIndex);
        weaponRoom.setItem(itemLibrary.WEAPONS[chanceNum.nextInt(itemLibrary.WEAPONS.length)]);
    }

    /**
     * Gets a random potion from the potion section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param size number of rooms in the dungeon
     * @param dungeon currently used dungeon
     */
    void setPotion(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int healthPotionRoomIndex = chanceNum.nextInt(size);
        Room healthPotionRoom = dungeon.getValAtIndex(healthPotionRoomIndex);
        healthPotionRoom.setItem(itemLibrary.HEALTH_POTIONS[chanceNum.nextInt(itemLibrary.HEALTH_POTIONS.length)]);
    }

    /**
     * Gets a random trap from the trap section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param size number of rooms in the dungeon
     * @param dungeon currently used dungeon
     */
    void setTrap(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int trapRoomIndex = chanceNum.nextInt(size);
        Room trapRoom = dungeon.getValAtIndex(trapRoomIndex);
        trapRoom.setItem(itemLibrary.TRAPS[chanceNum.nextInt(itemLibrary.TRAPS.length)]);
    }

    /**
     * Gets a random armor set from the armor section of the item library to add to the dungeon on each new level
     * @param chanceNum random number generator
     * @param size number of rooms in the dungeon
     * @param dungeon currently used dungeon
     */
    void setArmor(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int armorRoomIndex = chanceNum.nextInt(size);
        Room armorRoom = dungeon.getValAtIndex(armorRoomIndex);
        armorRoom.setItem(itemLibrary.ARMOR_PIECES[chanceNum.nextInt(itemLibrary.ARMOR_PIECES.length)]);
    }

    /**
     * Randomly chooses a room number that will be assigned as the exit
     * @param chanceNum random number generator
     * @param size number of rooms in the dungeon
     * @param dungeon currently used dungeon
     */
    void setExit(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        int exitRoomIndex = chanceNum.nextInt(size);
        Room exitRoom = dungeon.getValAtIndex(exitRoomIndex);
        exitRoom.setIsExit(true);
    }

    /**
     * Randomly chooses the room for an enemy to be placed in.
     * This is called for each enemy in the current roster when the setRooms
     * method is called
     * @param chanceNum random number generator
     * @param size number of rooms in the dungeon
     * @param dungeon currently used dungeon
     * @param enemyRoster arraylist of enemies in the current dungeon
     */
    void setEnemies(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon, ArrayList<Enemy> enemyRoster) {
        int enemyRoomIndex = chanceNum.nextInt(size);
        Node enemyRoomNode = dungeon.getNodeAtIndex(enemyRoomIndex);
        Room enemyRoom = (Room) enemyRoomNode.getValue();
        Enemy enemy = enemyRoster.get(chanceNum.nextInt(enemyRoster.size()));
        enemyRoom.setEnemyCharacter(enemy);
        enemy.setCurrentRoom(enemyRoomNode);
    }

    /**
     * Determines how many enemies will be added to the current floor based on the level count
     * @param chanceNum random number generator
     * @param size number of rooms in the dungeon
     * @param dungeon currently used dungeon
     * @param enemyRoster arraylist of enemies in the current dungeon
     */
    void enemyLevelCheck(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon, ArrayList<Enemy> enemyRoster){
        int levelCount = size / 7;
        if (levelCount == 1) {
            setEnemies(chanceNum, size, dungeon, enemyRoster);
        } else if (levelCount > 1 && levelCount < 4) {
            for (int i = 0; i < 2; i++) {
                setEnemies(chanceNum, size, dungeon, enemyRoster);
            }
        } else if(levelCount >= 4 && levelCount <= 7){
            for(int i = 0; i < 4; i++){
                setEnemies(chanceNum, size, dungeon, enemyRoster);
            }
        }else {
            for (int i = 0; i < enemyRoster.size(); i++) {
                setEnemies(chanceNum, size, dungeon, enemyRoster);
            }
        }
    }
}