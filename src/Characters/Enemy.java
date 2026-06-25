package Characters;

import Dungeon.Room;
import lib.Node;
import java.util.Random;
import lib.RobertHolder;

public class Enemy extends Character {
   private final RobertHolder<Move> moves = new RobertHolder<>();
   //Used to store the values used for fleeing probability in battle
   int[] fleeNums = new int[10];

    /**
     * Constructor for enemies to be used in the game
     * @param name enemy name
     * @param attackValue enemy attack value
     * @param healthValue enemy health value
     * @param speedValue enemy speed value
     * @param coinsHad coins enemy has for player to collect
     */
   public Enemy(String name, int attackValue, int healthValue, int speedValue, int coinsHad, int[] fleeNums){
       super(name, attackValue, healthValue, speedValue, coinsHad, new RobertHolder<>());
       this.fleeNums = fleeNums;
   }

   public Enemy(){
      super();
   }

   public void setMoves(Move move){
       this.moves.addToBucket(move);
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

   public int getSpeed(){
      return super.getSpeed();
   }

   public int getCoins(){
      return super.getCoins();
   }

   public int[] getFleeNum(){
      return fleeNums;
   }

   /**
    * Moves the enemy to a random adjacent room
    */
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
