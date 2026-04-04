public class Marc extends Enemy{
    public Marc(){
        super("Marc the Toaster Beast", 200, 200, 10, 100);
        setMoves("Bathtub Party", this.setName() + " drug you into a tub with him while he was still plugged in! -*ZAPP*- \n" + this.getName() + " dealt 100 damage", 100);
    }
}