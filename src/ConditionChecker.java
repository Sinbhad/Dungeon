import lib.RobertCircularlyLinkedList;
import lib.Node;

public class ConditionChecker {
    public static int conditionCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, int levelCount){
        Node currentRoomNode = character.getCurrentRoom();
        Room currentRoom = (Room) currentRoomNode.getValue();

        if(currentRoom.getIsExit()){
            System.out.println("You have found the exit");
            System.out.println("welcome to the next level.\n");
            System.out.println("You have gained nothing and your opponents are now stronger!");
            levelCount++;


            for(int i = EnemyLibrary.ENEMIES.length - 1; i > 0; i--){
                EnemyLibrary.ENEMIES[i].setHealthValue(EnemyLibrary.ENEMIES[i].getHealthValue() + (10 * levelCount));
            }
            System.out.println("The enemy has gained " + (10 * levelCount) + " health points");

            for(int i = EnemyLibrary.ENEMIES.length - 1; i > 0; i--){
                EnemyLibrary.ENEMIES[i].setAttackValue(EnemyLibrary.ENEMIES[i].getAttackValue() + (10 * levelCount));
            }
            System.out.println("...and " + (10 * levelCount)+ " attack points!\n");


            CreateDungeon.create(dungeon, 7 * levelCount);
            SetRooms.setRooms(dungeon);
            character.setCurrentRoom(dungeon.getHead());

            return levelCount;
        }

        if (currentRoom.getEnemyCharacter() != null) {
            Brawl.fight(dungeon, character, (Enemy) currentRoom.getEnemyCharacter());
        }

        if(currentRoom.getItem() != null){
            System.out.println("You found a chest!");
            ChestOpener.openChest(character.getCurrentRoom(), character);
        }

        return levelCount;
    }
}
