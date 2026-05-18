package Characters;

import Dungeon.*;
import Items.*;
import lib.Node;
import lib.RobertHolder;
import java.util.Scanner;

public class Character {
    private String name;
    private int attack, weaponAttack, totalAttack, speed, roomsTraversed, enemiesDefeated, coins, potionsConsumed;
    private double health, armorDefense, perkDefense, totalDefense;
    private Item weapon, armor;
    private RobertHolder<Item> inventory;
    private Node currentRoom;

    /**
     * Default constructor
     */
    public Character(){
        this.name = "";
        this.attack = 0;
        this.health = 0;
        this.speed = 0;
        this.coins = 0;
        this.weapon = null;
        this.armor = null;
        this.currentRoom = null;
        this.inventory = null;
    }

    /**
     * Constructor that allows for a character to have certain values preset
     * @param name
     * @param attack
     * @param health
     * @param speed
     * @param coinsHad
     * @param inventory
     */
    public Character(String name, int attack, double health, int speed, int coinsHad, RobertHolder<Item> inventory){
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.speed = speed;
        this.coins = coinsHad;
        this.weapon = null;
        this.armor = null;
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

    public void setHealthValue(double health){
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
        this.totalDefense = armorDefense + perkDefense;
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
        this.weaponAttack = this.weapon.getAttackValue();
    }

    public int getWeaponAttack(){
        return this.weapon.getAttackValue();
    }

    public void setTotalAttack(int attack, int weaponAttack){
        this.totalAttack = attack + weaponAttack;
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

    /**
     * Method used throughout the game to display equipment and other stats on each turn
     */
    void displayStats(){
        int healthInt = (int)this.health;
        System.out.println(name);
        System.out.println("Health Points: " + healthInt);
        System.out.println("Attack Points: " + attack);
        System.out.println("Weapon: " + weapon.getName());
        System.out.println("Armor: " + armor.getName());
        System.out.println("Coins: " + coins);
    }

    /**
     * Output prompt and logic for player dungeon traversal
     * @param levelCount
     * @param keyboard
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
     * @param keyboard
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
            if(currentRoom.getItem().getHpValue() != 0){
                healingItemHandler(currentRoom, keyboard);
            }
            if(currentRoom.getItem().getSpeedValue() != 0){
                speedItemHandler(currentRoom);
            }
            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Weapon")){
                weaponItemHandler(currentRoom);
            }
            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Armor")){
                armorItemHandler(currentRoom);
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
     * @param currentRoom
     * @param keyboard
     */
    void healingItemHandler(Room currentRoom, Scanner keyboard){
        double hp = this.getHealth();

        //Check to be certain this is a healing item
        if(currentRoom.getItem().getHpValue() > 0){

            //Prompt user for storage, used now otherwise
            System.out.print("Would you like to add this to your inventory? (Y/N) : ");
            String choice2 = keyboard.nextLine();
            if(choice2.trim().equalsIgnoreCase("y")){
                inventory.addToBucket(currentRoom.getItem());
                currentRoom.setItem(null);
                return;
            }else if(!choice2.trim().equalsIgnoreCase("n")){
                System.out.println("Invalid choice\n");
            }else{
                System.out.println("You chose to drink the potion now\n");
            }
        }

        //Make sure the players health value stays below the max
        int itemHp = currentRoom.getItem().getHpValue();
        if(this.getHealth() < 500){
            this.setHealthValue(hp + itemHp);
            if(this.getHealth() > 500){
                this.setHealthValue(500);
            }
        }else if(this.getHealth() == 500 && itemHp < 0){
            this.setHealthValue(hp + itemHp);
        }else{
            System.out.println("You have already reached maximum health, no effect\n");
        }

        //Add to the kidney stone meter
        this.setPotionsConsumed(this.getPotionsConsumed() + 1);
    }

    /**
     * Handles increased speed stat based on item attributes
     * @param currentRoom
     */
    void speedItemHandler(Room currentRoom){
        int itemSpeed = currentRoom.getItem().getSpeedValue();
        this.setSpeedValue(this.getSpeed() + itemSpeed);
    }

    /**
     * Handles increased attack stat based on item attributes
     * @param currentRoom
     */
    void weaponItemHandler(Room currentRoom){
        this.setWeapon(currentRoom.getItem());
        this.setWeaponAttack(currentRoom.getItem().getAttackValue());
        this.setTotalAttack(this.getAttack() , this.getWeaponAttack());
    }

    /**
     * Handles increase defense stat based on item attributes
     * @param currentRoom
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
     * Allows the user to view held items
     * @param keyboard
     */
    public void displayInventory(Scanner keyboard){
        for(int i = 0; i < inventory.size(); i++){
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
     * @param choice
     */
    void useInventory(int choice){
        Item itemUsed = inventory.getAtIndex(choice - 1);
        System.out.println("\n\n" + itemUsed.getName() + " was used");
        this.setHealthValue(this.getHealth() + itemUsed.getHpValue());
        this.setPotionsConsumed(this.getPotionsConsumed() + 1);
        System.out.println(itemUsed.getHpValue() + " health restored\n\n");
        inventory.removeAtIndex(choice);
    }
}
