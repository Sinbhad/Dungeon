package ui;

public class GameUI {
    /**
     * Used to provide a better UX with customizable styling options pulled from the GameFormatter Library
     * @param s Passed in string to be manipulated
     */
    public void prettyPrint(String s){
        String coloredString = s
                .replace("[R]", GameFormatter.RED)
                .replace("[G]", GameFormatter.GREEN)
                .replace("[Y]", GameFormatter.YELLOW)
                .replace("[B]", GameFormatter.BLUE)
                .replace("[P]", GameFormatter.PURPLE)
                .replace("[C]", GameFormatter.CYAN)

                .replace("[BLD]", GameFormatter.BOLD)
                .replace("[ITL]", GameFormatter.ITALICS)

                .replace("[BRK]", GameFormatter.RESET);

        System.out.println(coloredString);
    }
}
