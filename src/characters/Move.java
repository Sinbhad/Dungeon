package characters;

public class Move{
    private String moveName, description;
    private int damage, staminaCost;

    /**
     * Default constructor for enemy moves
     */
    public Move(){
        this.moveName = "";
        this.description = "";
        this.damage = 0;
        this.staminaCost = 0;
    }

    /**
     * Constructor that handles creation of moves for weapon-based player moves with an actual definition
     * @param moveName name of move
     * @param description description of the move (what it does, possible effects, etc.)
     * @param damage damage dealt by move
     * @param staminaCost energy cost of move
     */
    public Move(String moveName, String description, int damage, int staminaCost){
        this.moveName = moveName;
        this.description = description;
        this.damage = damage;
        this.staminaCost = staminaCost;
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

    public void setStaminaCost(int staminaCost){
        this.staminaCost = staminaCost;
    }

    public int getStaminaCost(){
        return staminaCost;
    }
}