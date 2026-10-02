package dungeon;

import characters.Enemy;
import characters.Player;
import items.Item;
import items.Weapon;
import lib.CustomArrayList;
import lib.CustomCircularlyLinkedList;
import lib.Node;

public class GameState {

    private final Player player;
    private final CustomCircularlyLinkedList<Room> dungeon;
    private final CustomArrayList<Enemy> enemyRoster;
    private final CustomArrayList<Weapon> weaponRoster;

    private int levelCount, enemyCount, weaponCount, roomCount;
    private boolean gameOver;

    public GameState() {
        player = new Player();
        dungeon = new CustomCircularlyLinkedList<>();
        enemyRoster = new CustomArrayList<>();
        weaponRoster = new CustomArrayList<>();

        levelCount = 1;
        roomCount = 7;
        gameOver = false;
    }

    public GameState(Player player, CustomCircularlyLinkedList<Room> dungeon, CustomArrayList<Enemy> enemyRoster, CustomArrayList<Weapon> weaponRoster, int levelCount, int roomCount) {
        this.player = player;
        this.dungeon = dungeon;
        this.enemyRoster = enemyRoster;
        this.weaponRoster = weaponRoster;
        this.levelCount = levelCount;
        this.roomCount = roomCount;
        this.gameOver = false;
    }

    public Player getPlayer() {
        return player;
    }

    public CustomCircularlyLinkedList<Room> getDungeon() {
        return dungeon;
    }

    public Node<Room> getCurrentRoomNode() {
        return player.getCurrentRoom();
    }

    public void setCurrentRoom(Node<Room> room) {
        player.setCurrentRoom(room);
    }

    public Room getCurrentRoom() {
        Node<Room> node = player.getCurrentRoom();
        if (node == null) {
            throw new IllegalStateException(
                    "Player does not have a current room"
            );
        }
        return node.getValue();
    }

    public void setLevelCount(int levelCount) {
        this.levelCount = levelCount;
    }

    public int getLevelCount() {
        return levelCount;
    }

    public void advanceLevel() {
        levelCount++;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void addEnemy(Enemy enemy){
        enemyRoster.add(enemy);
        enemyCount++;
    }

    public Enemy getCurrentRoomEnemy(){
        return getCurrentRoom().getEnemyCharacter();
    }

    public boolean isEnemyInRoom(){
        return getCurrentRoomEnemy() != null;
    }

    public int getEnemyCount(){
        return enemyCount;
    }

    public CustomArrayList<Enemy> getEnemyRoster() {
        return enemyRoster;
    }

    public void clearEnemyRoster(){
        enemyRoster.clear();
        enemyCount = 0;
    }

    public Item getCurrentRoomItem(){
        return getCurrentRoom().getItem();
    }

    public void addWeapon(Weapon weapon){
        weaponRoster.add(weapon);
        weaponCount++;
    }

    public int getWeaponCount(){
        return weaponCount;
    }

    public CustomArrayList<Weapon> getWeaponRoster(){
        return weaponRoster;
    }

    public void clearWeaponRoster(){
        weaponRoster.clear();
        weaponCount = 0;
    }
}