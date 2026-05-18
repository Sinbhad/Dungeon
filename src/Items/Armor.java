package Items;

public class Armor extends Item{
    /**
     * Constructor for armor items
     * @param name
     * @param type
     * @param description
     * @param attackVal
     * @param speedVal
     * @param defenseVal
     */
    public Armor(String name, String type, String description, int attackVal, int speedVal, double defenseVal) {
        super(name, type, description, 0, attackVal, speedVal, defenseVal);
    }
}
