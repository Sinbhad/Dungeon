public class ItemLibrary {

    //Health Potions
    public static final HealthPotion[] HEALTH_POTIONS = {
            new HealthPotion("Health Potion", "Healing Item", "restores 10 health points", 10),
            new HealthPotion("High Potion", "Healing Item", "restores 50 health points", 20),
            new HealthPotion("Mega Potion", "Healing Item", "restores 100 health points", 100)
    };

    //Traps
    public static final Trap[] TRAPS = {
            new Trap("Spray Trap", "Trap", "lowers players hp by 20 :)", -20, 0),
            new Trap("Speed Trap", "Trap", "lowers players speed by 10, oops", 0, -10)
    };

    //Weapons
    public static final Weapon[] WEAPONS = {
            new Weapon("Dagger", "Sword", "increases attack by 20 but decreases speed by 5", 20, -5),
            new Weapon("Short Sword", "Sword", "increases attack by 30 but decreases speed by 5", 30, -5),
            new Weapon("Broad Sword", "Sword", "increases damage by 50 but decreases speed by 10", 50, -10),
    };

    //Armor
    public static final Armor[] ARMOR_PIECES = {
            new Armor("Leather Armor", "Armor", "increases defense by 10 and reduces speed by 5", 0, -5, .1),
            new Armor("Chain Mail", "Armor", "increases defense by 20 and reduces speed by 10", 0, -10, .2),
            new Armor("Knight's Armor", "Armor", "increases defense by 30 and reduces speed by 20", 0, -20, .3),
    };
}