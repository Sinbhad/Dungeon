package Dungeon;
import Characters.Enemy;
import Items.Item;

public class Room {
    private String name;
    private Item item;
    private Character playerCharacter;
    private Enemy enemyCharacter;
    private boolean isExit;

    public Room(String name, Item item, Character playerCharacter, Enemy enemyCharacter, boolean isExit){
        this.name = name;
        this.item = item;
        this.playerCharacter = playerCharacter;
        this.enemyCharacter = enemyCharacter;
        this.isExit = isExit;
    }

    public void setCertain(Item item, Character playerCharacter, Enemy enemyCharacter, boolean isExit){
        setItem(item);
        setEnemyCharacter(enemyCharacter);
        setPlayerCharacter(playerCharacter);
        setIsExit(isExit);
    }

    public void setNames(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public Item getItem(){
        return item;
    }

    public void setPlayerCharacter(Character playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public void setEnemyCharacter(Enemy enemyCharacter) {
        this.enemyCharacter = enemyCharacter;
    }

    public Enemy getEnemyCharacter() {
        return enemyCharacter;
    }

    public void setIsExit(Boolean isExit){
        this.isExit = isExit;
    }

    public boolean getIsExit(){
        return isExit;
    }

}
