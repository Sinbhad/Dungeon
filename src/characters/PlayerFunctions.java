package characters;
import dungeon.GameState;
import dungeon.Room;
import items.Armor;
import items.Weapon;
import lib.CustomArrayList;
import lib.Node;
import ui.GameUI;

import java.util.Scanner;

public class PlayerFunctions {
    private final GameState gameState;
    private final GameUI gameUI;
    private final InventoryManager inventoryManager;
    private final Scanner keyboard = new Scanner(System.in);

    public PlayerFunctions(){
        this.gameState = new GameState();
        this.gameUI = new GameUI();
        this.inventoryManager = new InventoryManager();
    }

    public PlayerFunctions(GameState gameState){
        this.gameState = gameState;
        this.gameUI = new GameUI();
        this.inventoryManager = new InventoryManager();
    }

    /**
     * Output prompt and logic for player dungeon traversal
     */
    public void move() {
        Player player = gameState.getPlayer();
        Node<Room> currentDungeonRoom = player.getCurrentRoom();
        Room currentRoom = currentDungeonRoom.getValue();

        gameUI.prettyPrintln("[BLD]" + currentRoom.getName() + ": Level " + gameState.getLevelCount());
        gameUI.displayStats(gameState);

        gameUI.prettyPrint("\n\nEnter [C]I[BRK] to display inventory \nWould you like to move left or right? [C](L/R)[BRK]: ");
        String choice = keyboard.nextLine();

        if (choice.trim().equalsIgnoreCase("l")) {

            gameUI.prettyPrintln("\n[CLR][G]You have moved left[BRK]\n");
            player.setCurrentRoom(currentDungeonRoom.getLastNode());
            player.increaseRoomsTraversed();

        } else if (choice.trim().equalsIgnoreCase("r")) {
            gameUI.prettyPrintln("\n[CLR]You have moved right\n");
            player.setCurrentRoom(currentDungeonRoom.getNextNode());
            player.increaseRoomsTraversed();

        }else if(choice.trim().equalsIgnoreCase("i")){
            gameUI.prettyPrintln("[CLR]-=Inventory=-\n");
            inventoryManager.displayInventory();
        } else {
            gameUI.prettyPrintln("[INVALID]");
        }
    }

    /**
     * Chest opening handler. Provides players with the choice to open a chest or not.
     * Updates stats according to item type and attributes.
     */
    public void openChest(){
        Player player = gameState.getPlayer();
        Node<Room> currentDungeonRoom = player.getCurrentRoom();
        Room currentRoom = currentDungeonRoom.getValue();

        //Prompt the user
        gameUI.prettyPrint("\n\nWould you like to open the chest? [C](Y/N)[BRK]: ");
        String choice = keyboard.nextLine();

        //Evaluate input
        if(choice.trim().equalsIgnoreCase("y")){
            gameUI.prettyPrintln("\n[G]You have opened the chest[BRK]\n" +
                    "You have found a [BLD][B]" + currentRoom.getItem().getName() + "[BRK], " +
                    "[ITL]this " + currentRoom.getItem().getDescription() + "[BRK]\n\n\n");

            //Set stats based on item attributes
            if (currentRoom.getItem() != null && currentRoom.getItem().getHpValue() != 0) {
                inventoryManager.healingItemHandler();
            }
            if (currentRoom.getItem() != null && currentRoom.getItem().getSpeedValue() != 0) {
                inventoryManager.speedItemHandler();
            }
            if (currentRoom.getItem() != null && currentRoom.getItem() instanceof Weapon) {
                inventoryManager.weaponItemHandler();
            }
            if (currentRoom.getItem() != null && currentRoom.getItem() instanceof Armor) {
                inventoryManager.armorItemHandler();
            }
            if (currentRoom.getItem() != null && currentRoom.getItem().getStaminaValue() != 0) {
                inventoryManager.staminaItemHandler();
            }
            currentRoom.setItem(null);

        }else if(choice.trim().equalsIgnoreCase("n")){
            gameUI.prettyPrintln("[G]You have not opened the chest[BRK]");
        }else{
            gameUI.prettyPrintln("[INVALID]");
            openChest();
        }
    }

    /**
     * Method to display available moves to the player as well as displaying useful stats that can help the player decide
     */
    public Move chooseMove(){
        //Define a null move to store the user's selection
        Move move = null;
        Player player = gameState.getPlayer();
        Weapon weapon = player.getWeapon();
        //If a user has no weapon, they have no moves to choose from
        if(weapon == null || weapon.getMoves() == null || weapon.getMoves().size() == 0){
            gameUI.prettyPrintln("[BLD][R]You have no moves to choose from![BRK]\n");
            //Default move (punch)
            move = gameUI.returnSelectedMoveFormatted(gameState, 12);
        }else{
            //Displays all moves associated with current weapon
            gameUI.prettyPrintln("Choose a move from the following list:");
            CustomArrayList<Move> currentMoves = weapon.getMoves();
            for (int i = 0; i < currentMoves.size(); i++) {
                Move m = currentMoves.getAtIndex(i);
                gameUI.prettyPrintln("[C]" + (i + 1) + "[BRK][BLD]: " + m.getMoveName() + " [BRK][Y]Sp[BRK]: " + m.getStaminaCost() +
                        " [R]DMG[BRK]: " + (m.getDamage() + player.getTotalAttack()));
            }
            gameUI.prettyPrintln("Sp available [Y]" + player.getStamina() + "[BRK]\n");
            gameUI.prettyPrint("Enter your choice: ");
            String choice = keyboard.nextLine();
            int choiceNum;
            try {
                choiceNum = Integer.parseInt(choice.trim());
            } catch (NumberFormatException e) {
                choiceNum = -1;
            }
            move = gameUI.returnSelectedMoveFormatted(gameState, choiceNum);
        }
        return move;
    }
}
