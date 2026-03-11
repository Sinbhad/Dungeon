public class Character {
    private String name;
    private int attack;
    private int health;
    private int speed;
    private String equipment;


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

    public void setEquipment(String equipment){
        this.equipment = equipment;
    }

    public String getEquipment(){
        return equipment;
    }

    public void displayStats(){
        System.out.println(name);
        System.out.println("Health Points " + health);
        System.out.println("Attack Points " + attack);
        System.out.println("Equipment " + equipment);
    }
}
