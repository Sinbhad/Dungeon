import lib.Node;
import java.util.Random;
import lib.RobertHolder;

public class Enemy extends Character {
   private String name;
   private int attackValue, healthValue, speedValue, coinsHad;
   private RobertHolder<Move> moves = new RobertHolder();

   public Enemy(String name, int attackValue, int healthValue, int speedValue, int coinsHad){
      this.name = name;
      this.attackValue = attackValue;
      this.healthValue = healthValue;
      this.speedValue = speedValue;
      this.coinsHad = coinsHad;
   }

   public Enemy(){
      super();
   }

   public setMoves(Move move){
      this.moves.add(move);
   }

   public RobertHolder getMoves(){
      return moves;
   }

   public String getName(){
      return super.getName();
   }

   public int getAttackValue(){
      return super.getAttack();
   }

   public double getHealthValue(){
      return super.getHealth();
   }

   public int getSpeedValue(){
      return super.getSpeed();
   }

   public int getCoins(){
      return super.getCoins();
   }

   public void move() {
      Node enemyRoomNode = this.getCurrentRoom();

      if (enemyRoomNode != null) {
         Room currentEnemyRoom = (Room) enemyRoomNode.getValue();
         currentEnemyRoom.setEnemyCharacter(null);

         int enemyChoice = new Random().nextInt(2);
         if (enemyChoice == 0) {
            enemyRoomNode = enemyRoomNode.getLastNode();
         } else {
            enemyRoomNode = enemyRoomNode.getNextNode();
         }

         this.setCurrentRoom(enemyRoomNode);
         ((Room) enemyRoomNode.getValue()).setEnemyCharacter(this);
      }
   }
}
