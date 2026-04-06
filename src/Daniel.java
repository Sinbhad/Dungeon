
public class Daniel extends Enemy{
    public Daniel(){
        super("Daniel the Moose", 0, 80, 5, 25);
        setMoves(new Move("Torsion", "Twister!! That's gotta hurt!\n", 25));
        setMoves(new Move("Vortexblade", this.getName() + " created a spectral sword between his horns and struck you with it!\n", 40));
        setMoves(new Move("Tormento", this.getName() + " sent you to the realm of torment for a mini vacation :)\n", 35));
        setMoves(new Move("Mr.Muscles", this.getName() + " spawned his good friend Mr.Muscles to take care of you, UH-OH!\n", 50));
    }
}
