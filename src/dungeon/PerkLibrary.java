package dungeon;

public class PerkLibrary {
    //Defense
    public final Perks[] DEFENSE_PERKS = {
            new Perks("[BLD]Slight Defense Boost[BRK]", "Defense", " increases defense by 10: [BLD][Y]200[BRK] coins", .1, 200),
            new Perks("[BLD]Decent Defense Boost[BRK]", "Defense", " increases defense by 20: [BLD][Y]400[BRK] coins", .2, 400),
            new Perks("[BLD]Great Defense Boost[BRK]", "Defense", " increases defense by 30: [BLD][Y]600[BRK] coins", .3, 600),
    };

    //Health
    public final Perks[] HEALTH_PERKS = {
            new Perks("[BLD]Band-aid[BRK]", "Health", " increases max health by 50 points: [BLD][Y]200[BRK] coins", 50, 200),
            new Perks("[BLD]Healing Potion[BRK]", "Health", " increases max health by 100 points: [BLD][Y]400[BRK] coins", 100, 400),
            new Perks("[BLD]Ultimate Healing Potion[BRK]", "Health", " increases max health by 200 points: [BLD][Y]600[BRK] coins", 200, 600),
    };

    //Damage
    public final Perks[] DAMAGE_PERKS = {
            new Perks("[BLD]Can of Spinach[BRK]", "Damage", " increases damage by 10: [BLD][Y]200[BRK] coins", 10, 200),
            new Perks("[BLD]Strength Training[BRK]", "Damage", " increases damage by 30: [BLD][Y]400[BRK] coins", 30, 400),
            new Perks("[BLD]Steroids[BRK]", "Damage", " increases damage by 80: [BLD][Y]1000[BRK] coins", 80, 1000),
    };

    //Speed
    public final Perks[] SPEED_PERKS = {
      new Perks("Running Shoes", "Speed", " increases speed by 10: 200 coins", 10, 200),
      new Perks("Weight Loss Pill", "Speed", " increases speed by 20: 400 coins", 20, 400),
      new Perks("Wings of Hermes", "Speed", " increases speed by 50: 600 coins", 50, 1000),
    };

    //Stamina
    public final Perks[] STAMINA_PERKS = {
            new Perks("Iron Lung", "Stamina", " increases stamina by 40: 200 coins", 10, 200),
            new Perks("Peptides", "Stamina", " increases stamina by 100: 400 coins", 20, 400),
            new Perks("Beet Powder", "Stamina", " increases stamina by 180: 600 coins", 30, 600),
    };
}
