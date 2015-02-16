package com.philippejung.bankaccount.models.dao;

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
    private String name;
    private String accountNumber;
    private Double initialBalance;

    public AccountDAO() {
        super();
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.name = rs.getString("name");
        this.accountNumber = rs.getString("accountNumber");
        this.initialBalance = rs.getDouble("initialBalance");
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

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Override
    protected String getTableName() {
        return "Account";
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("name", getName());
        params.put("accountNumber", getAccountNumber());
        params.put("initialBalance", getInitialBalance());
    }
}
