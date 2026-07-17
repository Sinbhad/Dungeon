package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class Hammer extends Weapon {
    public Hammer() {
        super("Hammer", "Blunt", "increases damage by 20 but decreases speed by 10", 20, -10);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Head Bonk", "You bonked them over the head with the " + this.getName() + ": ", 10, 15));
        setMoves(new Move("Hammer Toss", "You threw the " + this.getName() + " at them, let's hope it comes back: ", 25, 30));
    }

}
