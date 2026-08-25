package ui;

/**
 * Library for storing command line arguments to be used with the pretty print method
 */
public class GameFormatter {
    //Clear
    public static final String RESET = "\u001B[0m";
    public static final String CLEAR_TERMINAL = "\033[H\033[2J";

    //Text Colors
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    //Text Formatting
    public static final String BOLD = "\u001B[1m";
    public static final String ITALICS = "\u001B[3m";

    //Messages
    public static final String EMPTY_INVENTORY = "\u001B[1m\u001B[31mYou have no items in your inventory!\u001B[0m\n";
    public static final String INVALID_CHOICE = "\u001B[1m\u001B[31mInvalid choice\u001B[0m\n";
}
