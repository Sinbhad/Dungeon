
public class ItemLibrary {

    //Health Potions
    public final HealthPotion[] HEALTH_POTIONS = {
            new HealthPotion("Health Potion", "Healing Item", "restores 10 health points", 10),
            new HealthPotion("High Potion", "Healing Item", "restores 50 health points", 20),
            new HealthPotion("Mega Potion", "Healing Item", "restores 100 health points", 100),
            new HealthPotion("Ultimate Potion", "Healing Item", "restores 200 health points", 200),
            new HealthPotion("Ho Lee Faq Potion", "Healing Item", "restores 500 health points", 500),
    };

    //Traps
    public final Trap[] TRAPS = {
            new Trap("Spray Trap", "Trap", "lowers players hp by 20 :)", -20, 0),
            new Trap("Speed Trap", "Trap", "lowers players speed by 10, oops", 0, -10),
            new Trap("Unlucky Traveler's Trap", "Trap", "lowers players hp and speed by 15 each, huzzah!", -15, -15),
            new Trap("Welp Trap", "Trap", "lowers players hp by 200, welp...", -200, 0)
    };

    //Weapons
    public final Weapon[] WEAPONS = {
            new Weapon("Dagger", "Sword", "increases attack by 20 but decreases speed by 5", 20, -5),
            new Weapon("Short Sword", "Sword", "increases attack by 30 but decreases speed by 5", 30, -5),
            new Weapon("Broad Sword", "Sword", "increases damage by 50 but decreases speed by 10", 50, -10),
            new Weapon("Mace", "Blunt", "increases damage by 10 but decreases speed by 5", 10, -5),
            new Weapon("Hammer", "Blunt", "increases damage by 20 but decreases speed by 10", 20, -10),
            new Weapon("Two Handed Hammer", "Blunt", "increases damage by 60 but decreases speed by 15", 60, -15),
            new Weapon("Mage's Staff", "Magic", "increases damage by 300, does not decrease speed", 300, 0),
    };

    //Armor
    public final Armor[] ARMOR_PIECES = {
            new Armor("Scraps Armor", "Armor", "increases defense by 5, does not decrease speed", 0, 5, 0),
            new Armor("Leather Armor", "Armor", "increases defense by 10 and reduces speed by 5", 0, -5, .1),
            new Armor("Chain Mail", "Armor", "increases defense by 20 and reduces speed by 10", 0, -10, .2),
            new Armor("Knight's Armor", "Armor", "increases defense by 30 and reduces speed by 20", 0, -20, .3),
            new Armor("Plate Mail", "Armor", "increases defense by 40 and reduces speed by 30", 0, -30, .4),
            new Armor("Dragon's Breastplate", "Armor", "increases defense by 50 and reduces speed by 40", 0, -40, .5),
            new Armor("Magic Armor", "Armor", "increases defense by 80, does not decrease speed", 0, 0, .8),
    };
}