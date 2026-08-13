package characters;

public class Andrew extends Enemy{
    public Andrew(){
        super("Andrew the Great", 0, 100, 0, 100, 0, new int[]{1, 2, 7, 8, 9});
        setMoves(new Move("Discord Mod", this.getName() + " banned you from his discord server dealing emotional damage\n", 100));
        setMoves(new Move("Pledge Ritual", this.getName() + " made you embarrass yourself in front of the whole school \nPledge Ritual dealt emotional damage\n", 20));
        setMoves(new Move("Dog Ears",this.getName() + " put on his dog ears, you were tricked into thinking he was really a dog. \n When you tried to pet him he slapped you dealing big damage\n", 50));
        setMoves(new Move("Deepseek", this.getName() + " made you think of every embarrassing thing you have ever done, the mental trauma caused you to take damage\n", 15));
    }
}
