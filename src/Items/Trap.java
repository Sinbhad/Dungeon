package Items;

public class Trap extends Item {
    /**
     * Constructor for trap items
     * @param name
     * @param type
     * @param description
     * @param hpValue
     * @param speedValue
     */
    public Trap(String name, String type, String description, int hpValue, int speedValue) {
        super(name, type, description, hpValue, 0, speedValue, 0);
    }
}