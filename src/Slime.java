
public class Slime extends Enemy{
    public Slime(){
        super("Slime", 10, 10, 10, 10);
        setMove("Encase", this.getName() + " covered you in his goop, nasty! \n" + this.getName() + " dealt 10 damage.");
    }
}
