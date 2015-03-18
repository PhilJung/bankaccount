package com.philippejung.bankaccount.models;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dao.*;
import com.philippejung.bankaccount.models.dto.*;
import com.philippejung.bankaccount.models.preferences.AppPreferences;
import com.philippejung.bankaccount.services.db.DatabaseAccess;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.HashMap;

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
    private HashMap<LocalDate, HashMap<Long, BudgetDTO>> allBudgets;
    private AppPreferences preferences;

    public void init() {
        preferences = new AppPreferences();
        preferences.loadPreferences();
        dbAccess = new DatabaseAccess(preferences.getDatabasePath());
        MainApp.getMainController().setProgress(0.05);
        readAllWaysOfPayment();
        MainApp.getMainController().setProgress(0.1);
        readAllCategories();
        MainApp.getMainController().setProgress(0.2);
        readAllBudgets();
        MainApp.getMainController().setProgress(0.3);
        readAllAccounts();
        MainApp.getMainController().setProgress(0.9);
        readAllClassifiers();
        MainApp.getMainController().setProgress(1);
    }

    public AppPreferences getPreferences() {
        return preferences;
    }

    public DatabaseAccess getDbAccess() {
        return dbAccess;
    }

    private void readAllAccounts() {
        allAccounts = AccountDTO.getAll();
        int nbAccounts = allAccounts.size();
        int cur = 0;
        for(AccountDTO accountDTO : allAccounts) {
            accountDTO.loadTransactions();
            cur++;
            MainApp.getMainController().setProgress(0.3 + 0.6 * cur / nbAccounts);
        }
    }

    private void readAllBudgets() {
        allBudgets = new HashMap<>();
        for(BudgetDTO budgetDTO : BudgetDTO.getAll()) {
            addBudget(budgetDTO);
        }
    }

    private void addBudget(BudgetDTO budgetDTO) {
        LocalDate month = budgetDTO.getMonth();
        if (!allBudgets.containsKey(month))
            allBudgets.put(month, new HashMap<>());
        allBudgets.get(month).put(budgetDTO.getCategory().getId(), budgetDTO);
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

    public HashMap<LocalDate, HashMap<Long, BudgetDTO>> getAllBudgets() {
        return allBudgets;
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
        assert accountId != null;
        if (accountId == -1) return null;
        for (AccountDTO dto : getAllAccounts()) {
            if (dto.getId().equals(accountId))
                return dto;
        }
        return null;
    }

    public WayOfPaymentDTO getWayOfPaymentById(Long wayOfPaymentId) {
        assert wayOfPaymentId != null;
        if (wayOfPaymentId == -1) return null;
        for (WayOfPaymentDTO dto : getAllWaysOfPayment()) {
            if (dto.getId().equals(wayOfPaymentId))
                return dto;
        }
        return null;
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        assert categoryId != null;
        if (categoryId == -1) return null;
        for (CategoryDTO dto : getAllCategories()) {
            if (dto.getId().equals(categoryId))
                return dto;
        }
        return null;
    }

    public BudgetDTO getBudgetByDateAndCategory(LocalDate date, CategoryDTO category) {
        if (category==null)
            return null;
        LocalDate month = BudgetDTO.monthForDate(date);
        BudgetDTO dto = null;
        if (allBudgets.containsKey(month)) {
            dto = allBudgets.get(month).get(category.getId());
        }
        if (dto==null) {
            // Not found, create one
            dto = new BudgetDTO();
            dto.setMonth(month);
            dto.setCategory(category);
            dto.setBudget(Currency.zero());
            dto.writeToDB();
            addBudget(dto);
        }
        return dto;
    }

    public void addTransaction(TransactionDTO transaction, AccountDTO account) {
        account.addTransaction(transaction);
        BudgetDTO budgetDTO = getBudgetByDateAndCategory(transaction.getDate(), transaction.getCategory());
        if (budgetDTO!=null)
            budgetDTO.addTransaction(transaction);
    }

    public void restore(String backupPath) {
        DatabaseAccess dbAccess = getDbAccess();
        dbAccess.beginTransaction();
        ClassifierDAO.truncateTable(dbAccess);
        TransactionDAO.truncateTable(dbAccess);
        AccountDAO.truncateTable(dbAccess);
        BudgetDAO.truncateTable(dbAccess);
        CategoryDAO.truncateTable(dbAccess);
        WayOfPaymentDAO.truncateTable(dbAccess);
        dbAccess.commitTransaction();

        dbAccess.beginTransaction();
        WayOfPaymentDAO.restore(backupPath + "WayOfPayment.csv", dbAccess, WayOfPaymentDAO.class);
        CategoryDAO.restore(backupPath + "Category.csv", dbAccess, CategoryDAO.class);
        BudgetDAO.restore(backupPath + "Budget.csv", dbAccess, BudgetDAO.class);
        AccountDAO.restore(backupPath + "Account.csv", dbAccess, AccountDAO.class);
        ClassifierDAO.restore(backupPath + "Classifier.csv", dbAccess, ClassifierDAO.class);
        TransactionDAO.restore(backupPath + "Transaction.csv", dbAccess, TransactionDAO.class);
        dbAccess.commitTransaction();
    }

    public void backup(String backupPath) {
        WayOfPaymentDAO.backup(backupPath + "WayOfPayment.csv", WayOfPaymentDAO.getAll());
        CategoryDAO.backup(backupPath + "Category.csv", CategoryDAO.getAll());
        BudgetDAO.backup(backupPath + "Budget.csv", BudgetDAO.getAll());
        AccountDAO.backup(backupPath + "Account.csv", AccountDAO.getAll());
        ClassifierDAO.backup(backupPath + "Classifier.csv", ClassifierDAO.getAll());
        TransactionDAO.backup(backupPath + "Transaction.csv", TransactionDAO.getAll());
    }
}
