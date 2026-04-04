public class Andrew extends Enemy{
    public Andrew(){
        super("Andrew the Great", 100, 100, 100, 100);
        setMoves(new Move("Discord Mod", this.getName() + " banned you from his discord server dealing 100 emotional damage", 100));
    }
}
