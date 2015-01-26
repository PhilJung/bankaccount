package com.philippejung.data;

import com.philippejung.data.models.AccountDAO;
import com.philippejung.data.models.AccountSummary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by philippe on 19/01/15.
 */
public class AppData {
    private DatabaseAccess dbAccess = null;
    private ObservableList<AccountSummary> allAccountSummary;
    private HashMap<Integer, AccountDAO> allAccounts;

    public AppData() throws ClassNotFoundException {
        dbAccess = new DatabaseAccess("/home/philippe/IdeaProjects/bankaccount/");
        allAccountSummary = FXCollections.observableArrayList();
        readAllAccounts();
    }

    private void readAllAccounts() {
        allAccounts = new HashMap<Integer, AccountDAO>();
        ArrayList<AccountDAO> tmp = dbAccess.select("SELECT * FROM account", AccountDAO.class);
        for(AccountDAO account : tmp) {
            System.out.println("Trouvé compte " + account.getName());
            allAccounts.put(account.getId(), account);
            AccountSummary as = new AccountSummary(account.getName(), 1234.34);
            allAccountSummary.add(as);
        }
    }

    public void close() {
        dbAccess.closeDB();
        dbAccess = null;
    }

    public ObservableList<AccountSummary> getAllAccountSummary() {
        return allAccountSummary;
    }
}
