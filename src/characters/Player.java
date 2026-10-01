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

    public void addCoins(int coins){
        this.setCoins(this.getCoins() + coins);
    }
}
