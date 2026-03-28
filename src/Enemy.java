import lib.Node;
import java.util.Random;

public class Enemy extends Character {
   public Enemy(String name, int attackValue, int healthValue, int speedValue){
      setName(name);
      setAttackValue(attackValue);
      setHealthValue(healthValue);
      setSpeedValue(speedValue);
   }

   public Enemy(){
      super();
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
