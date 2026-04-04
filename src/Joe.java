public class Joe extends Enemy{
    public Joe(){
        super("Joe the Ogre", 50, 100, 0, 60);
        setMoves("Kibble Launcher", this.getName() + " threw a bag of dog food at you which dealt 60 damage! \nWhere did that come from??", 60);
    }
}
