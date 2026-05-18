package Items;

public class Weapon extends Item {
    /**
     * Constructor for weapon items
     * @param name
     * @param type
     * @param description
     * @param attackVal
     * @param speedVal
     */
    public Weapon(String name, String type, String description, int attackVal, int speedVal) {
        super(name, type, description, 0, attackVal, speedVal, 0);
    }
}