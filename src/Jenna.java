public class Jenna extends Enemy{
    public Jenna(){
        super("Jenna the Phrog", 30, 60, 15, 25);
        setMoves(new Move("Throw Kermit", this.getName() + " threw her trusty cat sidekick at you, Kermit dealt 25 damage", 25));
        setMoves(new Move("High Jump", this.getName() + " jumped so high you couldn't see her anymore \nwhen she came down she landed on your head so hard it dealt 40 damage", 40));
        setMoves(new Move("Jab Lick", this.getName() + " shot her sharp tongue at you at an unbelievable speed \nit stabbed you and dealt 30 damage", 30));
        setMoves(new Move("Toxic Spit",  this.getName() + " spit toxin all over you, the stinky substance dealt 60 damage", 60));
    }
}
