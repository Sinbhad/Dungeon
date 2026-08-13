package dungeon;
import characters.Character;
import characters.Enemy;
import items.Item;

public class Room {
    private final String name;
    private Item item;
    private Character playerCharacter;
    private Enemy enemyCharacter;
    private boolean isExit;

    /**
     * Default constructor for room
     */
    public Room (){
        name = "";
        item = null;
        playerCharacter = null;
        enemyCharacter = null;
        isExit = false;
    }

    /**
     * Constructor for room
     * @param name name of room
     * @param item item in room
     * @param playerCharacter player character in the room
     * @param enemyCharacter enemy character in room
     * @param isExit whether room is exit
     */
    public Room(String name, Item item, Character playerCharacter, Enemy enemyCharacter, boolean isExit){
        this.name = name;
        this.item = item;
        this.playerCharacter = playerCharacter;
        this.enemyCharacter = enemyCharacter;
        this.isExit = isExit;
    }

    /**
     * Sets certain values for a room, used during floor generation
     * @param item item in room (chest)
     * @param playerCharacter player character in the room
     * @param enemyCharacter enemy character in room
     * @param isExit whether room is exit
     */
    public void setCertain(Item item, Character playerCharacter, Enemy enemyCharacter, boolean isExit){
        setItem(item);
        setEnemyCharacter(enemyCharacter);
        setPlayerCharacter(playerCharacter);
        setIsExit(isExit);
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
