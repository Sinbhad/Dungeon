public class Samir extends Enemy{
    public Samir(){
        super("Samir the Cheat", 5, 5, 0, 10);
        setMoves(new Move("Cheap Trick", this.getName() + " only dealt 10 damage but at least it was easy", 10));
        setMoves(new Move("Backhanded Compliment", this.getName() + " said something that sounded nice at first but you later realized he was being a meanie \nThe shame caused you to lose 30 health", 30));
        setMoves(new Move("Pencil Pusher", this.getName() + " stabbed you with his favorite pencil dealing 15 damage", 15));
        setMoves(new Move("The Last Straw", this.getName() + " suddenly remembered all the times you rage baited him, he slapped you into last week dealing 40 damage", 40));
    }
}