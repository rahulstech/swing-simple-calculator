package rahulstech.swing.calculator.history;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    private static List<HistoryEntry> histories = new ArrayList<>();

    public static void addToHistory(HistoryEntry entry) {
        histories.add(entry);
    }

    public static void clearHistory() {

    }

    public static void loadHistory() {
        try (BufferedReader reader = new BufferedReader(null)) {

        }
        catch (Exception e) {

        }
    }

    public static void saveHistory() {

    }
}
