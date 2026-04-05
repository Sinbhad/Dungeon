public class Move{
    private String moveName, description;
    private int damage;

    public Move(){
        this.moveName = "";
        this.description = "";
        this.damage = 0;
    }


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