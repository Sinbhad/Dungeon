import lib.CircularlyLinkedList;
import lib.Node;

import java.util.Random;

public class SetRooms {
    public void setRooms(CircularlyLinkedList<Room> dungeon, Enemy enemy){
        Random chanceNum = new Random();
        int size = dungeon.getSize();

        for(int i = 0; i < size; i++){
            Room tempRoom = dungeon.getValAtIndex(i);
            tempRoom.setCertain(null, null, null, false);
        }

        int enemyRoomIndex = chanceNum.nextInt(size);
        Node enemyRoomNode = dungeon.getNodeAtIndex(enemyRoomIndex);
        Room enemyRoom = (Room) enemyRoomNode.getValue();
        enemyRoom.setEnemyCharacter(enemy);
        enemy.setCurrentRoom(enemyRoomNode);

        int swordRoomIndex = chanceNum.nextInt(size);
        Room swordRoom = dungeon.getValAtIndex(swordRoomIndex);
        swordRoom.setItem(ItemLibrary.SWORDS[chanceNum.nextInt(ItemLibrary.SWORDS.length)]);

        int healthPotionRoomIndex = chanceNum.nextInt(size);
        Room healthPotionRoom = dungeon.getValAtIndex(healthPotionRoomIndex);
        healthPotionRoom.setItem(ItemLibrary.HEALTH_POTIONS[chanceNum.nextInt(ItemLibrary.HEALTH_POTIONS.length)]);

        int trapRoomIndex = chanceNum.nextInt(size);
        Room trapRoom = dungeon.getValAtIndex(trapRoomIndex);
        trapRoom.setItem(ItemLibrary.TRAPS[chanceNum.nextInt(ItemLibrary.TRAPS.length)]);

        int exitRoomIndex = chanceNum.nextInt(size);
        Room exitRoom = dungeon.getValAtIndex(exitRoomIndex);
        exitRoom.setIsExit(true);
    }
}
