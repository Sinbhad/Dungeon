package characters;

public class Byron extends Enemy{
    public Byron(){
        super("Byron the Dragon", 0, 300, 0, 150, 0, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        setMove(new Move("Flame Breath", this.getName() + " this burnt you to a crisp\n!", 150));
        setMove(new Move("Wings of Fury", this.getName() + " fatally struck you with his massive wings\n", 50));
        setMove(new Move("Dinosaurs", this.getName() + " sent a pack of dinosaurs after you, \nDinosaurs? DINOSAURS!", 80));
        setMove(new Move("Fireball", this.getName() + " shot a massive fireball at you, \nGET OUTTA THERE!!!", 200));
    }
}
