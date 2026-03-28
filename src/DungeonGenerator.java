import lib.Node;
import lib.RobertCircularlyLinkedList;

import java.util.Random;
import java.util.ArrayList;

public class DungeonGenerator {

    public void create(RobertCircularlyLinkedList<Room> dungeon, int roomCount){
        dungeon.clear();
        for(int i = 0; i < roomCount; i++){
            dungeon.add(new Room("Room " + (i + 1), null, null, null, false));
        }
    }


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

        int levelCount = size / 7;
        if (levelCount == 1) {
            setEnemies(chanceNum, size, dungeon, enemyRoster);
        } else if (levelCount > 1 && levelCount < 4) {
            for (int i = 0; i < 2; i++) {
                setEnemies(chanceNum, size, dungeon, enemyRoster);
            }
        } else {
            for (int i = 0; i < enemyRoster.size(); i++) {
                setEnemies(chanceNum, size, dungeon, enemyRoster);
            }
        }

        setWeapon(chanceNum, size, dungeon);
        setHealthPotion(chanceNum, size, dungeon);
        setTrap(chanceNum, size, dungeon);
        setArmor(chanceNum, size, dungeon);
        setExit(chanceNum, size, dungeon);
    }

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
        return enemyRoster;
    }


    void setWeapon(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int weaponRoomIndex = chanceNum.nextInt(size);
        Room weaponRoom = dungeon.getValAtIndex(weaponRoomIndex);
        weaponRoom.setItem(itemLibrary.WEAPONS[chanceNum.nextInt(itemLibrary.WEAPONS.length)]);
    }

    void setHealthPotion(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int healthPotionRoomIndex = chanceNum.nextInt(size);
        Room healthPotionRoom = dungeon.getValAtIndex(healthPotionRoomIndex);
        healthPotionRoom.setItem(itemLibrary.HEALTH_POTIONS[chanceNum.nextInt(itemLibrary.HEALTH_POTIONS.length)]);
    }

    void setTrap(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int trapRoomIndex = chanceNum.nextInt(size);
        Room trapRoom = dungeon.getValAtIndex(trapRoomIndex);
        trapRoom.setItem(itemLibrary.TRAPS[chanceNum.nextInt(itemLibrary.TRAPS.length)]);
    }

    void setArmor(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        ItemLibrary itemLibrary = new ItemLibrary();
        int armorRoomIndex = chanceNum.nextInt(size);
        Room armorRoom = dungeon.getValAtIndex(armorRoomIndex);
        armorRoom.setItem(itemLibrary.ARMOR_PIECES[chanceNum.nextInt(itemLibrary.ARMOR_PIECES.length)]);
    }

    void setExit(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon) {
        int exitRoomIndex = chanceNum.nextInt(size);
        Room exitRoom = dungeon.getValAtIndex(exitRoomIndex);
        exitRoom.setIsExit(true);
    }

    void setEnemies(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon, ArrayList<Enemy> enemyRoster) {
        int enemyRoomIndex = chanceNum.nextInt(size);
        Node enemyRoomNode = dungeon.getNodeAtIndex(enemyRoomIndex);
        Room enemyRoom = (Room) enemyRoomNode.getValue();
        Enemy enemy = enemyRoster.get(chanceNum.nextInt(enemyRoster.size()));
        enemyRoom.setEnemyCharacter(enemy);
        enemy.setCurrentRoom(enemyRoomNode);
    }
}