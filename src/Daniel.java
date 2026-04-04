
public class Daniel extends Enemy{
    public Daniel(){
        super("Daniel the Moose", 20, 80, 5, 25);
        setMoves(new Move("Tosion", "Twister!! That's gotta hurt! " + this.getName() + " did 25 damage", 25));
    }
}
