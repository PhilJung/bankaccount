package com.philippejung.bankaccount.models;

import com.philippejung.bankaccount.services.db.DatabaseAccess;
import com.philippejung.bankaccount.models.dto.AccountDTO;
import com.philippejung.bankaccount.models.dto.CategoryDTO;
import com.philippejung.bankaccount.models.dto.ClassifierDTO;
import com.philippejung.bankaccount.models.dto.WayOfPaymentDTO;
import com.philippejung.bankaccount.models.preferences.AppPreferences;
import javafx.collections.ObservableList;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 19/01/15.
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
        readAllWaysOfPayment();
        readAllCategories();
        readAllAccounts();
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
        for(AccountDTO accountDTO : allAccounts) {
            accountDTO.loadTransactions();
        }
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
        @SuppressWarnings("SuspiciousMethodCalls")
        int index = allWaysOfPayment.indexOf(wayOfPaymentName);
        if (index == -1)
            return null;
        else
            return allWaysOfPayment.get(index);
    }

    public AccountDTO getAccountByName(String accountName) {
        @SuppressWarnings("SuspiciousMethodCalls")
        int index = allAccounts.indexOf(accountName);
        if (index == -1)
            return null;
        else
            return allAccounts.get(index);
    }

    public CategoryDTO getCategoryByName(String categoryName) {
        @SuppressWarnings("SuspiciousMethodCalls")
        int index = allCategories.indexOf(categoryName);
        if (index == -1)
            return null;
        else
            return allCategories.get(index);
    }

    public AccountDTO getAccountById(Long accountId) {
        if (accountId == -1) return null;
        for (AccountDTO dto : getAllAccounts()) {
            if (dto.getId().equals(accountId))
                return dto;
        }
        return null;
    }

    public WayOfPaymentDTO getWayOfPaymentById(Long wayOfPaymentId) {
        if (wayOfPaymentId == -1) return null;
        for (WayOfPaymentDTO dto : getAllWaysOfPayment()) {
            if (dto.getId().equals(wayOfPaymentId))
                return dto;
        }
        return null;
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        if (categoryId == -1) return null;
        for (CategoryDTO dto : getAllCategories()) {
            if (dto.getId().equals(categoryId))
                return dto;
        }
        return null;
    }
}
