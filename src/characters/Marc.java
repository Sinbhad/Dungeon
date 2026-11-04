package characters;

public class Marc extends Enemy{
    public Marc(){
        super("Marc the Toaster Beast", 0, 200, 0, 100, 0, new int[]{2, 6});
        setMove(new Move("Bathtub Party", this.getName() + " drug you into a tub with him while he was still plugged in! -*ZAPP*- \n", 100));
        setMove(new Move("Toast at Max Heat", this.getName() + " toasted you on the darkest setting!\n", 80));
        setMove(new Move("Self Destruct", this.getName() + " turned his heat up so high he exploded!\n", 150));
        setMove(new Move("Cord Wrap", this.getName() + " wrapped his cord around your neck!\n", 50));
    }
}