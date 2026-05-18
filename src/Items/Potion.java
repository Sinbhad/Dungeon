package Items;

public class Potion extends Item {
    /**
     * Constructor for potion items
     * @param name
     * @param type
     * @param description
     * @param hpValue
     * @param speedValue
     */
    public Potion(String name, String type, String description, int hpValue, int speedValue) {
        super(name, type, description, hpValue, 0, speedValue, 0);
    }
}