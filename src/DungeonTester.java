import lib.CircularlyLinkedList;
import lib.Node;

import java.util.Scanner;
import java.util.Random;

public class DungeonTester<T> {
    public static void dungeon(){
        Rob rob = new Rob();
        Goblin goblin = new Goblin();

        int roomCount = 7;
        int levelCount = 1;

        CircularlyLinkedList<Room> dungeon = new CircularlyLinkedList<>();


        createDungeon(dungeon, roomCount);
        setRooms(dungeon);

        Room room1 = dungeon.getValAtIndex(0);
        room1.setPlayerCharacter(rob);
        Node currentDungeonRoom = dungeon.getHead();
        Node goblinRoomNode = dungeon.getNodeAtIndex(setRooms(dungeon));



        System.out.println("You have entered the dungeon");



        while(rob.getHealth() > 0){

            currentDungeonRoom = move(currentDungeonRoom, rob, levelCount);
            Room displayRoom = (Room) currentDungeonRoom.getValue();

            //System.out.println("\n\n");
            //System.out.println(displayRoom.getName() + ": Level " + levelCount);
            //System.out.println("\n\n\n");

            if(displayRoom.getIsExit()){
                System.out.println("You have found the exit");
                System.out.println("welcome to the next level.\n");
                System.out.println("You have gained nothing and your opponents are now stronger!");
                levelCount++;

                goblin.setHealthValue(new Goblin().getHealth() + 10);
                System.out.println("The goblin has gained 10 health points");
                if(levelCount % 2 == 0){
                    goblin.setAttackValue(goblin.getAttack() + 10);
                    System.out.println("...and 20 attack points!");
                }

                createDungeon(dungeon, roomCount * levelCount);
                setRooms(dungeon);
                currentDungeonRoom = dungeon.getHead();
                ((Room) currentDungeonRoom.getValue()).setPlayerCharacter(rob);
                continue;
            }

            if (displayRoom.getEnemyCharacter() != null) {
                fight(rob, goblin);
            }

            if(displayRoom.getItem() != null){
                System.out.println("You found a chest!");
                chestOpener(currentDungeonRoom, rob, levelCount);
            }



            //if(displayRoom.getIsExit()){
            //    System.out.println("You have found the exit");
            //    setRooms(dungeon);
            //    levelCount++;
            //    isExit = true;
            //}

            Room tempGoblinRoom = (Room) goblinRoomNode.getValue();
            goblinRoomNode = goblinMove(goblinRoomNode, goblin);
            tempGoblinRoom.setEnemyCharacter(null);


        }








    }

    public static int setRooms(CircularlyLinkedList<Room> dungeon){
        Random chanceNum = new Random();
        int size = dungeon.getSize();

        for(int i = 0; i < size; i++){
            Room tempRoom = dungeon.getValAtIndex(i);
            tempRoom.setCertain(null, null, null, false);
        }

        int goblinRoomIndex = chanceNum.nextInt(size);
        Room goblinRoom = dungeon.getValAtIndex(goblinRoomIndex);
        goblinRoom.setEnemyCharacter(new Goblin());


        int swordRoomIndex = chanceNum.nextInt(size);
        Room swordRoom = dungeon.getValAtIndex(swordRoomIndex);
        swordRoom.setItem(ItemLibrary.SWORDS[chanceNum.nextInt(ItemLibrary.SWORDS.length)]);

        //maybe we even call it a weapon
        //swordRoom.setItem(new Sword());


        int healthPotionRoomIndex = chanceNum.nextInt(size);
        Room healthPotionRoom = dungeon.getValAtIndex(healthPotionRoomIndex);
        healthPotionRoom.setItem(ItemLibrary.HEALTH_POTIONS[chanceNum.nextInt(ItemLibrary.HEALTH_POTIONS.length)]);

        //must be fixed
        //healthPotionRoom.setItem(HealthPotion());


        int trapRoomIndex = chanceNum.nextInt(size);
        Room trapRoom = dungeon.getValAtIndex(trapRoomIndex);
        trapRoom.setItem(ItemLibrary.TRAPS[chanceNum.nextInt(ItemLibrary.TRAPS.length)]);

        //uh oh, this one too
        //trapRoom.setItem(new Trap());


        int exitRoomIndex = chanceNum.nextInt(size);
        Room exitRoom = dungeon.getValAtIndex(exitRoomIndex);
        exitRoom.setIsExit(true);

        return goblinRoomIndex;
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
        System.out.print("\n\nWould you like to move left or right? (l/r) : ");
        String choice = keyboard.nextLine();

        if(choice.equals("l")){
            System.out.println("You have moved left\n");
            currentDungeonRoom = currentDungeonRoom.getLastNode();
        }else if(choice.equals("r")){
            System.out.println("You have moved right\n");
            currentDungeonRoom = currentDungeonRoom.getNextNode();
        }else{
            System.out.println("Invalid choice\n");
        }

        return currentDungeonRoom;
    }

    public static void chestOpener(Node currentDungeonRoom, Character rob, int levelCount){
        Room currentRoom = (Room) currentDungeonRoom.getValue();
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\n\nWould you like to open the chest? (y/n) : ");
        String choice = keyboard.nextLine();

        if(choice.equals("y")){
            int hp = rob.getHealth();
            int speed = rob.getSpeed();
            int attack = rob.getAttack();

            System.out.println("\nYou have opened the chest");
            System.out.println("You have found a " + currentRoom.getItem().getName());
            System.out.println("this " + currentRoom.getItem().getDescription() + "\n\n");

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

    public static void createDungeon(CircularlyLinkedList<Room> dungeon, int roomCount){
        dungeon.clear();
        for(int i = 0; i < roomCount; i++){
            dungeon.add(new Room("Room " + (i + 1), null, null, null, false));
        }
    }
}

