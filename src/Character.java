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
            System.out.println("You have found a " + currentRoom.getItem().getName());
            System.out.println("this " + currentRoom.getItem().getDescription() + "\n\n");

            if(currentRoom.getItem().getAttackValue() != 0){
                int itemAttack = currentRoom.getItem().getAttackValue();
                this.setAttackValue(attack + itemAttack);
            }
            if(currentRoom.getItem().getHpValue() != 0){
                int itemHp = currentRoom.getItem().getHpValue();
                if(this.getHealth() < 500){
                    if(this.getHealth() > 500){
                        this.setHealthValue(500);
                    }
                }else if(this.getHealth() == 500 && itemHp < 0){
                    this.setHealthValue(hp + itemHp);
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
                this.setAttackValue(40 + currentRoom.getItem().getAttackValue());
            }

            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Armor")){
                this.setArmor(currentRoom.getItem());
                this.setDefenseValue(currentRoom.getItem().getDefenseValue());
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
