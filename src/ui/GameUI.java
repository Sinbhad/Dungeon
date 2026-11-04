package ui;

import characters.Enemy;
import characters.Move;
import characters.Player;
import characters.PlayerFunctions;
import dungeon.GameState;
import dungeon.Room;
import items.Weapon;
import lib.CustomArrayList;

/**
 * Used to provide a better UX with customizable styling options pulled from the GameFormatter Library
 */
public class GameUI{
    private final GameState gameState;

    public GameUI(){
        this.gameState = new GameState();
    }

    public GameUI(GameState gameState){
        this.gameState = gameState;
    }

    public void prettyPrint(String value){
        String prettyString = prettyStringFormatter(value);
        System.out.print(prettyString);
    }

    public void prettyPrintln(String value){
        String prettyString = prettyStringFormatter(value);
        System.out.println(prettyString);
    }

    public String prettyStringFormatter(String value){
        return value
                .replace("[R]", GameFormatter.RED)
                .replace("[G]", GameFormatter.GREEN)
                .replace("[Y]", GameFormatter.YELLOW)
                .replace("[B]", GameFormatter.BLUE)
                .replace("[P]", GameFormatter.PURPLE)
                .replace("[C]", GameFormatter.CYAN)

                .replace("[BLD]", GameFormatter.BOLD)
                .replace("[ITL]", GameFormatter.ITALICS)

                .replace("[EMPTYINV]", GameFormatter.EMPTY_INVENTORY)
                .replace("[INVALID]", GameFormatter.INVALID_CHOICE)

                .replace("[BRK]", GameFormatter.RESET)
                .replace("[CLR]", GameFormatter.CLEAR_TERMINAL);
    }

    /**
     * Method used throughout the game to display equipment and other stats on each turn
     */
    public void displayStats() {
        String healthFormatString = "[G]";
        Player player = gameState.getPlayer();

        String weaponName = (player.getWeapon() != null) ? player.getWeapon().getName() : "Fists";
        String armorName = (player.getArmor() != null) ? player.getArmor().getName() : "Naked";

        // Fixed division so < 50% max health turns red
        if (player.getHealth() / player.getMaxHealth() < 0.5) {
            healthFormatString = "[R]";
        }

        prettyPrintln(
                "========================================================================\n" +
                        "[BLD][P]" + player.getName() + "[BRK]  |  " +
                        "[BLD]HP:[BRK] " + healthFormatString + player.getHealth() + "/" + player.getMaxHealth() + "[BRK]  |  " +
                        "[BLD]SP:[BRK] [Y]" + player.getStamina() + "/" + player.getMaxStamina() + "[BRK]  |  " +
                        "[BLD]ATK:[BRK] [P]" + player.getTotalAttack() + "[BRK]  |  " +
                        "[BLD]Coins:[BRK] [Y]" + player.getCoins() + "[BRK]\n" +
                        "[BLD]Weapon:[BRK] [ITL][C]" + weaponName + "[BRK]  |  " +
                        "[BLD]Armor:[BRK] [ITL][C]" + armorName + "[BRK]  |  " +
                        "[BLD]DEF:[BRK] [C]" + (int)(player.getTotalDefense() * 100) + "%[BRK]\n" +
                        "========================================================================"
        );
    }

    public Move returnSelectedMoveFormatted(int choice) {
        //Default move if no moves are available
        Move punch = new Move("Punch", "Bam! you hit them right in the face ", 10, 0);
        Player player = gameState.getPlayer();
        PlayerFunctions playerFunc = new PlayerFunctions(gameState);
        Weapon weapon = player.getWeapon();
        CustomArrayList<Move> moves = weapon.getMoves();
        //Check if moves are null (handling the "no moves" case)
        if (moves == null || moves.size() == 0) {
            if (choice == 12) {
                prettyPrintln("Resorting to fists\n");
                return punch;
            }
            //bs move to fill the void
            prettyPrintln("No moves available. Resorting to fists\n");
            return punch;
        }

        // error handling
        if (choice < 1 || choice > moves.size()) {
            prettyPrintln("[INVALID]");
            return playerFunc.chooseMove();
        }

        //edge case mainly for testing
        Move selectedMove = moves.getAtIndex(choice - 1);
        if (selectedMove == null) {
            prettyPrintln("Resorting to fists\n");
            return punch;
        }

        return selectedMove;
    }

    public void logMessage(String message){
        CustomArrayList<String> messageLog = gameState.getMessageLog();
        messageLog.add(message);
        gameState.setMessageLog(messageLog);
    }

    public void renderScreen() {
        prettyPrint("[CLR]");

        //Top Banner: Current Room & Level
        Room currentRoom = gameState.getCurrentRoom();
        prettyPrintln("========================================");
        prettyPrintln("[BLD]" + currentRoom.getName() + " | Level " + gameState.getLevelCount() + "[BRK]");
        prettyPrintln("========================================");

        displayStats();

        //If an enemy is in the room, show Enemy HUD right below Player HUD
        if (gameState.isEnemyInRoom() && !currentRoom.getIsExit()) {
            Enemy enemy = gameState.getCurrentRoomEnemy();
            prettyPrintln("----------------------------------------");
            prettyPrintln("[BLD][R]VS: " + enemy.getName() + " | HP: " + enemy.getHealth() + "[BRK]");
        }
        prettyPrintln("----------------------------------------");

        //Print the players messages
        for (String msg : gameState.getMessageLog()) {
            prettyPrintln(" > " + msg);
        }
        gameState.clearMessageLog();
        prettyPrintln("----------------------------------------");
    }
}
