package com.philippejung.data;

import com.philippejung.data.models.db.DatabaseAccess;
import com.philippejung.data.models.logical.AccountDTO;
import com.philippejung.data.models.logical.CategoryDTO;
import com.philippejung.data.models.logical.ClassifierDTO;
import com.philippejung.data.models.logical.WayOfPaymentDTO;
import com.philippejung.data.models.preferences.AppPreferences;
import com.philippejung.services.classifier.ClassifierService;
import javafx.collections.ObservableList;

/**
 * Created by philippe on 19/01/15.
 */
public class AppData {
    private DatabaseAccess dbAccess = null;
    private ObservableList<AccountDTO> allAccounts;
    private ObservableList<WayOfPaymentDTO> allWaysOfPayment;
    private ObservableList<ClassifierDTO> allClassifiers;
    private ObservableList<CategoryDTO> allCategories;
    private AppPreferences preferences;

    public void init() {
        preferences = new AppPreferences();
        preferences.loadPreferences();
        dbAccess = new DatabaseAccess("/home/philippe/.bankaccount/");
        readAllAccounts();
        readAllWaysOfPayment();
        readAllCategories();
        readAllClassifiers();
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

    private void readAllCategories() {
        allCategories = CategoryDTO.getAll();
    }

    private void readAllClassifiers() {
        allClassifiers = ClassifierDTO.getAll();
    }

    public void close() {
        dbAccess.closeDB();
        dbAccess = null;
        preferences = null;
    }

    public ObservableList<AccountDTO> getAllAccounts() {
        return allAccounts;
    }

    public ObservableList<CategoryDTO> getAllCategories() {
        return allCategories;
    }

    public ObservableList<WayOfPaymentDTO> getAllWaysOfPayment() {
        return allWaysOfPayment;
    }

    public ObservableList<ClassifierDTO> getAllClassifiers() { return allClassifiers; }

    public WayOfPaymentDTO getWayOfPaymentByName(String wayOfPaymentName) {
        int index = allWaysOfPayment.indexOf(wayOfPaymentName);
        if (index == -1)
            return null;
        else
            return allWaysOfPayment.get(index);
    }

    public AccountDTO getAccountByName(String accountName) {
        int index = allAccounts.indexOf(accountName);
        if (index == -1)
            return null;
        else
            return allAccounts.get(index);
    }

    public CategoryDTO getCategoryByName(String categoryName) {
        int index = allCategories.indexOf(categoryName);
        if (index == -1)
            return null;
        else
            return allCategories.get(index);
    }

    public AccountDTO getAccountById(Integer accountId) {
        if (accountId == -1) return null;
        for (AccountDTO dto : getAllAccounts()) {
            if (dto.getId() == accountId)
                return dto;
        }
        return null;
    }

    public WayOfPaymentDTO getWayOfPaymentById(Integer wayOfPaymentId) {
        if (wayOfPaymentId == -1) return null;
        for (WayOfPaymentDTO dto : getAllWaysOfPayment()) {
            if (dto.getId() == wayOfPaymentId)
                return dto;
        }
        return null;
    }

    public CategoryDTO getCategoryById(Integer categoryId) {
        if (categoryId == -1) return null;
        for (CategoryDTO dto : getAllCategories()) {
            if (dto.getId() == categoryId)
                return dto;
        }
        return null;
    }
}
