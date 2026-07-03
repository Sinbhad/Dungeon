package Items.Weapons;

import Characters.Move;
import Items.Weapon;

public class Dagger extends Weapon {
    public Dagger(){
        super("Dagger", "Slash", "increases attack by 20 but decreases speed by 5", 20, -5);
    }
}
