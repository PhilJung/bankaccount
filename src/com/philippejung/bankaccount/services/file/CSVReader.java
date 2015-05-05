package com.philippejung.bankaccount.services.file;

import com.philippejung.bankaccount.models.Currency;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 16/03/15.
 */
public class CSVReader {
    private String separator;
    private String delimiter;

    private ArrayList<String> allTokens = new ArrayList<>();
    private int currentToken = 0;
    private ArrayList<String> currentLine;

    public CSVReader(String filePath, String separator, String delimiter, String testBuffer) {
        assert filePath == null;
        this.separator = separator;
        this.delimiter = delimiter;
        tokenize(testBuffer);
    }

    public CSVReader(String filePath, String separator, String delimiter) throws IOException {
        String buffer = "";
        buffer = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset());
        this.separator = separator;
        this.delimiter = delimiter;
        tokenize(buffer);
    }

    private void tokenize(String buffer) {
        String delim = separator + delimiter + "\r\n";
        StringTokenizer tokenizer = new StringTokenizer(buffer, delim, true);
        while (tokenizer.hasMoreTokens()) {
            allTokens.add(tokenizer.nextToken());
        }
    }

    public boolean hasNext() {
        return currentToken < allTokens.size();
    }

    public void next() {
        // Read one line
        String token;
        String currentValue = "";
        int index = 0;
        boolean insideDelimiter = false;
        boolean clean = true;
        currentLine = new ArrayList<>();

        int tokenSize = allTokens.size();
        while (currentToken < tokenSize ) {
            token = allTokens.get(currentToken);

            // End of line, jobs done if not inside delimiter
            if (token.equals("\n") && !insideDelimiter) {
                currentLine.add(index, currentValue);
                index++;
                currentValue = "";
                currentToken++;
                return;
            }
            // Separator, commit current value and get ready for next except if inside delimiters;
            else if (token.equals(separator) && !insideDelimiter) {
                currentLine.add(index, currentValue);
                index++;
                currentValue = "";
                currentToken++;
                clean = true;
            }
            // Double delimiter
            else if (token.equals(delimiter) && (currentToken+1<tokenSize) && allTokens.get(currentToken + 1).equals(delimiter)) {
                currentValue += delimiter;
                clean = false;
                currentToken += 2;
            }
            // Single delimiter
            else if (token.equals(delimiter)) {
                if (insideDelimiter) {
                    // we are getting out of delimitor
                    insideDelimiter = false;
                } else {
                    insideDelimiter = true;
                }
                // Whatever the case, the token has been eaten
                currentToken++;
            }
            // Regular char
            else {
                // Handle CR/LF conversion to LF only
                if (token.equals("\r") && !insideDelimiter && (currentToken+1<tokenSize) && allTokens.get(currentToken + 1).equals("\n")) {
                    // Skip it
                } else {
                    // Take it
                    currentValue = currentValue + token;
                    clean = false;
                }
                currentToken++;
            }
        }
        // We reach end of buffer so save the last uncommited value
        if (!clean) {
            currentLine.add(index, currentValue);
        }
        // File syntax error should be handled at application level and not through assert
        assert !insideDelimiter;
    }

    public Date getDate(int index) {
        if (index >= currentLine.size()) return null;
        if (currentLine.get(index).isEmpty()) return null;
        return Date.valueOf(currentLine.get(index));
    }

    public Integer getInt(int index) {
        if (index >= currentLine.size()) return null;
        if (currentLine.get(index).isEmpty()) return null;
        return Integer.valueOf(currentLine.get(index));
    }

    public Long getLong(int index) {
        if (index >= currentLine.size()) return null;
        if (currentLine.get(index).isEmpty()) return null;
        return Long.valueOf(currentLine.get(index));
    }

    public String getString(int index) {
        if (index >= currentLine.size()) return null;
        return currentLine.get(index);
    }

    public Boolean getBoolean(int index) {
        if (index >= currentLine.size()) return null;
        String str = currentLine.get(index);
        if (str.equalsIgnoreCase("true") || str.equals("1")) return true;
        if (str.equalsIgnoreCase("false") || str.equals("0")) return false;
        return null;
    }

    public Currency getCurrency(int index) {
        return Currency.fromString(getString(index));
    }

    public Long getId(int index) {
        if (index >= currentLine.size()) return null;
        if (currentLine.get(index).isEmpty()) return -1L;
        return Long.valueOf(currentLine.get(index));
    }
}
