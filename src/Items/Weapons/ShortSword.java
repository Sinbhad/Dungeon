package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class ShortSword extends Weapon {
    public ShortSword() {
        super("Short Sword", "Slash", "increases attack by 30 but decreases speed by 5", 30, -5);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Slash", "You swung the  " + this.getName() + ": ", 10, 15));
        setMoves(new Move("Stab", "You stabbed with the " + this.getName() + ": ", 15, 30));
    }
}
