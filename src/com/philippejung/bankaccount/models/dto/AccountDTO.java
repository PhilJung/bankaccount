package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dao.AccountDAO;
import com.philippejung.bankaccount.models.dao.RootDAO;
import com.philippejung.bankaccount.models.dao.TransactionDAO;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final SimpleLongProperty balance = new SimpleLongProperty();
    private final SimpleLongProperty initialBalance = new SimpleLongProperty();

    public AccountDTO() {
        super();
        setName(null);
        setInitialBalance(0);
        setImporterFormat(null);
        setAccountNumber(null);
        setBalance(0);
    }

    @Override
    public RootDAO newDAO() {
        return new AccountDAO();
    }

    private AccountDTO(AccountDAO dao) {
        super(dao);
        setName(dao.getName());
        setInitialBalance(dao.getInitialBalance());
        setImporterFormat(dao.getImporterFormat());
        setAccountNumber(dao.getAccountNumber());
    }

    public void toDAO(RootDAO dao) {
        super.toDAO(dao);
        AccountDAO accountDAO = (AccountDAO)dao;
        accountDAO.setName(getName());
        accountDAO.setAccountNumber(getAccountNumber());
        accountDAO.setInitialBalance(getInitialBalance());
        accountDAO.setImporterFormat(getImporterFormat());
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

    public long getBalance() {
        return balance.get();
    }

    public SimpleLongProperty balanceProperty() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance.set(balance);
    }

    public Long getInitialBalance() {
        return initialBalance.get();
    }

    public SimpleLongProperty initialBalanceProperty() {
        return initialBalance;
    }

    public void setInitialBalance(long initialBalance) {
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
    public String toString() {
        return getName();
    }

    public void loadTransactions() {
        setBalance(getInitialBalance());
        ArrayList<TransactionDAO> queryResult = MainApp.getData().getDbAccess().select(
                "SELECT * FROM [transaction] WHERE accountId=" + getId().toString() + " ORDER BY date ASC",
                TransactionDAO.class
        );
        for (TransactionDAO dao : queryResult) {
            addTransaction(new TransactionDTO(dao));
        }
    }

    /**
     * Compute previous values of balance starting at (now) and going back by steps of 1 week.
     * Values are the account balance at the end of the given day.
     *
     * @return
     */
    public XYChart.Series getBalanceHistoryByWeeks() {
        XYChart.Series series = new XYChart.Series();
        series.setName(getName());

        LocalDate now = LocalDate.now();
        LocalDate fromData = LocalDate.now().minusMonths(6);
        Long[] values = new Long[30];
        for (int i=0; i<30; i++) {
            values[i] = 0L;
        }
        int maxSlot = 0;
        for(TransactionDTO dto : allTransactions) {
            if (dto.getDate().isAfter(fromData)) {
                int slot = (int) ChronoUnit.WEEKS.between(dto.getDate(), now);
                values[slot] += dto.getAmount();
                if (slot>maxSlot) maxSlot = slot;
            }
        }
        for (int i=maxSlot; i>=0; i--) {
            String index = (i==0) ? "0" : ("-" + Integer.toString(i));
            series.getData().add(new XYChart.Data(index, values[i] / 100.0));
        }
        return series;
    }

    public void addTransaction(TransactionDTO transaction) {
        allTransactions.add(transaction);
        setBalance(getBalance() + transaction.getAmount());
    }
}