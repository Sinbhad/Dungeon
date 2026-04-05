
public class Jared extends Enemy{
    public Jared(){
        super("Jared the Goblin", 10, 50, 40, 25);
        setMoves(new Move("Throw Leo", this.getName() + " threw his trusty cat sidekick at you, Leo dealt 10 damage", 10));
        setMoves(new Move("Explosive Leo", this.getName() + " seems to have no regard for his cats safety \nhe strapped C4 to leo before throwing him at you, Leo Bomber dealt 90 damage \nsomeone call PETA", 90));
        setMoves(new Move("Pinch", this.getName() + " pinched you really hard and dealt 20 damage!", 20));
        setMoves(new Move("Chicken Stars", this.getName() + " moved his hands in a hypnotizing motion, you fell to the floor so hard you took 67 damage!", 67));
    }
}
