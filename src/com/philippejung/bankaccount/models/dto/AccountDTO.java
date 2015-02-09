package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dao.AccountDAO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by philippe on 25/01/15.
 */

public class AccountDTO extends RootDTO {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleDoubleProperty amount = new SimpleDoubleProperty();
    private final SimpleStringProperty importerFormat = new SimpleStringProperty();
    private final SimpleStringProperty accountNumber = new SimpleStringProperty();

    public AccountDTO() {
        super();
        setName(null);
        setAmount(0.0);
        setImporterFormat(null);
        setAccountNumber(null);
    }

    private AccountDTO(AccountDAO dao) {
        super(dao);
        setName(dao.getName());
        setAmount(0);
        setImporterFormat("lbp");
        setAccountNumber(dao.getAccountNumber());
    }

    public void toDAO(AccountDAO dao) {
        super.toDAO(dao);
        dao.setName(getName());
        dao.setAccountNumber(getAccountNumber());
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

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
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

    public void writeToDB() {
        AccountDAO dao = new AccountDAO();
        toDAO(dao);
        dao.writeToDB();
    }

    @Override
    public String toString() {
        return getName();
    }
}