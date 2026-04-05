
public class Patrick extends Enemy{
    public Patrick(){
        super("Patrick the Whale", 20, 150, 1, 80);
        setMoves(new Move("Alright I'll Bite", this.getName() + " bit you so hard it dealt 80 damage!", 80));
        setMoves(new Move("Facebook Post", this.getName() + " made you look at his latest Facebook post \nhis post was so illiterate that you got confused and hit yourself and did 30 damage \nshame on his school system", 30));
        setMoves(new Move("Belly Flop", this.getName() + " slammed down on you with all his weight dealing 150 damage! \nHow does anyone survive this?", 150));
        setMoves(new Move("Easter Bunny", this.getName() + " gave you an easter egg with a bomb in it, you opened it up expecting jelly beans and got a jelly bomb \nJelly Bomb dealt 100 damage! hop hop hop...", 100));
    }
}
