package Items;

public class ItemLibrary {

    //Potions
    public final Potion[] HEALTH_POTIONS = {
            //Healing
            new Potion("Health Potion", "Healing Item", "restores 10 health points", 10, 0),
            new Potion("High Potion", "Healing Item", "restores 50 health points", 20, 0),
            new Potion("Mega Potion", "Healing Item", "restores 100 health points", 100, 0),
            new Potion("Ultimate Potion", "Healing Item", "restores 200 health points", 200, 0),
            new Potion("Ho Lee Phuq Potion", "Healing Item", "restores 500 health points", 500, 0),

            //Speed
            new Potion("Mini Speed Potion","Speed Up Item","increases speed by 15 points",0,15),
            new Potion("Moderate Speed Potion", "Speed Up Item", "increases speed by 30 points", 0, 30),
            new Potion("Great Speed Potion", "Speed Up Item", "increases speed by 50 points", 0, 50),
            new Potion("Mega Speed Potion", "Speed Up Item", "increases speed by 75 points", 0, 75),

            //Healing and Speed
            new Potion("Swift Life Potion", "Extended Healing Item", "increases speed and health by 20 points each", 20, 20),
            new Potion("Fast Life Potion", "Extended Healing Item", "increases speed and health by 40 points each", 40, 40),
            new Potion("Cheeseburger Deluxe", "Extended Healing Item", "increases health by 60 points but reduces speed by 10", 60, -10),
            new Potion("Breakneck Life Potion", "Extended Healing Item", "increases speed and health by 100 points each", 100,100)

    };

    //Traps
    public final Trap[] TRAPS = {
            new Trap("Spray Trap", "Trap", "lowers players hp by 20 :)", -20, 0),
            new Trap("Speed Trap", "Trap", "lowers players speed by 10, oops", 0, -10),
            new Trap("Unlucky Traveler's Trap", "Trap", "lowers players hp and speed by 15 each, huzzah!", -15, -15),
            new Trap("Welp Trap", "Trap", "lowers players hp by 200, welp...", -200, 0)
    };

    //Armor
    public final Armor[] ARMOR_PIECES = {
            new Armor("Scraps of Cloth", "Armor", "increases defense by 5, does not decrease speed", 0, 5, 0),
            new Armor("Leather Armor", "Armor", "increases defense by 10 and reduces speed by 5", 0, -5, .1),
            new Armor("Chain Mail", "Armor", "increases defense by 20 and reduces speed by 10", 0, -10, .2),
            new Armor("Knight's Armor", "Armor", "increases defense by 30 and reduces speed by 20", 0, -20, .3),
            new Armor("Plate Mail", "Armor", "increases defense by 40 and reduces speed by 30", 0, -30, .4),
            new Armor("Dragon's Breastplate", "Armor", "increases defense by 50 and reduces speed by 40", 0, -40, .5),
            new Armor("Magic Armor", "Armor", "increases defense by 80, does not decrease speed", 0, 0, .8),
    };
}