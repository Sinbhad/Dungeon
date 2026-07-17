package Items;

import lib.RobertHolder;

public class Item{
    String name;
    String type;
    String description;
    int hpValue, attackValue, speedValue;
    double defenseValue;
    RobertHolder moves = new RobertHolder();

    /**
     * Default item constructor
     */
    public Item(){
        this.name = "";
        this.type = "";
        this.description = "";
        this.hpValue = 0;
        this.attackValue = 0;
        this.speedValue = 0;
        this.defenseValue = 0;
    }

    /**
     * Constructor for item of multiple types
     * @param name name of item
     * @param type type of item
     * @param description description of item
     * @param hpValue health value of item
     * @param attackValue attack value of an item
     * @param speedValue speed value of item
     * @param defenseValue defense value of item
     */
    public Item(String name, String type, String description, int hpValue, int attackValue, int speedValue, double defenseValue){
        this.name = name;
        this.type = type;
        this.description = description;
        this.hpValue = hpValue;
        this.attackValue = attackValue;
        this.speedValue = speedValue;
        this.defenseValue = defenseValue;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setAttackValue(int attackValue){
        this.attackValue = attackValue;
    }

    public int getAttackValue(){
        return attackValue;
    }

    public void setHpValue(int hpValue){this.hpValue = hpValue;}

    public int getHpValue(){
        return hpValue;
    }

    public void setSpeedValue(int speedValue){
        this.speedValue = speedValue;
    }

    public int getSpeedValue(){
        return speedValue;
    }

    public void setDefenseValue(double defenseValue){
        this.defenseValue = defenseValue;
    }

    public double getDefenseValue(){
        return defenseValue;
    }

    public RobertHolder getMoves(){
        return moves;
    }
}
