import lib.RobertCircularlyLinkedList;

public class CreateDungeon {
    public void create(RobertCircularlyLinkedList<Room> dungeon, int roomCount){
        dungeon.clear();
        for(int i = 0; i < roomCount; i++){
            dungeon.add(new Room("Room " + (i + 1), null, null, null, false));
        }
    }
}
