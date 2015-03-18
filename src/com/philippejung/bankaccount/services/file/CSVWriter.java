package com.philippejung.bankaccount.services.file;

import com.philippejung.bankaccount.models.Currency;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Date;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 18/03/15.
 */
public class CSVWriter {
    private String separator;
    private String delimiter;
    private PrintStream printStream;
    private Boolean first;

    public CSVWriter(String filePath, String separator, String delimiter) throws FileNotFoundException {
        this.separator = separator;
        this.delimiter = delimiter;
        printStream = new PrintStream(new FileOutputStream(filePath, false));
        first = true;
    }

    private void addSeparator() {
        if (first)
            first = false;
        else
            printStream.print(separator);
    }

    private String protect(String value) {
        String retVal = value.replaceAll(delimiter, delimiter + delimiter);
        if (retVal.contains(separator))
            return delimiter + retVal + delimiter;
        else
            return retVal;
    }

    public void writeLong(Long value) {
        addSeparator();
        if (value!=null)
            printStream.print(value);
    }

    public void writeString(String value) {
        addSeparator();
        if (value!=null)
            printStream.print(protect(value));
    }

    public void writeCurrency(Currency value) {
        addSeparator();
        if (value!=null)
            printStream.print(protect(value.toString()));
    }

    public void writeDate(Date value) {
        addSeparator();
        if (value!=null)
            printStream.print(protect(value.toString()));
    }

    public void writeBoolean(Boolean value) {
        addSeparator();
        if (value!=null)
            printStream.print(value ? "1" : "0");
    }

    public void writeInt(Integer value) {
        addSeparator();
        if (value!=null)
            printStream.print(value);
    }

    public void next() {
        first = true;
        printStream.println();
    }

    public void writeId(Long value) {
        addSeparator();
        if (value != -1L && value != null)
            printStream.print(value);
    }
}
