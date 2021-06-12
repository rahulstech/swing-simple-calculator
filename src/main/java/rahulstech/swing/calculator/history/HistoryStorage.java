package rahulstech.swing.calculator.history;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    private static final File HISTORY_FILE = new File("storage/history.txt");

    private final static List<HistoryEntry> history = new ArrayList<>();

    private HistoryStorage() {}

    public static void addToHistory(HistoryEntry entry) {
        history.add(entry);
    }

    public static List<HistoryEntry> getHistory() {
        return history;
    }

    public static void clearHistory() {
        try {
            HISTORY_FILE.deleteOnExit();
            history.clear();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadHistory() {
        try (BufferedReader in = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while (null != (line = in.readLine())) {
                HistoryEntry entry = new HistoryEntry();
                entry.setExpression(line);
                entry.setResult(new BigDecimal(in.readLine()));
                in.readLine();
                history.add(entry);
            }
        }
        catch (FileNotFoundException ignore) {}
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveHistory() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (HistoryEntry entry : history) {
                out.write(entry.getExpression());
                out.newLine();
                out.write(entry.getResult().toString());
                out.newLine();
                out.newLine();
                out.flush();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
