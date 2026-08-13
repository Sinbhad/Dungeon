package dungeon;

public class Perks {
    String name, type, description;
    int cost;
    double value;

    /**
     * default constructor for perks
     */
    public Perks(){
        this.name = "";
        this.type = "";
        this.description = "";
        this.value = 0;
        cost = 0;
    }

    /**
     * constructor for perks
     * @param name name of perk
     * @param type type of attribute that perk increases
     * @param description description of perk
     * @param value value of perk in terms of how much it increases the player's stats
     * @param cost cost of perk
     */
    public Perks(String name, String type, String description, double value, int cost){
        this.name = name;
        this.type = type;
        this.description = description;
        this.value = value;
        this.cost = cost;
    }

    public String getPerkName(){
        return name;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    public int getCost(){
        return cost;
    }
}
