package items.weapons;

import characters.Move;
import items.Weapon;

public class BroadSword extends Weapon {
    public BroadSword() {
        super("Broad Sword", "Slash", "increases damage by 50 but decreases speed by 10", 50, -10);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Slash", "You swung the  " + this.getName() + ": ", 10, 15));
        setMoves(new Move("Stab", "You stabbed with the " + this.getName() + ": ", 15, 30));
    }
}
