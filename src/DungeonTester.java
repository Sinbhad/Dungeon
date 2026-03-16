import lib.CircularlyLinkedList;
import java.util.Scanner;


public class DungeonTester {
    public static void dungeon(){
        Rob rob = new Rob();
        Enemy enemy = new Enemy();
        Scanner keyboard = new Scanner(System.in);
        CreateDungeon createDungeon = new CreateDungeon();
        SetRooms setRooms = new SetRooms();

        int roomCount = 7;
        int levelCount = 1;

        CircularlyLinkedList<Room> dungeon = new CircularlyLinkedList<>();


        createDungeon.create(dungeon, roomCount);
        setRooms.setRooms(dungeon, enemy);

        rob.setCurrentRoom(dungeon.getHead());

        System.out.println("You have entered the dungeon");



        while(rob.getHealth() > 0){
            Move move = new Move();
            ConditionChecker conditionChecker = new ConditionChecker();

            move.movePlayer(rob, levelCount, keyboard);

            levelCount = conditionChecker.conditionCheck(dungeon, rob, enemy, levelCount);

            move.moveEnemy(enemy);
        }
    }


}

