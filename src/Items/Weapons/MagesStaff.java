package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class MagesStaff extends Weapon {
    public MagesStaff() {
        super("Mage's Staff", "Magic", "increases damage by 300, does not decrease speed", 300, 0);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Magic Jork", "You jorked them into another realm with the " + this.getName() + ": ", 100, 25));
        setMoves(new Move("Magical Castration", "You castrated them with the " + this.getName() + ": ", 300, 50));
        setMoves(new Move("Cosmic Array", "You conjured a hellstorm that destroyed everything in sight with the " + this.getName() + ": ", 10000, 200));
    }
}
