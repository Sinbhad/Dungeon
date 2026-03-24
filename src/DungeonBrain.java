import lib.RobertCircularlyLinkedList;
import java.util.Scanner;


public class DungeonBrain {
    public static void dungeon(){
        Rob rob = new Rob();
        Scanner keyboard = new Scanner(System.in);
        RobertCircularlyLinkedList<Room> dungeon = new RobertCircularlyLinkedList<>();
        int roomCount = 7;
        int levelCount = 1;

        CreateDungeon.create(dungeon, roomCount);
        SetRooms.setRooms(dungeon);
        rob.setCurrentRoom(dungeon.getHead());

        System.out.println("You have entered the dungeon");

        while(rob.getHealth() > 0){
            Move.movePlayer(rob, levelCount, keyboard);
            levelCount = ConditionChecker.conditionCheck(dungeon, rob, levelCount);
            Move.moveEnemy();
        }
    }


}

