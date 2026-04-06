
public class Jared extends Enemy{
    public Jared(){
        super("Jared the Goblin", 0, 50, 40, 25);
        setMoves(new Move("Throw Leo", this.getName() + " threw his trusty cat sidekick at you, Leo bit your ankle and ran back\n", 10));
        setMoves(new Move("Explosive Leo", this.getName() + " seems to have no regard for his cats safety \nhe strapped C4 to leo before throwing him at you, \nLeo Bomber exploded but is somehow still with us \nsomeone call PETA\n", 90));
        setMoves(new Move("Pinch", this.getName() + " pinched you really hard!\n", 20));
        setMoves(new Move("Chicken Stars", this.getName() + " moved his hands in a hypnotizing motion, you fell to the floor and hit your head! \n", 67));
    }
}
