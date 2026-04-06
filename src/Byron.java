public class Byron extends Enemy{
    public Byron(){
        super("Byron the Dragon", 0, 300, 100, 150);
        setMoves(new Move("Flame Breath", this.getName() + " this burnt you to a crisp\n!", 150));
        setMoves(new Move("Wings of Fury", this.getName() + " fatally struck you with his massive wings\n", 50));
        setMoves(new Move("Dinosaurs", this.getName() + " sent a pack of dinosaurs after you, \nDinosaurs? DINOSAURS!", 80));
        setMoves(new Move("Fireball", this.getName() + " shot a massive fireball at you, \nGET OUTTA THERE!!!", 200));
    }
}
