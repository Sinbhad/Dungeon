package Characters;

public class Move{
    private String moveName, description;
    private int damage;

    /**
     * Default constructor for enemy moves
     */
    public Move(){
        this.moveName = "";
        this.description = "";
        this.damage = 0;
    }

    /**
     * Constructor that handles creation of moves for enemies with an actual definition
     * @param moveName
     * @param description
     * @param damage
     */
    public Move(String moveName, String description, int damage){
        this.moveName = moveName;
        this.description = description;
        this.damage = damage;
    }

    public void setMoveName(String moveName){
        this.moveName = moveName;
    }

    public String getMoveName(){
        return moveName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getDamage(){
        return damage;
    }
}