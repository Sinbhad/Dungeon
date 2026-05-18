package Characters;

public class Jenna extends Enemy{
    public Jenna(){
        super("Jenna the Phrog", 0, 60, 15, 25);
        setMoves(new Move("Throw Kermit", this.getName() + " threw her trusty cat sidekick at you, Kermit scratched you up!\n", 25));
        setMoves(new Move("High Jump", this.getName() + " jumped so high you couldn't see her anymore \nwhen she came down she landed on your head \n", 40));
        setMoves(new Move("Jab Lick", this.getName() + " shot her sharp tongue at you at an unbelievable speed \nit stabbed you in the foot, OUCH!\n", 30));
        setMoves(new Move("Toxic Spit",  this.getName() + " spit toxin all over you, the stinky substance melted right through your armor!\n", 60));
    }
}
