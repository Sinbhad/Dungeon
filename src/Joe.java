public class Joe extends Enemy{
    public Joe(){
        super("Joe the Ogre", 50, 100, 0, 60);
        setMoves(new Move("Kibble Launcher", this.getName() + " threw a bag of dog food at you which dealt 60 damage! \nWhere did that come from??", 60));
        setMoves(new Move("Trample", this.getName() + " stomped around in a fit of rage, the shockwaves dealt 40 damage!", 40));
        setMoves(new Move("Forklift", this.getName() + " got on a forlift and drove straight into you dealing 100 damage! \nWhere does he keep finding this stuff??", 100));
        setMoves(new Move("Hay Toss Olympics", this.getName() + " threw a bale of hay at you which dealt 30 damage! \nWhat do ogres even need hay for?", 30));
    }
}
