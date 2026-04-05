public class Marc extends Enemy{
    public Marc(){
        super("Marc the Toaster Beast", 200, 200, 10, 100);
        setMoves(new Move("Bathtub Party", this.getName() + " drug you into a tub with him while he was still plugged in! -*ZAPP*- \n" + this.getName() + " dealt 100 damage", 100));
        setMoves(new Move("Toast at Max Heat", this.getName() + " toasted you on the darkest setting dealing 80 damage!", 80));
        setMoves(new Move("Self Destruct", this.getName() + " turned his heat up so high he exploded and dealt 150 damage!", 150));
        setMoves(new Move("Cord Wrap", this.getName() + " wrapped his cord around your neck and dealt 50 damage before you could free yourself!", 50));
    }
}