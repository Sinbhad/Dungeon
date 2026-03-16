import java.util.Scanner;
import java.util.Random;
import lib.Node;


@SuppressWarnings("rawtypes")
public class Move {
    public void movePlayer(Character character, int levelCount, Scanner keyboard){
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

    public void moveEnemy(Enemy enemy){
        Node enemyRoomNode = enemy.getCurrentRoom();

        Room currentEnemyRoom = (Room) enemyRoomNode.getValue();
        currentEnemyRoom.setEnemyCharacter(null);

        int enemyChoice = new Random().nextInt(2);
        if (enemyChoice == 0) {
            enemyRoomNode = enemyRoomNode.getLastNode();
        } else {
            enemyRoomNode = enemyRoomNode.getNextNode();
        }

        enemy.setCurrentRoom(enemyRoomNode);

        ((Room) enemyRoomNode.getValue()).setEnemyCharacter(enemy);
    }
}
