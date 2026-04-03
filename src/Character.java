import lib.Node;
import java.util.Scanner;

public class Character {
    private String name;
    private int attack, weaponAttack, totalAttack, speed, roomsTraversed, enemiesDefeated, coins, potionsConsumed;
    private double health, armorDefense, perkDefense, totalDefense;
    private Item weapon, armor;
    private Node currentRoom;

    public character(){
        this.name = "";
        this.attack = 0;
        this.health = 0;
        this.speed = 0;
        this.coins = 0;
        this.weapon = null;
        this.armor = null;
        this.currentRoom = null;
    }

    public Character(String name, int attack, double health, int speed, int coinsHad){
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.speed = speed;
        this.coins = coinsHad;
        this.weapon = null;
        this.armor = null;
        this.currentRoom = null;
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

    public void setTotalDefense(double totalDefense){
        this.totalDefense = totalDefense;
    }

    public double getTotalDefense(){
        return totalDefense;
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

    public void setWeaponAttack(weaponAttack){
        this.weaponAttack = this.weapon.getAttackValue();
    }

    public int getWeaponAttackValue(){
        return weaponAttack;
    }

    public void setTotalAttack(int toatalDefense){
        this.totalDefense = totalDefense;
    }

    public int getTotalAttack(){
        return totalAttack;
    }    

    public void setPotionsConsumed(int potionsConsumed){
        this.potionsConsumed = potionsConsumed;
    }

    public int getPotionsConsumed(){
        return potionsConsumed;
    }

    public void displayStats(){
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

        System.out.print("\n\nWould you like to move left or right? (l/r) : ");
        String choice = keyboard.nextLine();

        if (choice.trim().equalsIgnoreCase("l")) {
            System.out.println("You have moved left\n");
            this.setCurrentRoom(currentDungeonRoom.getLastNode());

        } else if (choice.trim().equalsIgnoreCase("r")) {
            System.out.println("You have moved right\n");
            this.setCurrentRoom(currentDungeonRoom.getNextNode());

        } else {
            System.out.println("Invalid choice\n");
        }

        this.setRoomsTraversed(this.getRoomsTraversed() + 1);
    }

    void openChest(Scanner keyboard){
        Node currentDungeonRoom = this.getCurrentRoom();
        Room currentRoom = (Room) currentDungeonRoom.getValue();
        System.out.print("\n\nWould you like to open the chest? (y/n) : ");
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
                this.setWeaponAttack(currenRoom.getItem.getAttackValue());
                this.setTotalAttack(attackValue + weaponAttack);
            }

            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Armor")){
                this.setArmor(currentRoom.getItem());
                this.setArmorDefenseValue(currentRoom.getItem().getDefenseValue() + this.getPerkDefense());
                if(this.getTotalDefense() > 0.8){
                    this.setTotalDefense(0.8);
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
}
