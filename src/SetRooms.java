import lib.RobertCircularlyLinkedList;
import lib.Node;

import java.util.Random;

public class SetRooms {
    public static void setRooms(RobertCircularlyLinkedList<Room> dungeon){
        Random chanceNum = new Random();
        int size = dungeon.getSize();

        for(int i = 0; i < size; i++){
            Room tempRoom = dungeon.getValAtIndex(i);
            tempRoom.setCertain(null, null, null, false);
        }

        int levelCount = size / 7;
        if(levelCount == 1){
            setEnemies(chanceNum, size, dungeon);
        }else if(levelCount > 1 && levelCount < 4){
            for(int i = 0; i < 1; i++){
                setEnemies(chanceNum, size, dungeon);
            }
        }else{
            for(int i = 0; i < EnemyLibrary.ENEMIES.length; i++){
                setEnemies(chanceNum, size, dungeon);
            }
        }
        
        
        int swordRoomIndex = chanceNum.nextInt(size);
        Room swordRoom = dungeon.getValAtIndex(swordRoomIndex);
        swordRoom.setItem(ItemLibrary.SWORDS[chanceNum.nextInt(ItemLibrary.SWORDS.length)]);

        int healthPotionRoomIndex = chanceNum.nextInt(size);
        Room healthPotionRoom = dungeon.getValAtIndex(healthPotionRoomIndex);
        healthPotionRoom.setItem(ItemLibrary.HEALTH_POTIONS[chanceNum.nextInt(ItemLibrary.HEALTH_POTIONS.length)]);

        int trapRoomIndex = chanceNum.nextInt(size);
        Room trapRoom = dungeon.getValAtIndex(trapRoomIndex);
        trapRoom.setItem(ItemLibrary.TRAPS[chanceNum.nextInt(ItemLibrary.TRAPS.length)]);

        int armorRoomIndex = chanceNum.nextInt(size);
        Room armorRoom = dungeon.getValAtIndex(armorRoomIndex);
        armorRoom.setItem(ItemLibrary.ARMOR_PIECES[chanceNum.nextInt(ItemLibrary.ARMOR_PIECES.length)]);

        int exitRoomIndex = chanceNum.nextInt(size);
        Room exitRoom = dungeon.getValAtIndex(exitRoomIndex);
        exitRoom.setIsExit(true);
        
    }
    
    public static void setEnemies(Random chanceNum, int size, RobertCircularlyLinkedList<Room> dungeon){
        int enemyRoomIndex = chanceNum.nextInt(size);
        Node enemyRoomNode = dungeon.getNodeAtIndex(enemyRoomIndex);
        Room enemyRoom = (Room) enemyRoomNode.getValue();
        Enemy enemy = EnemyLibrary.ENEMIES[chanceNum.nextInt(EnemyLibrary.ENEMIES.length)];
        enemyRoom.setEnemyCharacter(enemy);
        enemy.setCurrentRoom(enemyRoomNode);
    } 
}
