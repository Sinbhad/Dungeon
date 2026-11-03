package characters;

import items.Armor;
import items.Weapon;
import lib.CustomArrayList;
import items.Item;

public class Player extends Character {
    private static final CustomArrayList<Item> inventory = new CustomArrayList<>();

    public Player() {
        super("Rob", 40, 100, 500, 100, 100, 0, inventory);
        setWeapon(new Weapon("Fists", null, null, 0,0));
        setArmor(new Armor("Naked", null, null, 0,0,0));
    }

    //Inventory Mutators and Accessors set/get A-Z
    public void setArmor(Item armor){
        this.armor = armor;
    }

    public Item getArmor(){
        return armor;
    }

    public void addCoins(int coins){
        this.setCoins(this.getCoins() + coins);
    }

    public CustomArrayList<Item> getInventory(){
        return inventory;
    }


    //Stat Mutators and Accessors set/get A-Z
    public void setArmorDefenseValue(double defense){
        this.armorDefense = defense;
    }

    public double getArmorDefense(){
        return armorDefense;
    }

    public void setEnemiesDefeated(int enemiesDefeated){
        this.enemiesDefeated = enemiesDefeated;
    }

    public int getEnemiesDefeated(){
        return enemiesDefeated;
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

    public void reduceHealthByStone(){
        this.setHealth(this.getHealth() - 10);
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public void setPerkDefenseValue(double defense){
        this.perkDefense = defense;
    }

    public double getPerkDefense(){
        return perkDefense;
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

    public void clearPotionsConsumed(){
        this.setPotionsConsumed(0)
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

    public void setTotalAttack(int attack, int weaponAttack){
        this.attack = attack;
        this.weaponAttack = weaponAttack;
    }

    public int getTotalAttack(){
        return attack + weaponAttack;
    }

    public void setTotalDefense(double armorDefense, double perkDefense){
        this.armorDefense = armorDefense;
        this.perkDefense = perkDefense;
    }

    public double getTotalDefense(){
        return armorDefense + perkDefense;
    }

    public void setWeaponAttack(int weaponAttack){
        this.weaponAttack = weaponAttack;
    }

    public int getWeaponAttack(){
        return this.weapon.getAttackValue();
    }
}
