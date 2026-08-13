package characters;


public class Patrick extends Enemy{
    public Patrick(){
        super("Patrick the Whale", 0, 150, 0, 80, 0, new int[]{1, 2, 6, 7});
        setMoves(new Move("Alright I'll Bite", this.getName() + " bit you?!\n", 80));
        setMoves(new Move("Facebook Post", this.getName() + " made you look at his latest Facebook post \nhis post was so illiterate that you got confused and hit yourself \nshame on his school system\n", 30));
        setMoves(new Move("Belly Flop", this.getName() + " slammed down on you with all his weight dealing massive damage! \nHow does anyone survive this?\n", 150));
        setMoves(new Move("Easter Bunny", this.getName() + " gave you an easter egg with a bomb in it, you opened it up expecting jelly beans and got a jelly bomb \nJelly Bomb exploded! hop hop hop...\n", 100));
    }
}
