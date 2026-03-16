public class Brawl {
    public void fight(Character character, Enemy enemy){
        System.out.println("You have encountered a enemy, hit him with all you got\n");
        if(character.getSpeed() >= enemy.getSpeed()){
            enemy.setHealthValue(enemy.getHealth() - character.getAttack());
            System.out.println("You have hit the enemy and did " + character.getAttack() + " damage\n");
            character.setHealthValue(character.getHealth() - enemy.getAttack());
            System.out.println("He managed to get a hit in and did " + enemy.getAttack() + " damage\n\n");
        }else{
            character.setHealthValue(character.getHealth() - enemy.getAttack());
            System.out.println("The enemy hit you, but you will be okay, he did " + enemy.getAttack() + " damage\n");
            enemy.setHealthValue(enemy.getHealth() - character.getAttack());
            System.out.println("Luckily you swiped back, dealing " + character.getAttack() + " damage\n\n");
        }
        if(enemy.getHealth() <= 0){
            System.out.println("You have defeated the enemy, move along\n\n");
        }else{
            System.out.println("He ran off, better get him, he has " + enemy.getHealth() + " health remaining\n\n");
        }
    }
}
