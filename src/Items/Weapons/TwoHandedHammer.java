package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class TwoHandedHammer extends Weapon {
    public TwoHandedHammer() {
        super("Two Handed Hammer", "Blunt", "increases damage by 60 but decreases speed by 15", 60, -15);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Head Bonk", "You bonked them over the head with the " + this.getName() + ": ", 10, 15));
        setMoves(new Move("Overhead Slam", "You charged up an overhead slam with the " + this.getName() + ": ", 25, 50));
    }
}
