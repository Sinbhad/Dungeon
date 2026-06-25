package Items;

public class Trap extends Item {
    /**
     * Constructor for trap items
     * @param name name of the trap
     * @param type type of trap
     * @param description description of the trap
     * @param hpValue health value of the trap
     * @param speedValue speed value of the trap
     */
    public Trap(String name, String type, String description, int hpValue, int speedValue) {
        super(name, type, description, hpValue, 0, speedValue, 0);
    }
}