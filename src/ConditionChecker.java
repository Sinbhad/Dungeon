import lib.RobertCircularlyLinkedList;
import lib.Node;

public class ConditionChecker {
    public int conditionCheck(RobertCircularlyLinkedList<Room> dungeon, Character character, Enemy enemy, int levelCount){
        ChestOpener chestOpener = new ChestOpener();
        CreateDungeon createDungeon = new CreateDungeon();
        Brawl brawl = new Brawl();
        SetRooms setRooms = new SetRooms();

        Node currentRoomNode = character.getCurrentRoom();
        Room currentRoom = (Room) currentRoomNode.getValue();

        if(currentRoom.getIsExit()){
            System.out.println("You have found the exit");
            System.out.println("welcome to the next level.\n");
            System.out.println("You have gained nothing and your opponents are now stronger!");
            levelCount++;

            enemy.setHealthValue(new Enemy().getHealth() + 10 * levelCount);
            System.out.println("The enemy has gained " + (10 * levelCount) + " health points");
            enemy.setAttackValue(enemy.getAttack() + 10 * levelCount);
            System.out.println("...and " + (10 * levelCount)+ " attack points!");


            createDungeon.create(dungeon, 7 * levelCount);
            setRooms.setRooms(dungeon, enemy);
            character.setCurrentRoom(dungeon.getHead());

            currentRoom = (Room) character.getCurrentRoom().getValue();
        }

        if (currentRoom.getEnemyCharacter() != null) {
            brawl.fight(character, enemy);
        }

        if(currentRoom.getItem() != null){
            System.out.println("You found a chest!");
            chestOpener.openChest(character.getCurrentRoom(), character, levelCount);
        }
        return levelCount;
    }
}
