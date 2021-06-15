/**
 * Copyright 2021 rahulstech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rahulstech.swing.calculator.history;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles history storage related operations.
 *
 * @author Rahul Bagchi
 */
public class HistoryStorage {

    private static final File HISTORY_FILE = new File("storage/history.txt");

    private final static List<HistoryEntry> history = new ArrayList<>();

    private HistoryStorage() {}

    /**
     * Add a new entry to the history. This method does not
     * write the entry to the file, you need to call
     * {@link HistoryStorage#saveHistory() saveHistory()} to
     * store the history persistently.
     *
     * @param entry the new entry to add
     * @see #saveHistory()
     */
    public static void addToHistory(HistoryEntry entry) {
        history.add(entry);
    }

    /**
     * Returns list of history entries currently avalilable. Call
     * {@link #loadHistory() loadHistory()} to load all history
     * entries from the file
     *
     * @return non null instance of {@link List} of {@link HistoryEntry}
     */
    public static List<HistoryEntry> getHistory() {
        return history;
    }

    /**
     * Remove all history entries currently available. You need to
     * call {@link #saveHistory() saveHistory()} to write changes to
     * the file
     */
    public static void clearHistory() {
        try {
            HISTORY_FILE.deleteOnExit();
            history.clear();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads history entries from the file. Call this method before adding
     * new history entry
     */
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

    /**
     * Write changes to the file.
     */
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
