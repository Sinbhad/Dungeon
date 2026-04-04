
public class Jared extends Enemy{
    public Jared(){
        super("Jared the Goblin", 10, 50, 40, 25);
        setMoves(new Move("Throw Leo", this.getName() + " threw his trusty cat sidekick at you, Leo dealt 10 damage", 10));
    }
}
