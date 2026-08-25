package characters;

import items.Armor;
import items.Weapon;
import lib.RobertHolder;
import items.Item;

public class Player extends Character {
    private static final RobertHolder<Item> inventory = new  RobertHolder<>();

    public Player() {
        super("Rob", 40, 100, 500, 100, 100, 0, inventory);
        setWeapon(new Weapon("Fists", null, null, 0,0));
        setArmor(new Armor("Naked", null, null, 0,0,0));
    }
}
