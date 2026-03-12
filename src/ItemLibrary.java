public class ItemLibrary {

    //Health Potions
    public static final HealthPotion[] HEALTH_POTIONS = {

            new HealthPotion("Health Potion", "Healing Item", "restores 10 health points", 10)
    };

    //Traps
    public static final Trap[] TRAPS = {
            new Trap("Spray Trap", "Trap", "lowers players hp by 20 :)", -20)
    };

    //Weapons
    public static final Sword[] SWORDS = {
            new Sword("BroadSword", "Sword", "increases attack by 20 but decreases speed by 5", 20, -5)
    };
}