import lib.CircularlyLinkedList;
import lib.Node;

import java.util.Scanner;
import java.util.Random;

public class DungeonTester<T> {
    public static void dungeon(){
        Rob rob = new Rob();
        Goblin goblin = new Goblin();

        int levelCount = 1;
        //Sword sword = new Sword();


        //Scanner keyboard = new Scanner(System.in);
        Random chanceNum = new Random();
        CircularlyLinkedList<Room> dungeon = new CircularlyLinkedList<>();


        Room room1 = new Room("Room 1", null, null, null, false);
        Room room2 = new Room("Room 2", null, null, null, false);
        Room room3 = new Room("Room 3", null, null, null, false);
        Room room4 = new Room("Room 4", null, null, null, false);
        Room room5 = new Room("Room 5", null, null, null, false);
        Room room6 = new Room("Room 6", null, null, null, false);
        Room room7 = new Room("Room 7", null, null, null, false);



        dungeon.add(room1);
        dungeon.add(room2);
        dungeon.add(room3);
        dungeon.add(room4);
        dungeon.add(room5);
        dungeon.add(room6);
        dungeon.add(room7);



        /* test
        Room tempRoom = dungeon.getValAtIndex(3);


        tempRoom.setCertain(sword, rob, goblin, false);

        System.out.println(tempRoom.getItem().getName());
        System.out.println(tempRoom.getEnemyCharacter().getName());
        System.out.println(tempRoom.getPlayerCharacter().getName());


        if(!tempRoom.getIsExit()){
            System.out.println("This room is not an exit");
        }


        System.out.println(dungeon.getSize());
         */


        int goblinRoomIndex = chanceNum.nextInt(7);
        Room goblinRoom = dungeon.getValAtIndex(goblinRoomIndex);
        goblinRoom.setEnemyCharacter(new Goblin());
        //System.out.println(goblinRoom.getName() + " " +  goblinRoom.getEnemyCharacter().getName());


        int swordRoomIndex = chanceNum.nextInt(7);
        Room swordRoom = dungeon.getValAtIndex(swordRoomIndex);
        swordRoom.setItem(new Sword());
        //System.out.println(swordRoom.getName() + " " + swordRoom.getItem().getName());

        int healthPotionRoomIndex = chanceNum.nextInt(7);
        Room healthPotionRoom = dungeon.getValAtIndex(healthPotionRoomIndex);
        healthPotionRoom.setItem(new HealthPotion());
        //System.out.println(healthPotionRoom.getName() + " " + healthPotionRoom.getItem().getName());

        int trapRoomIndex = chanceNum.nextInt(7);
        Room trapRoom = dungeon.getValAtIndex(trapRoomIndex);
        trapRoom.setItem(new Trap());
        //System.out.println(trapRoom.getName() + " " + trapRoom.getItem().getName());

        int exitRoomIndex = chanceNum.nextInt(7);
        Room exitRoom = dungeon.getValAtIndex(exitRoomIndex);
        exitRoom.setIsExit(true);
        //if(exitRoom.getIsExit()){
        //     System.out.println(exitRoom.getName() + " is an exit");
        // }



        room1.setPlayerCharacter(rob);
        Node currentDungeonRoom = dungeon.getHead();
        Node goblinRoomNode = dungeon.getNodeAtIndex(goblinRoomIndex);



        System.out.println("You have entered the dungeon");


        boolean isExit = false;
        while(rob.getHealth() > 0 && !isExit){
            currentDungeonRoom = move(currentDungeonRoom, rob, levelCount);
            Room displayRoom = (Room) currentDungeonRoom.getValue();

            System.out.println("\n\n");

            System.out.println(displayRoom.getName() + ": Level " + levelCount);

            System.out.println("\n\n\n");

            if (displayRoom.getEnemyCharacter() != null) {
                fight(rob, goblin);
            }

            if(displayRoom.getItem() != null){
                System.out.println("You found a chest!");
                chestOpener(currentDungeonRoom, rob, levelCount);
            }

            if(displayRoom.getIsExit()){
                System.out.println("You have found the exit");
                isExit = true;
            }

            Room tempGoblinRoom = (Room) goblinRoomNode.getValue();
            goblinRoomNode = goblinMove(goblinRoomNode, goblin);
            tempGoblinRoom.setEnemyCharacter(null);


        }








    }

    public static void fight(Character rob, Character goblin) {
        System.out.println("You have encountered a goblin, hit him with all you got\n");
        if(rob.getSpeed() >= goblin.getSpeed()){
            goblin.setHealthValue(goblin.getHealth() - rob.getAttack());
            System.out.println("You have hit the goblin and did " + rob.getAttack() + " damage\n");
            rob.setHealthValue(rob.getHealth() - goblin.getAttack());
            System.out.println("He managed to get a hit in and did " + goblin.getAttack() + " damage\n\n");
        }else{
            rob.setHealthValue(rob.getHealth() - goblin.getAttack());
            System.out.println("The goblin hit you, but you will be okay, he did " + goblin.getAttack() + " damage\n");
            goblin.setHealthValue(goblin.getHealth() - rob.getAttack());
            System.out.println("Luckily you swiped back, dealing " + rob.getAttack() + " damage\n\n");
        }
        if(goblin.getHealth() <= 0){
            System.out.println("You have defeated the goblin, move along\n\n");
        }else{
            System.out.println("He ran off, better get him, he has " + goblin.getHealth() + " health remaining\n\n");
        }
        rob.displayStats();
    }

    public static Node goblinMove(Node goblinRoomNode, Goblin goblin){
        Room currentGoblinRoom = (Room) goblinRoomNode.getValue();
        currentGoblinRoom.setEnemyCharacter(null);

        int goblinChoice = new Random().nextInt(2);
        if(goblinChoice == 0){
            goblinRoomNode = goblinRoomNode.getLastNode();
        }else{
            goblinRoomNode = goblinRoomNode.getNextNode();
        }

        Room newGoblinRoom = (Room) goblinRoomNode.getValue();
        newGoblinRoom.setEnemyCharacter(goblin);

        return goblinRoomNode;
    }



    public static Node move(Node currentDungeonRoom, Character rob, int levelCount){
        Scanner keyboard = new Scanner(System.in);
        Room currentRoom = (Room) currentDungeonRoom.getValue();
        System.out.println(currentRoom.getName() + ": Level " + levelCount);
        rob.displayStats();
        System.out.print("Would you like to move left or right? (l/r) : ");
        String choice = keyboard.nextLine();

        if(choice.equals("l")){
            System.out.println("You have moved left");
            currentDungeonRoom = currentDungeonRoom.getLastNode();
        }else if(choice.equals("r")){
            System.out.println("You have moved right");
            currentDungeonRoom = currentDungeonRoom.getNextNode();
        }else{
            System.out.println("Invalid choice");
        }

        return currentDungeonRoom;
    }

    public static void chestOpener(Node currentDungeonRoom, Character rob, int levelCount){
        Room currentRoom = (Room) currentDungeonRoom.getValue();
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Would you like to open the chest? (y/n) : ");
        String choice = keyboard.nextLine();

        if(choice.equals("y")){
            int hp = rob.getHealth();
            int speed = rob.getSpeed();
            int attack = rob.getAttack();

            System.out.println("You have opened the chest");
            System.out.println("You have found a " + currentRoom.getItem().getName());
            System.out.println("this " + currentRoom.getItem().getDescription());

            if(currentRoom.getItem().getAttackValue() != 0){
                int itemAttack = currentRoom.getItem().getAttackValue();
                rob.setAttackValue(attack + itemAttack);
            }
            if(currentRoom.getItem().getHpValue() != 0){
                int itemHp = currentRoom.getItem().getHpValue();
                rob.setHealthValue(hp + itemHp);
            }
            if(currentRoom.getItem().getSpeedValue() != 0){
                int itemSpeed = currentRoom.getItem().getSpeedValue();
                rob.setSpeedValue(speed + itemSpeed);
            }
            if(currentRoom.getItem().getType() != null && currentRoom.getItem().getType().equals("Sword")){
                rob.setEquipment(currentRoom.getItem().getName());
            }

            currentRoom.setItem(null);

        }else if(choice.equals("n")){
            System.out.println("You have not opened the chest");
        }else{
            System.out.println("Invalid choice");
            chestOpener(currentDungeonRoom, rob, levelCount);
        }
    }
}

