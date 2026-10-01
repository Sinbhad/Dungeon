package characters;

import dungeon.GameState;
import dungeon.Room;
import items.Item;
import lib.CustomArrayList;
import ui.GameUI;

import java.util.Scanner;

public class InventoryManager {
    private final GameState gameState;
    private final GameUI gameUI;
    private final Scanner keyboard = new Scanner(System.in);

    public InventoryManager(){
        this.gameState = new GameState();
        this.gameUI = new GameUI();
    }

    public InventoryManager(GameState gameState){
        this.gameState = gameState;
        this.gameUI = new GameUI();
    }

    /**
     * Helper method for healing items (potions with positive effects).
     * The user may decide to add the item to their inventory rather than using it immediately.
     */
    void healingItemHandler(){
        Player player = gameState.getPlayer();
        double hp = player.getHealth();
        boolean willUsePotion = true;
        Room currentRoom = gameState.getCurrentRoom();
        int itemHp = currentRoom.getItem().getHpValue();
        int itemSpeed = currentRoom.getItem().getSpeedValue();


        if (itemHp < 0) {
            // It's a trap: always apply damage
            player.setHealth(hp + itemHp);
            return;
        }

        gameUI.prettyPrint("Would you like to add this to your inventory? [C](Y/N)[BRK]: ");
        String choice = keyboard.nextLine();
        willUsePotion = consumePotionChoice(choice);


        //Make sure the player's health value stays below the max
        if (willUsePotion && player.getHealth() < player.getMaxHealth()) {
            String healthString = String.valueOf(player.getHealth());
            // It's a healing item, and player is below max health: apply healing
            player.setHealth(hp + itemHp);

            // Cap health at maxHealth
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }

            //Add to the kidney stone meter
            player.increasePotionsConsumed();
            if(itemSpeed > 0){speedItemHandler();}
        } else if(player.getHealth() == player.getMaxHealth() && willUsePotion){
            gameUI.prettyPrintln("[BLD][R]You have already reached maximum health, no effect[BRK]\n+" +
                    "You have not consumed the potion, adding to inventory instead\n");
            player.getInventory().add(currentRoom.getItem());
            currentRoom.setItem(null);
        }
    }

    /**
     * Handles the user's choice of whether to consume a potion or not.
     * @param choice User's choice of whether to consume the potion or not, based on the keyboard input
     */
    boolean consumePotionChoice(String choice){
        //Prompt user for storage, used now otherwise
        if(choice.trim().equalsIgnoreCase("y")){
            gameState.getPlayer().getInventory().add(gameState.getCurrentRoom().getItem());
            gameState.getCurrentRoom().setItem(null);
            return false;
        }else if(choice.trim().equalsIgnoreCase("n")){
            gameUI.prettyPrintln("[G]You chose to drink the potion now[BRK]\n");
        }else{
            gameUI.prettyPrintln("[INVALID]\n");
            healingItemHandler();
        }
        return true;
    }

    /**
     * Handles increased speed stat based on item attributes
     */
    void speedItemHandler(){
        Player player = gameState.getPlayer();
        player.setSpeedValue(player.getSpeed() + gameState.getCurrentRoom().getItem().getSpeedValue());
    }

    /**
     * Handles increased attack stat based on item attributes
     */
    void weaponItemHandler(){
        Room currentRoom = gameState.getCurrentRoom();
        Player player = gameState.getPlayer();
        player.setWeapon(currentRoom.getWeapon());
        player.setWeaponAttack(currentRoom.getItem().getAttackValue());
        player.setTotalAttack(player.getAttack(), player.getWeaponAttack());
        player.setSpeedValue(player.getSpeed() + currentRoom.getItem().getSpeedValue());
    }

    /**
     * Handles increased stamina stat based on item attributes
     */
    void staminaItemHandler(){
        gameState.getPlayer().setMaxStamina(gameState.getPlayer().getMaxStamina() + gameState.getCurrentRoom().getItem().getStaminaValue());
    }

    /**
     * Handles increase defense stat based on item attributes
     */
    void armorItemHandler(){
        Player player = gameState.getPlayer();
        Room currentRoom = gameState.getCurrentRoom();

        player.setArmor(currentRoom.getItem());
        player.setArmorDefenseValue(currentRoom.getItem().getDefenseValue() + player.getPerkDefense());

        //Maintain max defense stat
        if(player.getTotalDefense() > 0.8){
            player.setTotalDefense(0.8 , 0.0);
            gameUI.prettyPrintln("\n[BLD][R]Total defense value has reached or exceeded the maximum value[BRK]\n\n" +
                    "Total defense value has been reduced to max (80%)\n");
        }

    }

    /**
     * Allows the user to view held items
     */
    public void displayInventory(){
        CustomArrayList<Item> inventory = gameState.getPlayer().getInventory();
        int inventorySize = 0;
        if(inventory != null && inventory.size() >= 0){
            inventorySize = inventory.size();
        }else{
            gameUI.prettyPrintln("[BLD][R]You have no items in your inventory![BRK]\n");
            return;
        }
        for(int i = 0; i < inventorySize; i++){
            gameUI.prettyPrintln((i + 1) + ": "+ inventory.getAtIndex(i).getName());
        }
        gameUI.prettyPrintln("Use [C]0[BRK] to exit inventory");
        gameUI.prettyPrint("Which item would you like to use? [C](0/" + inventory.size() + ")[BRK] : ");
        String choice = keyboard.nextLine();
        int choiceNum = Integer.parseInt(choice.trim());
        if(choiceNum >= 1 && choiceNum <= inventory.size()){
            useInventory(choiceNum);
        }else if(choiceNum == 0){
            gameUI.prettyPrintln("[BLD][R]If you didn't want to use an item, why did you open this menu??[BRK]\n\n");
        }else{
            gameUI.prettyPrintln("[INVALID]");
            displayInventory();
        }
    }

    /**
     * Helper method to update stats based on item used from inventory.
     * To be used with the displayInventory method.
     * @param choice player's choice of item to use, represented by the index of the item in the inventory
     */
    void useInventory(int choice){
        Player player = gameState.getPlayer();
        CustomArrayList<Item> inventory = player.getInventory();

        if(inventory == null || inventory.size() == 0){
            gameUI.prettyPrintln("[EMPTYINV]");
        }else if(choice > inventory.size()){
            gameUI.prettyPrintln("[INVALID]");
        }else{
            Item itemUsed = inventory.getAtIndex(choice - 1);
            gameUI.prettyPrintln("\n\n[C]" + itemUsed.getName() + "[BRK] was used");
            player.increaseHealth(itemUsed.getHpValue());
            player.increasePotionsConsumed();
            gameUI.prettyPrintln("[G]" + itemUsed.getHpValue() + "[BRK] health restored\n\n");
            inventory.removeAtIndex(choice);
        }
    }
}
