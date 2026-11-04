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

    private Player player;


    public PlayerFunctions(){
        this.gameState = new GameState();
        this.gameUI = new GameUI();
        this.inventoryManager = new InventoryManager();
        this.player = gameState.getPlayer();
    }

    public PlayerFunctions(GameState gameState){
        this.gameState = gameState;
        this.gameUI = new GameUI(gameState);
        this.inventoryManager = new InventoryManager(gameState);
        this.player = gameState.getPlayer();
    }

    /**
     * Output prompt and logic for player dungeon traversal
     */
    public void move() {
        player = gameState.getPlayer();

        gameUI.renderScreen();

        gameUI.prettyPrint("Enter [C]I[BRK] to display inventory \nWould you like to move left or right? [C](L/R)[BRK]: ");
        String choice = keyboard.nextLine();

        if (choice.trim().equalsIgnoreCase("l")) {
            gameUI.logMessage("[G]You have moved left[BRK]");
            moveLeft();
            player.increaseRoomsTraversed();

        } else if (choice.trim().equalsIgnoreCase("r")) {
            gameUI.logMessage("[G]You have moved right[BRK]");
            moveRight();
            player.increaseRoomsTraversed();

        } else if (choice.trim().equalsIgnoreCase("i")) {
            gameUI.prettyPrintln("\n-=Inventory=-");
            inventoryManager.displayInventory();
        } else {
            gameUI.logMessage("[INVALID]");
        }
    }

    public void moveLeft(){
        player.setCurrentRoom(gameState.getLeftRoom());
    }

    public void moveRight(){
        player.setCurrentRoom(gameState.getRightRoom());
    }

    /**
     * Chest opening handler. Provides players with the choice to open a chest or not.
     * Updates stats according to item type and attributes.
     */
    public void openChest(){
        Room currentRoom = gameState.getCurrentRoom();

        gameUI.renderScreen();

        //Prompt the user
        gameUI.prettyPrint("Would you like to open the chest? [C](Y/N)[BRK]: ");
        String choice = keyboard.nextLine();

        //Evaluate input
        if(choice.trim().equalsIgnoreCase("y")){
            gameUI.logMessage("[G]You have opened the chest![BRK] " +
                    "You have found a [BLD][B]" + currentRoom.getItem().getName() + "[BRK], " +
                    "[ITL]this " + currentRoom.getItem().getDescription() + "[BRK]");

            //Set stats based on item attributes
            if (currentRoom.getItem() != null && currentRoom.getItem().getHpValue() > 0) {
                gameUI.renderScreen();
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
            if (currentRoom.getItem() != null && currentRoom.getItem().getStaminaValue() < 0) {
                inventoryManager.staminaItemHandler();
            }
            if(currentRoom.getItem() != null && (currentRoom.getItem().getStaminaValue() < 0 || currentRoom.getItem().getHpValue() < 0)){
                inventoryManager.badLuckHandler();
            }
            currentRoom.setItem(null);

        }else if(choice.trim().equalsIgnoreCase("n")){
            gameUI.logMessage("[G]You have not opened the chest[BRK]");
        }else{
            gameUI.logMessage("[INVALID]");
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
            gameUI.logMessage("[BLD][R]You have no moves to choose from![BRK]");
            //Default move (punch)
            move = gameUI.returnSelectedMoveFormatted(12);
        }else{
            //Displays all moves associated with current weapon
            gameUI.prettyPrintln("\nChoose a move from the following list:");
            CustomArrayList<Move> currentMoves = weapon.getMoves();
            for (int i = 0; i < currentMoves.size(); i++) {
                Move m = currentMoves.getAtIndex(i);
                gameUI.prettyPrintln("[C]" + (i + 1) + "[BRK][BLD]: " + m.getMoveName() + " [BRK][Y]Sp[BRK]: " + m.getStaminaCost() +
                        " [R]DMG[BRK]: " + (m.getDamage() + player.getTotalAttack()));
            }
            gameUI.prettyPrintln("Sp available [Y]" + player.getStamina() + "[BRK]");
            gameUI.prettyPrint("Enter your choice: ");
            String choice = keyboard.nextLine();
            int choiceNum;
            try {
                choiceNum = Integer.parseInt(choice.trim());
            } catch (NumberFormatException e) {
                choiceNum = -1;
            }
            move = gameUI.returnSelectedMoveFormatted(choiceNum);
        }
        return move;
    }
}
