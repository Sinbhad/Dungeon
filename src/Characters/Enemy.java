package Characters;

import Dungeon.Room;
import Items.Item;
import lib.Node;
import java.util.Random;
import lib.RobertHolder;

public class Enemy extends Character {
   private RobertHolder<Move> moves = new RobertHolder();

    /**
     * Constructor for enemies to be used in the game
     * @param name
     * @param attackValue
     * @param healthValue
     * @param speedValue
     * @param coinsHad
     */
   public Enemy(String name, int attackValue, int healthValue, int speedValue, int coinsHad){
       super(name, attackValue, healthValue, speedValue, coinsHad, new RobertHolder<Item>());
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
