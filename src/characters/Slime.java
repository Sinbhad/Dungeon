package characters;

public class Slime extends Enemy{
    public Slime(){
        super("Slime", 0, 10, 0, 10, 0, new int[]{0});
        setMove(new Move("Encase", this.getName() + " covered you in his goop, nasty! \n", 10));
        setMove(new Move("Scream", this.getName() + " screamed so loud it hurt your soul.\n", 5));
        setMove(new Move("Roll", this.getName() + " rolled into you, it almost hurt.\n", 5));
        setMove(new Move("Slime Launch", this.getName() + " grabbed some of his goop and threw it at you.\n", 15));
    }
}
