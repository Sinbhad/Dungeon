public class Enemy extends Character implements Attack{
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
}
