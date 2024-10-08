/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author anil
 */
public class CommandHistory {
    protected List history = new ArrayList();

    protected String charset = "UTF-8";
    protected PrintStream output = null;

    protected int maxSize = 500;

    protected int currentIndex = 0;

    /**
     * Construstor: initialize a blank history.
     */
    public CommandHistory() {
    }

    /**
     * Construstor: initialize History object the the specified {@link File} for
     * storage.
     */
    public CommandHistory(final File historyFile, String charset) throws IOException {
        setHistoryFile(historyFile, charset);
    }

    public void setHistoryFile(final File historyFile, String charset) throws IOException {
        if (historyFile.isFile()) {
            load(new FileInputStream(historyFile), charset);
        }

        setOutput(new PrintStream(historyFile, charset));
        flushBuffer();
    }

    /**
     * Load the history buffer from the specified InputStream.
     */
    public void load(final InputStream in, String charset) throws IOException {
        this.charset = charset;
        load(new InputStreamReader(in, charset));
    }

    /**
     * Load the history buffer from the specified Reader.
     */
    public void load(final Reader reader) throws IOException {
        BufferedReader breader = new BufferedReader(reader);
        List lines = new ArrayList();
        String line;

        while ((line = breader.readLine()) != null) {
            lines.add(line);
        }

        for (Iterator i = lines.iterator(); i.hasNext();) {
            addToHistory((String) i.next());
        }
    }

    public int size() {
        return history.size();
    }

    /**
     * Clear the history buffer
     */
    public void clear() {
        history.clear();
        currentIndex = 0;
    }

    /**
     * Add the specified buffer to the end of the history. The pointer is set to
     * the end of the history buffer.
     */
    public void addToHistory(final String buffer) {
        // don't append duplicates to the end of the buffer
        if ((history.size() != 0)
                && buffer.equals(history.get(history.size() - 1))) {
            return;
        }

        history.add(buffer);

        while (history.size() > getMaxSize()) {
            history.remove(0);
        }

        currentIndex = history.size();

        if (getOutput() != null) {
            getOutput().println(buffer);
            getOutput().flush();
        }
    }

    /**
     * Flush the entire history buffer to the output PrintWriter.
     */
    public void flushBuffer() throws IOException {
        if (getOutput() != null) {
            for (Iterator i = history.iterator(); i.hasNext(); getOutput()
                    .println((String) i.next())) {
                ;
            }

            getOutput().flush();
        }
    }

    /**
     * This moves the history to the last entry. This entry is one position
     * before the moveToEnd() position.
     *
     * @return Returns false if there were no history entries or the history
     *         index was already at the last entry.
     */
    public boolean moveToLastEntry() {
        int lastEntry = history.size() - 1;
        if (lastEntry >= 0 && lastEntry != currentIndex) {
            currentIndex = history.size() - 1;
            return true;
        }

        return false;
    }

    /**
     * Move to the end of the history buffer. This will be a blank entry, after
     * all of the other entries.
     */
    public void moveToEnd() {
        currentIndex = history.size();
    }

    /**
     * Set the maximum size that the history buffer will store.
     */
    public void setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Get the maximum size that the history buffer will store.
     */
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * The output to which all history elements will be written (or null of
     * history is not saved to a buffer).
     */
    public void setOutput(final PrintStream output) {
        this.output = output;
    }

    /**
     * Returns the PrintStream that is used to store history elements.
     */
    public PrintStream getOutput() {
        return this.output;
    }

    /**
     * Returns the current history index.
     */
    public int getCurrentIndex() {
        return this.currentIndex;
    }

    /**
     * Return the content of the current buffer.
     */
    public String current() {
        if (currentIndex >= history.size()) {
            return "";
        }

        return (String) history.get(currentIndex);
    }

    /**
     * Move the pointer to the previous element in the buffer.
     *
     * @return true if we successfully went to the previous element
     */
    public boolean previous() {
        if (currentIndex <= 0) {
            return false;
        }

        currentIndex--;

        return true;
    }

    /**
     * Move the pointer to the next element in the buffer.
     *
     * @return true if we successfully went to the next element
     */
    public boolean next() {
        if (currentIndex >= history.size()) {
            return false;
        }

        currentIndex++;

        return true;
    }

    /**
     * Returns an immutable list of the history buffer.
     */
    public List getHistoryList() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Returns the standard {@link AbstractCollection#toString} representation
     * of the history list.
     */
    public String toString() {
        return history.toString();
    }

    /**
     * Moves the history index to the first entry.
     *
     * @return Return false if there are no entries in the history or if the
     *         history is already at the beginning.
     */
    public boolean moveToFirstEntry() {
        if (history.size() > 0 && currentIndex != 0) {
            currentIndex = 0;
            return true;
        }

        return false;
    }
}
