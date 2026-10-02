package characters;

import dungeon.*;
import items.*;
import lib.Node;
import lib.CustomArrayList;
import ui.*;

import java.util.Objects;
import java.util.Scanner;

public class Character {
    private String name;

    //Player state
    private int attack, weaponAttack, speed, coins, stamina, maxStamina;
    private double health, maxHealth, armorDefense, perkDefense;
    private Weapon weapon;
    private Item armor;
    private final CustomArrayList<Item> inventory;
    private Node<Room> currentRoom;

    //Point tracking
    private int roomsTraversed, enemiesDefeated, potionsConsumed;

    /**
     * Default constructor
     */
    public Character(){
        this.name = "";
        this.attack = 0;
        this.health = 0;
        this.maxHealth = 500;
        this.stamina = 0;
        this.maxStamina = 0;
        this.speed = 0;
        this.coins = 0;
        this.weapon = null;
        this.setWeapon(new Weapon("Fists", null, null, 0, 0));
        this.armor = null;
        this.setArmor(new Armor("Naked", null, null, 0, 0, 0));
        this.currentRoom = null;
        this.inventory = null;
    }

    /**
     * Constructor that allows for an enemy to have certain values preset
     * @param name Name of the character
     * @param attack Attack value of the character
     * @param health Health value of the character\
     * @param stamina Stamina value of the character
     * @param speed Speed value of the character
     * @param coinsHad Coins the character has
     * @param inventory Inventory of the character
     */
    public Character(String name, int attack, double health, int stamina,  int speed, int coinsHad, CustomArrayList<Item> inventory){
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.stamina = stamina;
        this.speed = speed;
        this.coins = coinsHad;
        this.weapon = null;
        this.setWeapon(new Weapon("Fists", null, null, 0, 0));
        this.armor = null;
        this.setArmor(new Armor("Naked", null, null, 0, 0, 0));
        this.currentRoom = null;
        this.inventory = inventory;
    }

    /**
     * Constructor that allows for a character to have certain values preset
     * @param name Name of the character
     * @param attack Attack value of the character
     * @param health Health value of the character
     * @param speed Speed value of the character
     * @param coinsHad Coins the character has
     * @param inventory Inventory of the character
     */
    public Character(String name, int attack, double health, double maxHealth, int stamina , int speed, int coinsHad, CustomArrayList<Item> inventory){
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.maxHealth = maxHealth;
        this.stamina = stamina;
        this.speed = speed;
        this.coins = coinsHad;
        this.weapon = null;
        this.setWeapon(new Weapon("Fists", null, null, 0, 0));
        this.armor = null;
        this.setArmor(new Armor("Naked", null, null, 0, 0, 0));
        this.currentRoom = null;
        this.inventory = inventory;
    }

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

    public void setHealth(double health){
        this.health = health;
    }

    public void increaseHealth(double healthIncrease){
        this.health += healthIncrease;
    }

    public void decreaseHealth(double healthDecrease){
        this.health -= healthDecrease;
        if(this.health < 0) health = 0;
    }

    public double getHealth(){
        return health;
    }

    public void setSpeedValue(int speed){
        this.speed = speed;
    }

    public void decreaseSpeed(int speedDecrease){
        this.speed -= speedDecrease;
        if(this.speed < 0) this.speed = 0;
    }

    public int getSpeed(){
        return speed;
    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public void setCurrentRoom(Node<Room> currentRoom){
        this.currentRoom = currentRoom;
    }

    public Node<Room> getCurrentRoom(){
        return currentRoom;
    }

    public void setArmor(Item armor){
        this.armor = armor;
    }

    public Item getArmor(){
        return armor;
    }

    public void setArmorDefenseValue(double defense){
        this.armorDefense = defense;
    }

    public double getArmorDefense(){
        return armorDefense;
    }

    public void setPerkDefenseValue(double defense){
        this.perkDefense = defense;
    }

    public double getPerkDefense(){
        return perkDefense;
    }

    public void setTotalDefense(double armorDefense, double perkDefense){
        this.armorDefense = armorDefense;
        this.perkDefense = perkDefense;
    }

    public double getTotalDefense(){
        return armorDefense + perkDefense;
    }

    public void setEnemiesDefeated(int enemiesDefeated){
        this.enemiesDefeated = enemiesDefeated;
    }

    public int getEnemiesDefeated(){
        return enemiesDefeated;
    }

    public void setRoomsTraversed(int roomsTraversed){
        this.roomsTraversed = roomsTraversed;
    }

    public void increaseRoomsTraversed(){
        this.roomsTraversed++;
    }

    public int getRoomsTraversed(){
        return roomsTraversed;
    }

    public void setCoins(int coins){
        this.coins = coins;
    }

    public int getCoins(){
        return coins;
    }

    public void setWeaponAttack(int weaponAttack){
        this.weaponAttack = weaponAttack;
    }

    public int getWeaponAttack(){
        return this.weapon.getAttackValue();
    }

    public void setTotalAttack(int attack, int weaponAttack){
        this.attack = attack;
        this.weaponAttack = weaponAttack;
    }

    public int getTotalAttack(){
        return attack + weaponAttack;
    }    

    public void setPotionsConsumed(int potionsConsumed){
        this.potionsConsumed = potionsConsumed;
    }

    public void increasePotionsConsumed(){
        this.potionsConsumed++;
    }

    public int getPotionsConsumed(){
        return potionsConsumed;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void increaseMaxHealth(double maxHealthIncrease){
        this.maxHealth += maxHealthIncrease;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return stamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public CustomArrayList<Item> getInventory(){
        return inventory;
    }
}
