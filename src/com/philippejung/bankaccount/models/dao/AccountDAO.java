package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.services.db.DatabaseAccess;
import com.philippejung.bankaccount.services.file.CSVReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public class AccountDAO extends RootDAO {
    private final static String TABLE_NAME = "Account";

    private String name;
    private String accountNumber;
    private String importerFormat;
    private Currency initialBalance;

    public AccountDAO() {
        super();
        setName("Undefined");
        setAccountNumber(null);
        setImporterFormat(null);
        setInitialBalance(Currency.zero());
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.name = rs.getString("name");
        this.accountNumber = rs.getString("accountNumber");
        this.initialBalance = new Currency(rs.getLong("initialBalance"));
        this.importerFormat = rs.getString("importerFormat");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Currency getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Currency initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getImporterFormat() {
        return importerFormat;
    }

    public void setImporterFormat(String importerFormat) {
        this.importerFormat = importerFormat;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("name", getName());
        params.put("accountNumber", getAccountNumber());
        params.put("initialBalance", getInitialBalance());
        params.put("importerFormat", getImporterFormat());
    }

    public static AccountDAO byId(long id) {
        return byId(id, MainApp.getData().getDbAccess());
    }

    public static AccountDAO byId(long id, DatabaseAccess dbAccess) {
        return dbAccess.findById(id, AccountDAO.class);
    }

    public static void truncateTable(DatabaseAccess dbAccess) {
        dbAccess.truncateTable(TABLE_NAME);
    }

    @Override
    public void readFromCSV(CSVReader reader) {
        super.readFromCSV(reader);
        setName(reader.getString(1));
        setAccountNumber(reader.getString(2));
        setInitialBalance(Currency.fromString(reader.getString(3)));
        setImporterFormat(reader.getString(4));
    }
}
