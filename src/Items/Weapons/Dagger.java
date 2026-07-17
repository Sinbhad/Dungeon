package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class Dagger extends Weapon {
    public Dagger(){
        super("Dagger", "Slash", "increases attack by 20 but decreases speed by 5", 20, -5);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Slash", "You swung the  " + this.getName() + ": ", 10, 15));
        setMoves(new Move("Stab", "You stabbed with the " + this.getName() + ": ", 15, 30));
    }
}
