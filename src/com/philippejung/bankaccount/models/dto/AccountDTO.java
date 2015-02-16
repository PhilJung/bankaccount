package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dao.AccountDAO;
import com.philippejung.bankaccount.models.dao.TransactionDAO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public class AccountDTO extends RootDTO {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty importerFormat = new SimpleStringProperty();
    private final SimpleStringProperty accountNumber = new SimpleStringProperty();
    private final ObservableList<TransactionDTO> allTransactions = FXCollections.observableArrayList();
    private final SimpleDoubleProperty balance = new SimpleDoubleProperty();
    private final SimpleDoubleProperty initialBalance = new SimpleDoubleProperty();

    public AccountDTO() {
        super();
        setName(null);
        setInitialBalance(0.0);
        setImporterFormat(null);
        setAccountNumber(null);
    }

    private AccountDTO(AccountDAO dao) {
        super(dao);
        setName(dao.getName());
        setInitialBalance(dao.getInitialBalance());
        setImporterFormat("lbp");
        setAccountNumber(dao.getAccountNumber());
    }

    public void toDAO(AccountDAO dao) {
        super.toDAO(dao);
        dao.setName(getName());
        dao.setAccountNumber(getAccountNumber());
        dao.setInitialBalance(getInitialBalance());
    }

    public String getAccountNumber() {
        return accountNumber.get();
    }

    public SimpleStringProperty accountNumberProperty() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber.set(accountNumber);
    }

    public String getImporterFormat() {
        return importerFormat.get();
    }

    public void setImporterFormat(String importerFormat) {
        this.importerFormat.set(importerFormat);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getBalance() {
        return balance.get();
    }

    public SimpleDoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public double getInitialBalance() {
        return initialBalance.get();
    }

    public SimpleDoubleProperty initialBalanceProperty() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance.set(initialBalance);
    }

    public static ObservableList<AccountDTO> getAll() {
        ArrayList<AccountDAO> queryResult = MainApp.getData().getDbAccess().select("SELECT * FROM account", AccountDAO.class);
        ArrayList<AccountDTO> retVal = new ArrayList<>();
        for(AccountDAO accountDAO : queryResult) {
            System.out.println("Trouvé compte " + accountDAO.getName());
            retVal.add(new AccountDTO(accountDAO));
        }
        return FXCollections.observableArrayList(retVal);
    }

    @Override
    public void writeToDB() {
        AccountDAO dao = new AccountDAO();
        toDAO(dao);
        dao.writeToDB();
    }

    @Override
    public String toString() {
        return getName();
    }

    public void loadTransactions() {
        setBalance(getInitialBalance());
        ArrayList<TransactionDAO> queryResult = MainApp.getData().getDbAccess().select(
                "SELECT * FROM [transaction] WHERE accountId=" + getId().toString(), TransactionDAO.class
        );
        for (TransactionDAO dao : queryResult) {
            allTransactions.add(new TransactionDTO(dao));
            setBalance(getBalance() + dao.getAmount());
        }
    }
}