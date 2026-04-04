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


    void displayStats(){
        System.out.println(name);
        System.out.println("Health Points: " + health);
        System.out.println("Attack Points: " + attack);
        System.out.println("Weapon: " + weapon.getName());
        System.out.println("Armor: " + armor.getName());
        System.out.println("Coins: " + coins);
    }

    void move(int levelCount, Scanner keyboard) {
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

    void openChest(Scanner keyboard){
        Node currentDungeonRoom = this.getCurrentRoom();
        Room currentRoom = (Room) currentDungeonRoom.getValue();
        System.out.print("\n\nWould you like to open the chest? (Y/N) : ");
        String choice = keyboard.nextLine();

        if(choice.trim().equals("y")){
            double hp = this.getHealth();
            int speed = this.getSpeed();
            int attack = this.getAttack();

            System.out.println("\nYou have opened the chest");
            System.out.print("You have found a " + currentRoom.getItem().getName() + ", ");
            System.out.println("this " + currentRoom.getItem().getDescription() + "\n\n");


            if(currentRoom.getItem().getAttackValue() != 0){
                int itemAttack = currentRoom.getItem().getAttackValue();
                this.setAttackValue(attack + itemAttack);
            }
            if(currentRoom.getItem().getHpValue() != 0){
                if(currentRoom.getItem().getHpValue() > 0){
                    System.out.print("Would you like to add this to your inventory? (Y/N) : ");
                    String choice2 = keyboard.nextLine();
                    if(choice2.trim().equalsIgnoreCase("y")){
                        inventory.addToBucket(currentRoom.getItem());
                    }else if(!choice2.trim().equalsIgnoreCase("n")){
                        System.out.println("Invalid choice\n");
                    }else{
                        System.out.println("You chose to drink the potion now\n");
                    }
                }
                int itemHp = currentRoom.getItem().getHpValue();
                if(this.getHealth() < 500){
                    this.setHealthValue(hp + itemHp);
                    if(this.getHealth() > 500){
                        this.setHealthValue(500);
                    }
                    this.setPotionsConsumed(this.getPotionsConsumed() + 1);
                }else if(this.getHealth() == 500 && itemHp < 0){
                    this.setHealthValue(hp + itemHp);
                    this.setPotionsConsumed(this.getPotionsConsumed() + 1);
                }else{
                    System.out.println("You have already reached maximum health, no effect\n");
                }
            }
            if(currentRoom.getItem().getSpeedValue() != 0){
                int itemSpeed = currentRoom.getItem().getSpeedValue();
                this.setSpeedValue(speed + itemSpeed);
            }
            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Weapon")){
                this.setWeapon(currentRoom.getItem());
                this.setWeaponAttack(currentRoom.getItem().getAttackValue());
                this.setTotalAttack(this.getAttack() , this.getWeaponAttack());
            }

            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Armor")){
                this.setArmor(currentRoom.getItem());
                this.setArmorDefenseValue(currentRoom.getItem().getDefenseValue() + this.getPerkDefense());
                if(this.getTotalDefense() > 0.8){
                    this.setTotalDefense(0.8 , 0.0);
                    System.out.println("\nTotal defense value has reached or exceeded the maximum value\n");
                    System.out.println("Total defense value has been reduced to max (80%)\n");
                }
            }

            currentRoom.setItem(null);

        }else if(choice.trim().equalsIgnoreCase("n")){
            System.out.println("You have not opened the chest");
        }else{
            System.out.println("Invalid choice");
            openChest(keyboard);
        }
    }

    void displayInventory(Scanner keyboard){
        for(int i = 0; i < inventory.size(); i++){
            System.out.println((i + 1) + ": "+ inventory.getValueAtIndex(i).getName());
        }
        System.out.print("Would you like to use any of your items? (1/" + inventory.size() + ") : ");
        int choice = keyboard.nextInt();
        if(choice >= 1 && choice <= inventory.size()){
            useInventory(choice);
        }else{
            System.out.println("Invalid choice\n");
            displayInventory(keyboard);
        }
    }

    void useInventory(int choice){
        Item itemUsed = inventory.getValueAtIndex(choice - 1);
        System.out.println("\n\n" + itemUsed.getName() + " was used");
        this.setHealthValue(this.getHealth() + itemUsed.getHpValue());
        this.setPotionsConsumed(this.getPotionsConsumed() + 1);
        System.out.println(itemUsed.getHpValue() + " health restored\n\n");
        inventory.removeAtIndex(choice);
    }
}
