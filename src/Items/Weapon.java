package Items;

import Characters.Move;
import lib.RobertHolder;

public class Weapon extends Item {
    private final RobertHolder<Move> moves = new RobertHolder<>();

    /**
     * Constructor for weapon items
     * @param name name of the weapon
     * @param type type of weapon
     * @param description short description of the weapon
     * @param attackVal attack value of the weapon
     * @param speedVal speed value of the weapon
     */
    public Weapon(String name, String type, String description, int attackVal, int speedVal) {
        super(name, type, description, 0, attackVal, speedVal, 0);
    }

    public void setMoves(Move move){
        this.moves.addToBucket(move);
    }

    public RobertHolder getMoves(){
        return moves;
    }
}