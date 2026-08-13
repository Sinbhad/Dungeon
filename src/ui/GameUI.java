package ui;

public class GameUI {
    public void prettyPrint(String s){
        String coloredString = s
                .replace("[R]", GameFormatter.RED)
                .replace("[G]", GameFormatter.GREEN)
                .replace("[Y]", GameFormatter.YELLOW)
                .replace("[B]", GameFormatter.BLUE)
                .replace("[P]", GameFormatter.PURPLE)
                .replace("[C]", GameFormatter.CYAN)
                .replace("[BLD]", GameFormatter.BOLD)
                .replace("[BRK]", GameFormatter.RESET);

        System.out.println(coloredString);
    }
}
