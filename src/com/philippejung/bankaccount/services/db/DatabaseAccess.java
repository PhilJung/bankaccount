package com.philippejung.bankaccount.services.db;

import com.philippejung.bankaccount.models.dao.RootDAO;
import com.philippejung.bankaccount.view.utils.AlertPopup;
import com.philippejung.bankaccount.view.utils.Joiner;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 21/01/15.
 */
public class DatabaseAccess {

    private Connection connection = null;
    private final String pathToDB;

    // This table contains all requests to be applied from an empty database to create the most
    // up to date database.
    private final static String[] ALL_UPDATES = new String[]{
            null,
            "CREATE TABLE IF NOT EXISTS schema_version (" +
                    "id INTEGER PRIMARY KEY NOT NULL, "  +
                    "applicationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            "CREATE TABLE account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT," +
                    "initialBalance REAL," +
                    "importerFormat TEXT," +
                    "accountNumber TEXT)",
            "CREATE TABLE [transaction] (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "date DATE, " +
                    "type INTEGER NOT NULL, " +
                    "accountId INTEGER, " +
                    "otherAccountId INTEGER, " +
                    "otherTransactionId INTEGER, " +
                    "categoryId INTEGER, " +
                    "wayOfPaymentId INTEGER," +
                    "amount REAL," +
                    "detail TEXT," +
                    "comment TEXT)",
            "CREATE TABLE wayOfPayment (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT)",
            "CREATE TABLE category (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "isexpense INTEGER)",
            "CREATE TABLE classifier (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "detailConditionTest TEXT," +
                    "detailConditionValue TEXT," +
                    "amountConditionTest TEXT," +
                    "amountConditionValue REAL," +
                    "newTypeId INTEGER," +
                    "newWayOfPaymentId INTEGER," +
                    "newOtherAccountId INTEGER," +
                    "newCategoryId INTEGER, " +
                    "stopFurtherClassification INTEGER)",
            "INSERT INTO wayOfPayment VALUES (1, 'CB Hellobank')",
            "INSERT INTO wayOfPayment VALUES (2, 'CB LBP')",
            "INSERT INTO wayOfPayment VALUES (3, 'Cheque Hellobank')",
            "INSERT INTO wayOfPayment VALUES (4, 'Cheque LBP')",
            "INSERT INTO wayOfPayment VALUES (5, 'Paypal')",
            "INSERT INTO wayOfPayment VALUES (6, 'Prélèvement LBP')",
            "INSERT INTO account VALUES (1, 'Compte courant La Banque Postale', 0.0, 'lbp', '0000');",
            "INSERT INTO account VALUES (2, 'Compte courant Hellobank', 0.0, 'lbp', '0000');",
            "INSERT INTO category VALUES (1, 'Salaire', 0)",
            "INSERT INTO category VALUES (2, 'Divers', 0)",
            "INSERT INTO category VALUES (3, 'Alimentation', 1)",
            "INSERT INTO category VALUES (4, 'Facture', 1)",
            "INSERT INTO category VALUES (5, 'Divers', 1)",
            "INSERT INTO category VALUES (6, 'Téléphonie', 1)",
            "INSERT INTO category VALUES (7, 'Impôts sur le revenu', 1)",
            "INSERT INTO classifier VALUES (1, null, null, '>', 0, 1, null, null, null, 0)",
            "INSERT INTO classifier VALUES (2, null, null, '<', 0, 2, null, null, null, 0)",
            "INSERT INTO classifier VALUES (3, 'startsWith', 'REMISE DE CHEQUE', null, 0, 1, null, null, 5, 0)",
            "INSERT INTO classifier VALUES (4, 'startsWith', 'VIREMENT POUR', null, 0, 3, null, null, null, 0)",
            "INSERT INTO classifier VALUES (5, 'startsWith', 'VIREMENT DE', null, 0, 4, null, null, null, 0)",
            "INSERT INTO classifier VALUES (6, 'contains', 'VIREMENT DE DRFIP', null, 0, 1, null, null, 1, 0)",
            "INSERT INTO classifier VALUES (7, 'contains', 'FREE MOBILE', null, 0, 2, 6, null, 6, 0)",
            "INSERT INTO classifier VALUES (8, 'contains', 'DIRECTION GENERAL', '==', 1503, 2, 6, null, 7, 0)",
    };

    public DatabaseAccess(String pathToDB) {
        this.pathToDB = pathToDB;
    }

    private void handleException(SQLException exc) {
        String msg =
                "Error code: " + exc.getErrorCode() + "\n" +
                "SQL State: " + exc.getSQLState() + "\n" +
                "Message: " + exc.getMessage() + "\n" +
                "Application will quit.";
        AlertPopup.alert(Alert.AlertType.ERROR, "Database error", "Exception occurred during database acces.", msg, exc);
        closeDB();
        Platform.exit();
    }

    private void checkConnection() {
        if (connection == null) {
            // load the sqlite-JDBC driver using the current class loader
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException exc) {
                AlertPopup.alert(
                        Alert.AlertType.ERROR, "Error", "Something prevents the program to continue",
                        "Impossible to load JDBC - Sqlite driver. Program will exit.", exc
                );
                Platform.exit();
            }
            openDB(pathToDB);
            checkForUpdates();
        }
    }

    private int executeUpdate(String update) {
        int retVal = 0;
        Statement statement = null;
        checkConnection();
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

    public <T extends RootDAO> ArrayList<T> select(String selector, Class<T> objectClass) {
        Statement statement = null;
        ArrayList<T> retVal = new ArrayList<>();
        ResultSet rs=null;
        checkConnection();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(selector);
            while (rs.next()) {
                T obj = objectClass.newInstance();
                obj.readFromDB(rs);
                retVal.add(obj);
            }
        } catch (SQLException exc) {
            handleException(exc);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) try {
                rs.close();
            } catch (SQLException exc) {
                // Do nothing
            }
            if (statement!=null) try {
                statement.close();
            } catch (SQLException exc) {
                // Do nothing
            }
        }
        return retVal;
    }

    public <T extends RootDAO> T findById(long id, Class<T> objectClass) {
        Statement statement = null;
        ResultSet rs=null;
        checkConnection();
        T retVal = null;
        try {
            retVal = objectClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
            Method method = null;
            String tableName = null;
            method = objectClass.getMethod("getTableName", null);
            tableName = (String) method.invoke(retVal);
            rs = statement.executeQuery("SELECT * FROM " + tableName + " WHERE id=" + id);
            if (rs.next()) {
                retVal.readFromDB(rs);
            }
        }
        catch (NoSuchMethodException ignored) { retVal = null; }
        catch (InvocationTargetException ignored) { retVal = null; }
        catch (IllegalAccessException ignored) { retVal = null; }
        catch (SQLException exc) {
            handleException(exc);
            retVal = null;
        } finally {
            if (rs!=null) try {
                rs.close();
            } catch (SQLException ignored) {}
            if (statement!=null) try {
                statement.close();
            } catch (SQLException ignored) {}
        }
        return retVal;
    }

    private int selectOneNumber(String request) {
        int retVal = -1;
        checkConnection();
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
        if (ALL_UPDATES.length > currentVersion + 1) {
            beginTransaction();
            for (int id=currentVersion+1 ; id<ALL_UPDATES.length; id++) {
                // Apply allUpdates[id]
                System.out.println("Applying " + ALL_UPDATES[id]);
                executeUpdate(ALL_UPDATES[id]);
                executeUpdate("INSERT INTO schema_version (id) VALUES (" + Integer.toString(id) + ")");
            }
            commitTransaction();
        }
    }

    private void openDB(String pathToDB) {
        // Ensure path exists
        File dir = new File(pathToDB);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
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

    public Long insert(String tableName, Map<String, Object> fieldValues) {
        Long newId = -1L;
        String request =
                "INSERT INTO " + tableName + " (" +
                        Joiner.join(",", fieldValues.keySet(), false) +
                        ") VALUES (" +
                        Joiner.join(",", fieldValues.values(), "?") +
                        ")";
        PreparedStatement statement = null;
        checkConnection();
        try {
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            addParams(statement, fieldValues.values());
            int affectedRows = statement.executeUpdate();
            if (affectedRows==0) {
                throw new SQLException("INSERT statement failed. No row modified.");
            }
            ResultSet genKeys = statement.getGeneratedKeys();
            if (genKeys.next()) {
                newId = genKeys.getLong(1);
            } else {
                throw new SQLException("INSERT statement returned no id.");
            }
        } catch (SQLException exc) {
            handleException(exc);
        } finally {
            if (statement!=null) try {
                statement.close();
            } catch (SQLException exc) {
                // Do nothing
            }
        }
        return newId;
    }

    private void addParams(PreparedStatement statement, Collection<Object> values) throws SQLException {
        Integer index = 1;
        for (Object entry : values) {
            if (entry==null) {
                statement.setObject(index, entry);
            } else {
                switch (entry.getClass().getName()) {
                    case "java.lang.String":
                        statement.setString(index, (String) entry);
                        break;
                    case "java.lang.Boolean":
                        statement.setBoolean(index, (Boolean) entry);
                        break;
                    case "java.lang.Integer":
                        statement.setInt(index, (Integer) entry);
                        break;
                    case "java.lang.Long":
                        statement.setLong(index, (Long) entry);
                        break;
                    case "java.lang.Double":
                        statement.setDouble(index, (Double) entry);
                        break;
                    case "java.sql.Date":
                        statement.setDate(index, (Date) entry);
                        break;
                    default:
                        System.err.println("DatabaseAccess, unsupported type " + entry.getClass().getName());
                        assert (false);
                }
            }
            index++;
        }
    }

    public void update(String tableName, Long id, Map<String, Object> fieldValues) {
        String request =
                "UPDATE " + tableName + " SET " +
                        Joiner.join("=?,", fieldValues.keySet(), false) +
                        "=? WHERE id=" +
                        Long.toString(id);
        PreparedStatement statement = null;
        checkConnection();
        try {
            statement = connection.prepareStatement(request);
            addParams(statement, fieldValues.values());
            int affectedRows = statement.executeUpdate();
            if (affectedRows==0) {
                throw new SQLException("UPDATE statement failed. No row modified.");
            }
        } catch (SQLException exc) {
            handleException(exc);
        } finally {
            if (statement!=null) try {
                statement.close();
            } catch (SQLException exc) {
                // Do nothing
            }
        }
    }

    public void beginTransaction() {
        checkConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exc) {
            handleException(exc);
        }


    }

    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException exc) {
            handleException(exc);
        }
    }
}
