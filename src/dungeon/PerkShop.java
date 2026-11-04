package dungeon;

import characters.Character;
import characters.Player;
import ui.GameUI;

import java.util.Random;

public class PerkShop {
    private final GameState gameState;
    private final Player player;
    private GameUI gameUI;

    public PerkLibrary perkLibrary;
    public int damageIndex, defenseIndex, healthIndex, speedIndex, staminaIndex;
    public Perk damagePerk, defensePerk, healthPerk, speedPerk, staminaPerk;
    public Random random = new Random();


    public PerkShop(){
        this.gameState = new GameState();
        this.player = new Player();
        setPerks();
    }

    public PerkShop(GameState gameState){
        this.gameState = gameState;
        this.player = gameState.getPlayer();
        setPerks();
    }

    /**
     * Sets the available perks in the shop based on the amount of indexes available in each category
     */
    public void setPerks(){
        //Determine which perks will be available
        speedIndex = random.nextInt(perkLibrary.SPEED_PERKS.length);
        Perk speedPerk = perkLibrary.SPEED_PERKS[speedIndex];
        defenseIndex = random.nextInt(perkLibrary.DEFENSE_PERKS.length);
        Perk defensePerk = perkLibrary.DEFENSE_PERKS[defenseIndex];
        healthIndex = random.nextInt(perkLibrary.HEALTH_PERKS.length);
        Perk healthPerk = perkLibrary.HEALTH_PERKS[healthIndex];
        damageIndex = random.nextInt(perkLibrary.DAMAGE_PERKS.length);
        Perk damagePerk = perkLibrary.DAMAGE_PERKS[damageIndex];
        staminaIndex = random.nextInt(perkLibrary.STAMINA_PERKS.length);
        Perk staminaPerk = perkLibrary.STAMINA_PERKS[staminaIndex];
    }

    /**
     * Applies the stat boosts to the current player based on the selected perk
     * @param choice the players selected perk number
     * @return uses a helper method to inform the user why their choice was invalid and returns a flag a loop depends on
     */
    public boolean applyChoice(int choice) {
        if (choice == 0) {
            gameUI.prettyPrintln("Moving on then, good luck!\n\n");
            return true;
        }

        switch (choice) {
            case 1 -> {
                if (!checkBread(player, speedPerk)) return handleInvalid();
                player.setSpeedValue((int) (player.getSpeed() + speedPerk.getValue()));
            }
            case 2 -> {
                if (!checkBread(player, defensePerk)) return handleInvalid();
                if(!applyDefensePerk()) return false;
            }
            case 3 -> {
                if (!checkBread(player, healthPerk)) return handleInvalid();
                player.setMaxHealth(player.getMaxHealth() + healthPerk.getValue());
            }
            case 4 -> {
                if (!checkBread(player, damagePerk)) return handleInvalid();
                player.setAttackValue((int) (player.getAttack() + damagePerk.getValue()));
            }
            case 5 -> {
                if (!checkBread(player, staminaPerk)) return handleInvalid();
                player.setStamina((int) (player.getStamina() + staminaPerk.getValue()));
            }
            case 6 -> {
                if (player.getCoins() < 100) return handleInvalid();
                player.setCoins(player.getCoins() - 100);
            }
            default -> {
                return handleInvalid();
            }
        }
        return true;
    }


    /**
     *Helper method to keep defense logic from cluttering the main switch
     */
    private boolean applyDefensePerk() {
        if (player.getTotalDefense() >= 0.8) {
            gameUI.prettyPrintln("[BLD][R]You have already reached maximum defense, choose a different perk or move on[BRK]");
            return false;
        }

        player.setTotalDefense(player.getArmorDefense(), player.getPerkDefense() + defensePerk.getValue());

        if (player.getTotalDefense() > 0.8) {
            player.setTotalDefense(0.8, 0);
            gameUI.prettyPrintln("[BLD][C]Your defense value would exceed 80%, you have been set to 80% :([BRK]");
        }
        return true;
    }

    /**
     *Helper method to remove repetitive invalid-input UI logic
     */
    private boolean handleInvalid() {
        gameUI.prettyPrintln("[INVALID]");
        return false;
    }


    /**
     * Helper class to determine if the player has enough coins to complete their current selection in the perk menu
     * @param character player character
     * @param perk perk object
     */
    boolean checkBread(Character character, Perk perk){
        if(character.getCoins() >= perk.getCost()){
            character.setCoins(character.getCoins() - perk.getCost());
            return true;
        }else{
            gameUI.prettyPrintln("[BLD][R]You do not have enough coins to buy this perk![BRK]");
            return false;
        }
    }

    /**
     * Shows the player a formatted list of what is for sale at the current moment
     */
    public void displayChoices(){
        gameUI.prettyPrintln(
                "[BLD][C]Choose a perk[BRK]" +
                        "\n[C]1.[BRK] :[BLD]" + speedPerk.getPerkName() + "[BRK] - [ITL]" + speedPerk.getDescription() + "[BRK]" +
                        "\n[C]2.[BRK] :[BLD]" + defensePerk.getPerkName() + "[BRK] - [ITL]" + defensePerk.getDescription() + "[BRK]" +
                        "\n[C]3.[BRK] :[BLD]" + healthPerk.getPerkName() + "[BRK] - [ITL]" + healthPerk.getDescription() + "[BRK]" +
                        "\n[C]4.[BRK] :[BLD]" + damagePerk.getPerkName() + "[BRK] - [ITL]" + damagePerk.getDescription() + "[BRK]" +
                        "\n[C]5.[BRK] :[BLD]" + staminaPerk.getPerkName() + "[BRK] - [ITL]" + staminaPerk.getDescription() + "[BRK]" +
                        "\n[C]6.[BRK] :[BLD]Reroll for [C]100[BRK][BLD] coins[BRK]\n\n [ITL]" +
                        "\nYou have [BLD][Y]" + player.getCoins() + "[BRK] coins");

        gameUI.prettyPrint("Enter your choice: [C](1-6)[BRK] [R][BLD]'0 to exit'[BRK]: ");
    }
}