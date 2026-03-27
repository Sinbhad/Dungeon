import lib.Node;
import java.util.Scanner;

public class Character {
    private String name;
    private int attack;
    private double health;
    private int speed;
    private double defense;
    private Item weapon, armor;
    private Node currentRoom;


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

    public void setDefenseValue(double defense){
        this.defense = defense;
    }

    public double getDefense(){
        return defense;
    }

    public void displayStats(){
        System.out.println(name);
        System.out.println("Health Points " + health);
        System.out.println("Attack Points " + attack);
        System.out.println("Weapon: " + weapon.getName());
        System.out.println("Armor: " + armor.getName());
    }

    public void move(int levelCount, Scanner keyboard) {
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
    }
}
