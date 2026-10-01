package ui;

import characters.Move;
import characters.Player;
import characters.PlayerFunctions;
import dungeon.GameState;
import items.Weapon;
import lib.CustomArrayList;

import java.util.Scanner;
/**
 * Used to provide a better UX with customizable styling options pulled from the GameFormatter Library
 */
public class GameUI{
    private final Scanner keyboard = new Scanner(System.in);

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
    public void displayStats(GameState gameState){
        String healthFormatString = "[G]";
        Player player = gameState.getPlayer();

        //if a weapon has not yet been acquired, the game wil build a default placeholder
        String weaponName = (player.getWeapon() != null) ? player.getWeapon().getName() : "Fists";
        //if armor has not yet been acquired, the game wil build a default placeholder
        String armorName = (player.getArmor() != null) ? player.getArmor().getName() : "Naked";

        //Color of health points determined by player health < 50% == red || > 50% == green
        if(player.getMaxHealth() / player.getHealth() < 0.5){
            healthFormatString = "[R]";
        }

        prettyPrintln(
                "\n[ITL][P]" + player.getName() + "[BRK]" +
                        "\n[BLD]Health Points: " + healthFormatString + player.getHealth() + "[BRK]" +
                        "\n[BLD]Total Attack: [BRK][P]" + player.getTotalAttack() + "[BRK]" +
                        "\n[BLD]Weapon: [ITL][C]" + weaponName + "[BRK]" +
                        "\n[BLD]Armor: [ITL][C]" + armorName + "[BRK]" +
                        "\n[BLD]Coins: [Y]" + player.getCoins() + "[BRK]");


        prettyPrintln("\n\n");
    }

    public Move returnSelectedMoveFormatted(GameState gameState, int choice) {
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
}
