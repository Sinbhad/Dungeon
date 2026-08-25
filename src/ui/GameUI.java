package ui;

public class GameUI <T>{
    /**
     * Used to provide a better UX with customizable styling options pulled from the GameFormatter Library
     * @param value Passed in string to be manipulated
     */
    public void prettyPrint(T value){
        String prettyString = prettyStringFormatter(value);
        System.out.print(prettyString);
    }

    public void prettyPrintln(T value){
        String prettyString = prettyStringFormatter(value);
        System.out.println(prettyString);
    }

    public String prettyStringFormatter(T value){
        String coloredString;
        coloredString = value.toString()
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

        return coloredString;
    }
}
