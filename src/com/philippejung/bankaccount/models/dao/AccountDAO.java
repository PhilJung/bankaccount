package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import javafx.util.Pair;
import sun.applet.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by philippe on 25/01/15.
 */
public class AccountDAO extends RootDAO {
    private String name;
    private String accountNumber;

    public AccountDAO() {
        super();
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.name = rs.getString("name");
        this.accountNumber = rs.getString("accountNumber");
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

    public void writeToDB() {
        Map<String, Object> allVals = new HashMap<>();
        allVals.put("name", getName());
        allVals.put("accountNumber", getAccountNumber());
        if (getId() == -1) {
            Integer id = MainApp.getData().getDbAccess().insert("Account", allVals);
        } else {
            MainApp.getData().getDbAccess().update("Account", getId(), allVals);
        }
    }

}
