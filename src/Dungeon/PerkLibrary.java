package Dungeon;

public class PerkLibrary {
    //Defense
    public final Perks[] DEFENSE_PERKS = {
            new Perks("Slight Defense Boost", "Defense", " increases defense by 10: 200 coins", .1, 200),
            new Perks("Decent Defense Boost", "Defense", " increases defense by 20: 400 coins", .2, 400),
            new Perks("Great Defense Boost", "Defense", " increases defense by 30: 600 coins", .3, 600),
    };

    //Health
    public final Perks[] HEALTH_PERKS = {
            new Perks("Band-aid", "Health", " increases health by 50: 200 coins", 50, 200),
            new Perks("Healing Potion", "Health", " increases health by 100: 400 coins", 100, 400),
            new Perks("Ultimate Healing Potion", "Health", " increases health by 200: 600 coins", 200, 600),
    };

    //Damage
    public final Perks[] DAMAGE_PERKS = {
            new Perks("Can of Spinach", "Damage", " increases damage by 10: 200 coins", 10, 200),
            new Perks("Strength Training", "Damage", " increases damage by 30: 400 coins", 30, 400),
            new Perks("Steroids", "Damage", " increases damage by 80: 1000 coins", 80, 1000),
    };

    //Speed
    public final Perks[] SPEED_PERKS = {
      new Perks("Running Shoes", "Speed", " increases speed by 10: 200 coins", 10, 200),
      new Perks("Weight Loss Pill", "Speed", " increases speed by 20: 400 coins", 20, 400),
      new Perks("Wings of Hermes", "Speed", " increases speed by 50: 600 coins", 50, 1000),
    };
}
