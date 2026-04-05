public class Byron extends Enemy{
    public Byron(){
        super("Byron the Dragon", 200, 300, 100, 150);
        setMoves(new Move("Flame Breath", this.getName() + " this burnt you to a crisp\n!", 150));
    }
}
