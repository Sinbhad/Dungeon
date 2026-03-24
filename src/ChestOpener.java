import lib.Node;

import java.util.Scanner;

public class ChestOpener {
    public static void openChest(Node currentDungeonRoom, Character character){
        Room currentRoom = (Room) currentDungeonRoom.getValue();
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\n\nWould you like to open the chest? (y/n) : ");
        String choice = keyboard.nextLine();

        if(choice.trim().equals("y")){
            double hp = character.getHealth();
            int speed = character.getSpeed();
            int attack = character.getAttack();

            System.out.println("\nYou have opened the chest");
            System.out.println("You have found a " + currentRoom.getItem().getName());
            System.out.println("this " + currentRoom.getItem().getDescription() + "\n\n");

            if(currentRoom.getItem().getAttackValue() != 0){
                int itemAttack = currentRoom.getItem().getAttackValue();
                character.setAttackValue(attack + itemAttack);
            }
            if(currentRoom.getItem().getHpValue() != 0){
                int itemHp = currentRoom.getItem().getHpValue();
                if(character.getHealth() < 500){
                    character.setHealthValue(hp + itemHp);
                    if(character.getHealth() > 500){
                        character.setHealthValue(500);
                    }
                }else{
                    System.out.println("You have already reached maximum health, no effect\n");
                }
            }
            if(currentRoom.getItem().getSpeedValue() != 0){
                int itemSpeed = currentRoom.getItem().getSpeedValue();
                character.setSpeedValue(speed + itemSpeed);
            }
            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Sword")){
                character.setWeapon(currentRoom.getItem());
                character.setAttackValue(40 + currentRoom.getItem().getAttackValue());
            }

            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Armor")){
                character.setArmor(currentRoom.getItem());
                character.setDefenseValue(currentRoom.getItem().getDefenseValue());
            }

            currentRoom.setItem(null);

        }else if(choice.trim().equalsIgnoreCase("n")){
            System.out.println("You have not opened the chest");
        }else{
            System.out.println("Invalid choice");
            openChest(currentDungeonRoom, character);
        }
    }
}
