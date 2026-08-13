package items;

public class Potion extends Item {
    public Potion(){
        super();
    }

    /**
     * Constructor for potion items
     * @param name name of potion
     * @param type type of effect the potion has
     * @param description short description of potion
     * @param hpValue health value of potion
     * @param speedValue speed value of potion
     * @param staminaValue stamina value of potion
     */
    public Potion(String name, String type, String description, int hpValue, int speedValue, int staminaValue) {
        super(name, type, description, hpValue, 0, speedValue, 0, staminaValue);
    }

}