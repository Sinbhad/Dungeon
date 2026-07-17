package Characters;

public class Joe extends Enemy{
    public Joe(){
        super("Joe the Ogre", 0, 100, 0, 60, 0, new int[]{1});
        setMoves(new Move("Kibble Launcher", this.getName() + " threw a bag of dog food at you! \nWhere did that come from??", 60));
        setMoves(new Move("Trample", this.getName() + " stomped around in a fit of rage, the shockwaves hurt you!\n", 40));
        setMoves(new Move("Forklift", this.getName() + " got on a forklift and drove straight into you! \nWhere does he keep finding this stuff??\n", 100));
        setMoves(new Move("Hay Toss Olympics", this.getName() + " threw a bale of hay at you! \nWhat do ogres even need hay for?\n", 30));
    }
}
