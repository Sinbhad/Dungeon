
public class Player extends Character {
    public Player(){
        setName("Rob");
        setAttackValue(40);
        setHealthValue(100);
        setSpeedValue(20);
        setWeapon(new Weapon("Fists", null, null, 0,0));
        setArmor(new Armor("Naked", null, null, 0,0,0));
    }
}
