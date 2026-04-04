public class Jenna extends Enemy{
    public Jenna(){
        super("Jenna the Phrog", 30, 60, 15, 25);
        setMoves(new Move("Throw Kermit", this.getName() + " threw her trusty cat sidekick at you, Kermit dealt 25 damage", 25));
    }
}
