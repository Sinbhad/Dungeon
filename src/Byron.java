public class Byron extends Enemy{
    public Byron(){
        super("Byron the Dragon", 200, 300, 100, 150);
        setMoves(new Move("Flame Breath", " this burnt you to a crisp dealing 150 damage!", 150));
    }
}
