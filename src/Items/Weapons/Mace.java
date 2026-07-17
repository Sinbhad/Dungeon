package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class Mace extends Weapon {
    public Mace() {
        super("Mace", "Blunt", "increases damage by 10 but decreases speed by 5", 10, -5);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Head Bonk", "You bonked them over the head with the " + this.getName() + ": ", 10, 15));
        setMoves(new Move("Mace Poke", "You poked them with the " + this.getName() + ": ", 5, 10));
    }

}
