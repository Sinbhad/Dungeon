
public class Patrick extends Enemy{
    public Patrick(){
        super("Patrick the Whale", 20, 150, 1, 80);
        setMove(new Move("Alright I'll Bite", this.getName + " bit you so hard it dealt 80 damage!", 80))
    }
}
