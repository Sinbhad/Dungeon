package Characters;

public class Move{
    private String moveName, description;
    private int damage, energyCost;

    /**
     * Default constructor for enemy moves
     */
    public Move(){
        this.moveName = "";
        this.description = "";
        this.damage = 0;
        this.energyCost = 0;
    }

    /**
     * Constructor that handles creation of moves for weapon-based player moves with an actual definition
     * @param moveName name of move
     * @param description description of the move (what it does, possible effects, etc.)
     * @param damage damage dealt by move
     * @param energyCost energy cost of move
     */
    public Move(String moveName, String description, int damage, int energyCost){
        this.moveName = moveName;
        this.description = description;
        this.damage = damage;
        this.energyCost = energyCost;
    }

    /**
     * Constructor that handles creation of moves for enemy moves with an actual definition
     * @param moveName name of move
     * @param description description of the move (what it does, possible effects, etc.)
     * @param damage damage dealt by move
     */
    public Move(String moveName, String description, int damage){
        this(moveName, description, damage, 0);
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

    public void setEnergyCost(int energyCost){
        this.energyCost = energyCost;
    }

    public int getEnergyCost(){
        return energyCost;
    }
}