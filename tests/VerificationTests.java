import dungeon.HighScoreDB;
import lib.CustomArrayList;
import lib.CustomCircularlyLinkedList;
import lib.Node;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class VerificationTests {
    private static int checks;

    public static void main(String[] args) throws Exception {
        testCustomArrayList();
        testCustomCircularlyLinkedList();
        testHighScoreDatabase();
        System.out.println("Passed " + checks + " verification checks.");
    }

    private static void testCustomArrayList() {
        CustomArrayList<Integer> values = new CustomArrayList<>();
        check(values.size() == 0, "new array list is empty");
        check(!values.find(1), "find on an empty array list");

        for (int i = 0; i < 25; i++) {
            values.add(i);
        }
        check(values.size() == 25, "array list expands beyond its initial capacity");
        check(values.getAtIndex(24) == 24, "array list retains values after expansion");

        values.addAtIndex(0, -1);
        values.addAtIndex(values.size(), 25);
        values.addAtIndex(5, 99);
        check(values.getAtIndex(0) == -1, "insert at the beginning");
        check(values.getAtIndex(5) == 99, "insert in the middle");
        check(values.getAtIndex(values.size() - 1) == 25, "insert at the end");

        values.replaceAtIndex(5, 500);
        check(values.find(500), "find existing value");
        check(values.findCount(500) == 1, "count occurrences");
        values.remove(500);
        values.removeAtIndex(0);
        check(!values.find(500), "remove by value");
        check(values.getAtIndex(0) == 0, "remove by index shifts values");

        CustomArrayList<Integer> copy = values.cloneClass();
        copy.add(100);
        check(values.size() + 1 == copy.size(), "clone has independent storage");
        values.clear();
        check(values.size() == 0, "clear resets size");

        Iterator<Integer> iterator = copy.iterator();
        int iterated = 0;
        while (iterator.hasNext()) {
            iterator.next();
            iterated++;
        }
        check(iterated == copy.size(), "iterator visits each element");
        expectThrows(NoSuchElementException.class, iterator::next, "iterator rejects exhausted reads");
        expectThrows(IndexOutOfBoundsException.class, () -> copy.getAtIndex(-1), "negative index rejected");
        expectThrows(IndexOutOfBoundsException.class, () -> copy.getAtIndex(copy.size()), "index past end rejected");
    }

    private static void testCustomCircularlyLinkedList() {
        CustomCircularlyLinkedList<String> values = new CustomCircularlyLinkedList<>();
        check(values.getSize() == 0, "new circular list is empty");
        check(values.findIndex("missing") == -1, "findIndex on an empty circular list");
        check(!values.find("missing"), "find on an empty circular list");

        values.add("middle");
        values.addAtIndex(0, "first");
        values.addAtIndex(2, "last");
        values.addAtIndex(1, null);
        check(values.getSize() == 4, "insertions update circular-list size");
        check(values.getValAtIndex(0).equals("first"), "insert at circular-list head");
        check(values.getValAtIndex(1) == null, "circular list stores null values");
        check(values.find(null), "find null value");
        check(values.findIndex(null) == 1, "find index of null value");
        check(values.findIndex("last") == 3, "find index of final value");

        Node<String> head = values.getHead();
        Node<String> tail = values.getLast();
        check(head.getLastNode() == tail && tail.getNextNode() == head, "head and tail remain circular");
        check(head.getNextNode().getLastNode() == head, "forward and backward links agree");

        values.remove((String) null);
        check(!values.find(null) && values.getSize() == 3, "remove null value");
        values.removeAt(0);
        check(values.getValAtIndex(0).equals("middle"), "remove head");
        values.removeAt(values.getSize() - 1);
        check(values.getLast().getValue().equals("middle"), "remove tail");
        values.removeAt(0);
        check(values.getSize() == 0, "remove final node");
        check(values.getHead() == null && values.getLast() == null, "empty list clears head and tail");
        values.add("again");
        values.clear();
        check(values.getSize() == 0, "clear circular list");
    }

    private static void testHighScoreDatabase() throws Exception {
        HighScoreDB database = new HighScoreDB();
        database.initializeDatabase();
        database.saveStats("Low", 10);
        database.saveStats("Middle", 20);
        database.saveStats("High", 30);
        database.saveStats("Top", 40);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            database.printHighScores();
        } finally {
            System.setOut(originalOutput);
        }
        String scores = output.toString(StandardCharsets.UTF_8);
        check(scores.contains("1: Top - 40"), "database prints highest score first");
        check(scores.contains("3: Middle - 20"), "database prints only top three scores");
        check(!scores.contains("Low - 10"), "database excludes scores below the top three");

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:dungeon_saves.db");
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM HighScores")) {
            check(results.next() && results.getInt(1) == 4, "database persists all inserted scores");
        }
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        checks++;
    }

    private static void expectThrows(Class<? extends Throwable> expected, Runnable action, String description) {
        try {
            action.run();
        } catch (Throwable actual) {
            if (expected.isInstance(actual)) {
                checks++;
                return;
            }
            throw new AssertionError(description + ": expected " + expected.getSimpleName()
                    + " but got " + actual.getClass().getSimpleName(), actual);
        }
        throw new AssertionError(description + ": expected " + expected.getSimpleName());
    }
}
