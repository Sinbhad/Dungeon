package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class TacticalWalkingStick extends Weapon {
    public TacticalWalkingStick() {
        super("15 in 1 Tactical Walking Stick", "Magic", "increases damage by 150 and increases speed by 30", 150, 30);
        setMoves(new Move("Falter", "You swung around the " + this.getName() + ": ", 0, 0));
        setMoves(new Move("Tactical Jab", "You used the " + this.getName() + " to gracefully move in and jab them: ", 100, 10));
        setMoves(new Move("Pogo", "You used the " + this.getName() + " as a vault and slammed down on the enemy: ", 200, 20));
        setMoves(new Move("Pharid", "Behold, true power...: ", 25000, 500));
    }
}
