package characters;

public class Samir extends Enemy{
    public Samir(){
        super("Samir the Cheat", 0, 5, 0, 10, 0, new int[]{0});
        setMove(new Move("Cheap Trick", this.getName() + " only dealt a little damage but at least it was easy\n", 10));
        setMove(new Move("Backhanded Compliment", this.getName() + " said something that sounded nice at first but you later realized he was being a meanie \nThe shame caused you to lose health\n", 30));
        setMove(new Move("Pencil Pusher", this.getName() + " stabbed you with his favorite pencil\n", 15));
        setMove(new Move("The Last Straw", this.getName() + " suddenly remembered all the times you rage baited him, he slapped you into last week\n", 40));
    }
}