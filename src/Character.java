import lib.Node;

public class Character {
    private String name;
    private int attack;
    private int health;
    private int speed;
    private String weapon;
    private String armor;
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

    public void setHealthValue(int health){
        this.health = health;
    }

    public int getHealth(){
        return health;
    }

    public void setSpeedValue(int speed){
        this.speed = speed;
    }

    public int getSpeed(){
        return speed;
    }

    public void setWeapon(String equipment){
        this.weapon = equipment;
    }

    public String getWeapon(){
        return weapon;
    }

    public void setCurrentRoom(Node currentRoom){
        this.currentRoom = currentRoom;
    }

    public Node getCurrentRoom(){
        return currentRoom;
    }

    public void setArmor(String armor){
        this.armor = armor;
    }

    public String getArmor(){
        return armor;
    }

    public void displayStats(){
        System.out.println(name);
        System.out.println("Health Points " + health);
        System.out.println("Attack Points " + attack);
        System.out.println("Weapon: " + weapon);
        System.out.println("Armor: " + armor);
    }
}
