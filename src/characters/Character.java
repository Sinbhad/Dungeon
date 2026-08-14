package characters;

import dungeon.*;
import items.*;
import lib.Node;
import lib.RobertHolder;
import ui.*;

import java.util.Objects;
import java.util.Scanner;

public class Character {
    GameUI gameUI = new GameUI();
    private String name;

    //Player state
    private int attack, weaponAttack, speed, coins, stamina, maxStamina;
    private double health, maxHealth, armorDefense, perkDefense;
    private Item weapon, armor;
    private final RobertHolder<Item> inventory;
    private Node currentRoom;

    //Point tracking
    private int roomsTraversed, enemiesDefeated, potionsConsumed;

    /**
     * Default constructor
     */
    public Character(){
        this.name = "";
        this.attack = 0;
        this.health = 0;
        this.maxHealth = 500;
        this.stamina = 0;
        this.maxStamina = 0;
        this.speed = 0;
        this.coins = 0;
        this.weapon = null;
        this.setWeapon(new Weapon("Fists", null, null, 0, 0));
        this.armor = null;
        this.setArmor(new Armor("Naked", null, null, 0, 0, 0));
        this.currentRoom = null;
        this.inventory = null;
    }

    /**
     * Constructor that allows for an enemy to have certain values preset
     * @param name Name of the character
     * @param attack Attack value of the character
     * @param health Health value of the character\
     * @param stamina Stamina value of the character
     * @param speed Speed value of the character
     * @param coinsHad Coins the character has
     * @param inventory Inventory of the character
     */
    public Character(String name, int attack, double health, int stamina,  int speed, int coinsHad, RobertHolder<Item> inventory){
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.stamina = stamina;
        this.speed = speed;
        this.coins = coinsHad;
        this.weapon = null;
        this.setWeapon(new Weapon("Fists", null, null, 0, 0));
        this.armor = null;
        this.setArmor(new Armor("Naked", null, null, 0, 0, 0));
        this.currentRoom = null;
        this.inventory = inventory;
    }

    /**
     * Constructor that allows for a character to have certain values preset
     * @param name Name of the character
     * @param attack Attack value of the character
     * @param health Health value of the character
     * @param speed Speed value of the character
     * @param coinsHad Coins the character has
     * @param inventory Inventory of the character
     */
    public Character(String name, int attack, double health, double maxHealth, int speed, int coinsHad, RobertHolder<Item> inventory){
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.coins = coinsHad;
        this.weapon = null;
        this.setWeapon(new Weapon("Fists", null, null, 0, 0));
        this.armor = null;
        this.setArmor(new Armor("Naked", null, null, 0, 0, 0));
        this.currentRoom = null;
        this.inventory = inventory;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAttackValue(int attack){
        this.attack = attack;
    }

    public int getAttack(){
        return attack;
    }

    public void setHealth(double health){
        this.health = health;
    }

    public double getHealth(){
        return health;
    }

    public void setSpeedValue(int speed){
        this.speed = speed;
    }

    public int getSpeed(){
        return speed;
    }

    public void setWeapon(Item weapon){
        this.weapon = weapon;
    }

    public Item getWeapon(){
        return weapon;
    }

    public void setCurrentRoom(Node currentRoom){
        this.currentRoom = currentRoom;
    }

    public Node getCurrentRoom(){
        return currentRoom;
    }

    public void setArmor(Item armor){
        this.armor = armor;
    }

    public Item getArmor(){
        return armor;
    }

    public void setArmorDefenseValue(double defense){
        this.armorDefense = defense;
    }

    public double getArmorDefense(){
        return armorDefense;
    }

    public void setPerkDefenseValue(double defense){
        this.perkDefense = defense;
    }

    public double getPerkDefense(){
        return perkDefense;
    }

    public void setTotalDefense(double armorDefense, double perkDefense){
        this.armorDefense = armorDefense;
        this.perkDefense = perkDefense;
    }

    public double getTotalDefense(){
        return armorDefense + perkDefense;
    }

    public void setEnemiesDefeated(int enemiesDefeated){
        this.enemiesDefeated = enemiesDefeated;
    }

    public int getEnemiesDefeated(){
        return enemiesDefeated;
    }

    public void setRoomsTraversed(int roomsTraversed){
        this.roomsTraversed = roomsTraversed;
    }

    public int getRoomsTraversed(){
        return roomsTraversed;
    }

    public void setCoins(int coins){
        this.coins = coins;
    }

    public int getCoins(){
        return coins;
    }

    public void setWeaponAttack(int weaponAttack){
        this.weaponAttack = weaponAttack;
    }

    public int getWeaponAttack(){
        return this.weapon.getAttackValue();
    }

    public void setTotalAttack(int attack, int weaponAttack){
        this.attack = attack;
        this.weaponAttack = weaponAttack;
    }

    public int getTotalAttack(){
        return attack + weaponAttack;
    }    

    public void setPotionsConsumed(int potionsConsumed){
        this.potionsConsumed = potionsConsumed;
    }

    public int getPotionsConsumed(){
        return potionsConsumed;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return stamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    /**
     * Method used throughout the game to display equipment and other stats on each turn
     */
    void displayStats(){
        int healthInt = (int)this.health;
        System.out.println(name);
        System.out.println("Health Points: " + healthInt);
        System.out.println("Total Attack: " + getTotalAttack());
        //if a weapon has not yet been acquired, the game wil build a default placeholder
        String weaponName = (this.getWeapon() != null) ? this.getWeapon().getName() : "Fists";
        System.out.println("Weapon: " + weaponName);
        //if an armor has not yet been acquired, the game wil build a default placeholder
        String armorName = (this.getArmor() != null) ? this.getArmor().getName() : "Naked";
        System.out.println("Armor: " + armorName);
        System.out.println("Coins: " + coins);
    }

    /**
     * Output prompt and logic for player dungeon traversal
     * @param levelCount current level count
     * @param keyboard keyboard input
     */
    public void move(int levelCount, Scanner keyboard) {
        Node currentDungeonRoom = this.getCurrentRoom();
        Room currentRoom = (Room) currentDungeonRoom.getValue();

        System.out.println(currentRoom.getName() + ": Level " + levelCount);
        this.displayStats();

        System.out.println("\n\nEnter I to display inventory");
        System.out.print("Would you like to move left or right? (L/R) : ");
        String choice = keyboard.nextLine();

        if (choice.trim().equalsIgnoreCase("l")) {
            System.out.println("You have moved left\n");
            this.setCurrentRoom(currentDungeonRoom.getLastNode());
            this.setRoomsTraversed(this.getRoomsTraversed() + 1);

        } else if (choice.trim().equalsIgnoreCase("r")) {
            System.out.println("You have moved right\n");
            this.setCurrentRoom(currentDungeonRoom.getNextNode());
            this.setRoomsTraversed(this.getRoomsTraversed() + 1);

        }else if(choice.trim().equalsIgnoreCase("i")){
            System.out.println("-=Inventory=-\n");
            displayInventory(keyboard);
        } else {
            System.out.println("Invalid choice\n");
        }
    }

    /**
     * Chest opening handler. Provides players with the choice to open a chest or not.
     * Updates stats according to item type and attributes.
     * @param keyboard keyboard input
     */
    public void openChest(Scanner keyboard){
        Node currentDungeonRoom = this.getCurrentRoom();
        Room currentRoom = (Room) currentDungeonRoom.getValue();

        //Prompt the user
        System.out.print("\n\nWould you like to open the chest? (Y/N) : ");
        String choice = keyboard.nextLine();

        //Evaluate input
        if(choice.trim().equalsIgnoreCase("y")){
            System.out.println("\nYou have opened the chest");
            System.out.print("You have found a " + currentRoom.getItem().getName() + ", ");
            System.out.println("this " + currentRoom.getItem().getDescription() + "\n\n");

            //Set stats based on item attributes
            if (currentRoom.getItem() != null && currentRoom.getItem().getHpValue() != 0) {
                healingItemHandler(currentRoom, keyboard);
            }
            if (currentRoom.getItem() != null && currentRoom.getItem().getSpeedValue() != 0) {
                speedItemHandler(currentRoom);
            }
            if (currentRoom.getItem() != null && currentRoom.getItem() instanceof Weapon) {
                weaponItemHandler(currentRoom);
            }
            if (currentRoom.getItem() != null && currentRoom.getItem() instanceof Armor) {
                armorItemHandler(currentRoom);
            }
            if (currentRoom.getItem() != null && currentRoom.getItem().getStaminaValue() != 0) {
                staminaItemHandler(currentRoom);
            }
            currentRoom.setItem(null);

        }else if(choice.trim().equalsIgnoreCase("n")){
            System.out.println("You have not opened the chest");
        }else{
            System.out.println("Invalid choice");
            openChest(keyboard);
        }
    }

    /**
     * Helper method for healing items (potions with positive effects).
     * The user may decide to add the item to their inventory rather than using it immediately.
     * @param currentRoom current room the player is in
     * @param keyboard keyboard input
     */
    void healingItemHandler(Room currentRoom, Scanner keyboard){
        double hp = this.getHealth();
        boolean willUsePotion = true;
        int itemHp = currentRoom.getItem().getHpValue();


        if (itemHp < 0) {
            // It's a trap: always apply damage
            this.setHealth(hp + itemHp);
            return;
        }

        System.out.print("Would you like to add this to your inventory? (Y/N) : ");
        String choice2 = keyboard.nextLine();


        //Check to be certain this is a healing item
        if(currentRoom.getItem().getHpValue() > 0){
            willUsePotion = consumePotionChoice(choice2, currentRoom, keyboard);
        }

        //Make sure the player's health value stays below the max
        if (this.getHealth() < this.getMaxHealth() && willUsePotion) {
            // It's a healing item, and player is below max health: apply healing
            this.setHealth(hp + itemHp);

            // Cap health at maxHealth
            if (this.getHealth() > this.getMaxHealth()) {
                this.setHealth(this.getMaxHealth());
            }
            //Add to the kidney stone meter
            this.setPotionsConsumed(this.getPotionsConsumed() + 1);
        } else if(this.getHealth() == this.getMaxHealth() && willUsePotion){
            gameUI.prettyPrint("[BLD][R]You have already reached maximum health, no effect[BRK]\n+" +
                                   "You have not consumed the potion, adding to inventory instead\n");
            Objects.requireNonNull(inventory).addToBucket(currentRoom.getItem());
            currentRoom.setItem(null);
        }


    }

    /**
     * Handles the user's choice of whether to consume a potion or not.
     * @param choice User's choice of whether to consume the potion or not, based on the keyboard input
     * @param currentRoom Current room the player is in
     * @param keyboard Scanner object for keyboard input
     * @return returns a boolean value for use with the healingItemHandler method.
     */
    boolean consumePotionChoice(String choice, Room currentRoom, Scanner keyboard){
        //Prompt user for storage, used now otherwise
        if(choice.trim().equalsIgnoreCase("y")){
            Objects.requireNonNull(inventory).addToBucket(currentRoom.getItem());
            currentRoom.setItem(null);
            return false;
        }else if(choice.trim().equalsIgnoreCase("n")){
            gameUI.prettyPrint("[BLD][R]Invalid choice![BRK]\n");
            healingItemHandler(currentRoom, keyboard);
        }else{
            System.out.println("You chose to drink the potion now\n");
        }
        return true;
    }

    /**
     * Handles increased speed stat based on item attributes
     * @param currentRoom current room the player is in
     */
    void speedItemHandler(Room currentRoom){
        this.setSpeedValue(this.getSpeed() + currentRoom.getItem().getSpeedValue());
    }

    /**
     * Handles increased attack stat based on item attributes
     * @param currentRoom current room the player is in
     */
    void weaponItemHandler(Room currentRoom){
        this.setWeapon(currentRoom.getItem());
        this.setWeaponAttack(currentRoom.getItem().getAttackValue());
        this.setTotalAttack(this.getAttack() , this.getWeaponAttack());
        this.setSpeedValue(this.getSpeed() + currentRoom.getItem().getSpeedValue());
    }

    /**
     * Handles increase defense stat based on item attributes
     * @param currentRoom current room the player is in
     */
    void armorItemHandler(Room currentRoom){
        this.setArmor(currentRoom.getItem());
        this.setArmorDefenseValue(currentRoom.getItem().getDefenseValue() + this.getPerkDefense());

        //Maintain max defense stat
        if(this.getTotalDefense() > 0.8){
            this.setTotalDefense(0.8 , 0.0);
            System.out.println("\nTotal defense value has reached or exceeded the maximum value\n");
            System.out.println("Total defense value has been reduced to max (80%)\n");
        }

    }

    /**
     * Handles increased stamina stat based on item attributes
     * @param currentRoom
     */
    void staminaItemHandler(Room currentRoom){
        this.setMaxStamina(this.getMaxStamina() + currentRoom.getItem().getStaminaValue());
    }

    /**
     * Allows the user to view held items
     * @param keyboard keyboard input
     */
    public void displayInventory(Scanner keyboard){
        int inventorySize = 0;
        if(inventory != null && inventory.size() >= 0){
            inventorySize = inventory.size();
        }else{
            System.out.println("You have no items in your inventory\n");
            return;
        }
        for(int i = 0; i < inventorySize; i++){
            System.out.println((i + 1) + ": "+ inventory.getAtIndex(i).getName());
        }
        System.out.println("Use 0 to exit inventory");
        System.out.print("Which item would you like to use? (0/" + inventory.size() + ") : ");
        String choice = keyboard.nextLine();
        int choiceNum = Integer.parseInt(choice.trim());
        if(choiceNum >= 1 && choiceNum <= inventory.size()){
            useInventory(choiceNum);
        }else if(choiceNum == 0){
            System.out.println("If you didn't want to use an item, why did you open this menu??\n\n");
        }else{
            System.out.println("Invalid choice\n");
            displayInventory(keyboard);
        }
    }

    /**
     * Helper method to update stats based on item used from inventory.
     * To be used with the displayInventory method.
     * @param choice player's choice of item to use, represented by the index of the item in the inventory
     */
    void useInventory(int choice){
        if(inventory == null || inventory.size() == 0){
            System.out.println("You have no items in your inventory\n");
        }else if(choice > inventory.size()){
            System.out.println("Invalid choice\n");
        }else{
            Item itemUsed = inventory.getAtIndex(choice - 1);
            System.out.println("\n\n" + itemUsed.getName() + " was used");
            this.setHealth(this.getHealth() + itemUsed.getHpValue());
            this.setPotionsConsumed(this.getPotionsConsumed() + 1);
            System.out.println(itemUsed.getHpValue() + " health restored\n\n");
            inventory.removeAtIndex(choice);
        }
    }

    public Move chooseMove(Weapon weapon, Scanner keyboard){
        Move move = null;
        if(weapon == null || weapon.getMoves() == null || weapon.getMoves().size() == 0){
            System.out.println("You have no moves to choose from\n");
            move = returnSelectedMoveFormatted(null, 12, keyboard);
        }else{
            System.out.println("Choose a move from the following list:");
            RobertHolder<Move> currentMoves = weapon.getMoves();
            for (int i = 0; i < currentMoves.size(); i++) {
                Move m = currentMoves.getAtIndex(i);
                System.out.println((i + 1) + ": " + m.getMoveName() + " Sp: " + m.getStaminaCost());
            }
            System.out.println("Sp available " + this.getStamina() + "\n");
            System.out.print("Enter your choice: ");
            String choice = keyboard.nextLine();
            int choiceNum;
            try {
                choiceNum = Integer.parseInt(choice.trim());
            } catch (NumberFormatException e) {
                choiceNum = -1;
            }
            move = returnSelectedMoveFormatted(currentMoves, choiceNum, keyboard);
        }
        return move;
    }

    Move returnSelectedMoveFormatted(RobertHolder<Move> moves, int choice, Scanner keyboard) {
        //Default move if no moves are available
        Move punch = new Move("Punch", "Bam! you hit them right in the face ", 10, 0);

        //Check if moves are null (handling the "no moves" case)
        if (moves == null || moves.size() == 0) {
            if (choice == 12) {
                System.out.println("Resorting to fists\n");
                return punch;
            }
            //bs move to fill the void
            System.out.println("No moves available. Resorting to fists\n");
            return punch;
        }

        // error handling
        if (choice < 1 || choice > moves.size()) {
            System.out.println("Invalid move selection.");
            return chooseMove((Weapon) this.getWeapon(), keyboard);
        }

        //edge case mainly for testing
        Move selectedMove = moves.getAtIndex(choice - 1);
        if (selectedMove == null) {
            System.out.println("Resorting to fists\n");
            return punch;
        }

        return selectedMove;
    }
}
