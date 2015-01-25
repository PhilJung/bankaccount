package com.philippejung.data;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * Created by philippe on 21/01/15.
 */
public class DatabaseAccess {

    Connection connection = null;

    // This table contains all requests to be applied from an empty database to create the most
    // up to date database.
    private final static String[] ALL_UPDATES = new String[]{
            null,
            "CREATE TABLE IF NOT EXISTS schema_version (id INTEGER PRIMARY KEY NOT NULL, " +
                    "applicationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            "CREATE TABLE account (id INTEGER PRIMARY KEY NOT NULL, name TEXT)",
            "ALTER TABLE account ADD COLUMN accountNumber TEXT",
            "CREATE TABLE movement (id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE, type INTEGER NOT NULL, " +
                    "otherAccountId INTEGER, otherTransactionId INTEGER, wayOfPayment INTEGER, amount REAL," +
                    "detail TEXT, comment TEXT)",
            "CREATE TABLE wayOfPayment (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)",
            "INSERT INTO wayOfPayment (name) VALUES ('CB Hellobank'), ('CB LBP'), ('Cheque Hellobank'), " +
                    "('Cheque LBP'), ('Paypal')"
    };

    public DatabaseAccess(String pathToDB) throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        openDB(pathToDB);
        checkForUpdates();
    }

    private void handleException(SQLException exc) {
        String msg =
                "Error code: " + exc.getErrorCode() + "\n" +
                "SQL State: " + exc.getSQLState() + "\n" +
                "Message: " + exc.getMessage() + "\n" +
                "Application will quit.";
        JOptionPane.showMessageDialog(null, msg, "Database error", ERROR_MESSAGE);
        System.exit(0);
    }

    private int executeUpdate(String update) {
        int retVal = 0;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            retVal = statement.executeUpdate(update);
        } catch (SQLException exc) {
            handleException(exc);
        } finally {
            if (statement!=null) try {
                statement.close();
            } catch (SQLException exc) {
                // Do nothing
            }
        }
        return retVal;
    }

    private int selectOneNumber(String request) {
        int retVal = -1;
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(request);
            if (rs.next()) {
                retVal = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException exc) {
            handleException(exc);
        }
        return retVal;
    }

    private void checkForUpdates() {
        int currentVersion = selectOneNumber("SELECT MAX(id) FROM schema_version");
        System.out.println("Current schema version: " + currentVersion);
        if (ALL_UPDATES.length > currentVersion) {
            for (int id=currentVersion+1 ; id<ALL_UPDATES.length; id++) {
                // Apply allUpdates[id]
                System.out.println("Applying " + ALL_UPDATES[id]);
                executeUpdate(ALL_UPDATES[id]);
                executeUpdate("INSERT INTO schema_version (id) VALUES (" + Integer.toString(id) + ")");
            }
        }
    }

    private void openDB(String pathToDB) {
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + pathToDB + "bankaccount.db");
            executeUpdate(ALL_UPDATES[1]);
            executeUpdate("INSERT OR IGNORE INTO schema_version (id) VALUES (1)");
        } catch (SQLException exc) {
            handleException(exc);
        }
    }

    public void closeDB() {
        try {
            connection.close();
        } catch (SQLException exc) {
            handleException(exc);
        }
        connection = null;
    }

}
