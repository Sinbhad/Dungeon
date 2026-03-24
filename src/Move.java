import java.util.Scanner;
import java.util.Random;
import lib.Node;


@SuppressWarnings("rawtypes")
public class Move {
    public static void movePlayer(Character character, int levelCount, Scanner keyboard){
        Node currentDungeonRoom = character.getCurrentRoom();
        Room currentRoom = (Room) currentDungeonRoom.getValue();

        System.out.println(currentRoom.getName() + ": Level " + levelCount);
        character.displayStats();

        System.out.print("\n\nWould you like to move left or right? (l/r) : ");
        String choice = keyboard.nextLine();

        if(choice.equals("l")){
            System.out.println("You have moved left\n");
            character.setCurrentRoom(currentDungeonRoom.getLastNode());

        }else if(choice.equals("r")){
            System.out.println("You have moved right\n");
            character.setCurrentRoom(currentDungeonRoom.getNextNode());

        }else{
            System.out.println("Invalid choice\n");
        }

    }

    public static void moveEnemy(){
        Enemy activeEnemy = null;
        for (Enemy enemy : EnemyLibrary.ENEMIES) {
            if (enemy.getCurrentRoom() != null) {
                activeEnemy = enemy;
            }

            Node enemyRoomNode = null;
            if (activeEnemy != null) {
                enemyRoomNode = activeEnemy.getCurrentRoom();
            }
            Room currentEnemyRoom = (Room) enemyRoomNode.getValue();
            currentEnemyRoom.setEnemyCharacter(null);

            int enemyChoice = new Random().nextInt(2);
            if (enemyChoice == 0) {
                enemyRoomNode = enemyRoomNode.getLastNode();
            } else {
                enemyRoomNode = enemyRoomNode.getNextNode();
            }

            if (activeEnemy != null) {
                activeEnemy.setCurrentRoom(enemyRoomNode);
            }
            ((Room) enemyRoomNode.getValue()).setEnemyCharacter(activeEnemy);
        }
    }
}
