package com.philippejung.data;

import com.philippejung.data.models.db.DatabaseAccess;
import com.philippejung.data.models.dao.AccountDAO;
import com.philippejung.data.models.logical.AccountDTO;
import com.philippejung.data.models.preferences.AppPreferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by philippe on 19/01/15.
 */
public class AppData {
    private DatabaseAccess dbAccess = null;
    private ObservableList<AccountDTO> allAccountSummary;
    private HashMap<Integer, AccountDAO> allAccounts;
    private AppPreferences preferences;

    public AppData() throws ClassNotFoundException {
        preferences = new AppPreferences();
        preferences.loadPreferences();
        dbAccess = new DatabaseAccess("/home/philippe/IdeaProjects/bankaccount/");
        allAccountSummary = FXCollections.observableArrayList();
        readAllAccounts();
    }

    public AppPreferences getPreferences() {
        return preferences;
    }

    private void readAllAccounts() {
        allAccounts = new HashMap<Integer, AccountDAO>();
        ArrayList<AccountDAO> tmp = dbAccess.select("SELECT * FROM account", AccountDAO.class);
        for(AccountDAO account : tmp) {
            System.out.println("Trouvé compte " + account.getName());
            allAccounts.put(account.getId(), account);
            AccountDTO as = new AccountDTO(account.getName(), 1234.34, "LBP");
            allAccountSummary.add(as);
        }
    }

    public void close() {
        dbAccess.closeDB();
        dbAccess = null;
        preferences.savePreferences();
        preferences = null;
    }

    public ObservableList<AccountDTO> getAllAccountSummary() {
        return allAccountSummary;
    }
}
