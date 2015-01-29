package com.philippejung.data;

import com.philippejung.data.models.db.DatabaseAccess;
import com.philippejung.data.models.dao.AccountDAO;
import com.philippejung.data.models.logical.AccountDTO;
import com.philippejung.data.models.logical.WayOfPaymentDTO;
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
    private ObservableList<AccountDTO> allAccounts;
    private ObservableList<WayOfPaymentDTO> allWaysOfPayment;
    private AppPreferences preferences;

    public void init() {
        preferences = new AppPreferences();
        preferences.loadPreferences();
        dbAccess = new DatabaseAccess("/home/philippe/.bankaccount/");
        readAllAccounts();
        readAllWaysOfPayment();
    }

    public AppPreferences getPreferences() {
        return preferences;
    }

    public DatabaseAccess getDbAccess() {
        return dbAccess;
    }

    private void readAllAccounts() {
        allAccounts = AccountDTO.getAll();
    }

    private void readAllWaysOfPayment() {
        allWaysOfPayment = WayOfPaymentDTO.getAll();
    }

    public void close() {
        dbAccess.closeDB();
        dbAccess = null;
        preferences.savePreferences();
        preferences = null;
    }

    public ObservableList<AccountDTO> getAllAccounts() {
        return allAccounts;
    }

    public Integer getWayOfPaymentByName(String wayOfPaymentName) {
        return null;
    }

    public Integer getAccountByName(String accountName) {
        return null;
    }

    public Integer getCategoryByName(String categoryName) {
        return null;
    }
}
