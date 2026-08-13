package items;

public class Armor extends Item{
    /**
     * Constructor for armor items
     * @param name name of armor
     * @param type type of armor
     * @param description description of armor
     * @param attackVal attack value of armor
     * @param speedVal speed value of armor
     * @param defenseVal defense value of armor
     */
    public Armor(String name, String type, String description, int attackVal, int speedVal, double defenseVal) {
        super(name, type, description, 0, attackVal, speedVal, defenseVal, 0);
    }
}
