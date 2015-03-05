package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.models.dao.AccountDAO;
import com.philippejung.bankaccount.models.dao.RootDAO;
import com.philippejung.bankaccount.models.dao.TransactionDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public class AccountDTO extends RootDTO {

    // DTO properties
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty importerFormat = new SimpleStringProperty();
    private final SimpleStringProperty accountNumber = new SimpleStringProperty();
    private final ObservableList<TransactionDTO> allTransactions = FXCollections.observableArrayList();
    private final SimpleObjectProperty<Currency> balance = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Currency> initialBalance = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Currency> futureBalance = new SimpleObjectProperty<>();

    // Balance history: how long (days) and data. Always generates 32 points of data
    private final static Integer FUTURE_BALANCE_NUMBER_OF_DAYS = 180;
    private final static Integer BALANCE_HISTORY_DEPTH = 180;
    private final XYChart.Series<String, Double> balanceHistorySerie = new XYChart.Series<>();

    // Balance variation by week over the last SLOT_NUMBER_IN_BALANCE_VARIATION weeks
    private final static Integer SLOT_NUMBER_IN_BALANCE_VARIATION = 24;
    private final XYChart.Series<String, Double> balanceVariationSerie = new XYChart.Series<>();
    private final ConcurrentSkipListMap<Long, Currency> balanceVariation = new ConcurrentSkipListMap<>();
    private final LocalDate balanceVariationNow = LocalDate.now();

    // To disable multiple computations during initial load
    private Boolean initialLoad = true;

    /**
     * Default constructor
     */
    public AccountDTO() {
        super();
        setName(null);
        setInitialBalance(Currency.zero());
        setImporterFormat(null);
        setAccountNumber(null);
        setBalance(Currency.zero());
    }

    /**
     * Build a DTO from a DAO
     * @param dao the dao containing data extracted from the database
     */
    private AccountDTO(AccountDAO dao) {
        super(dao);
        setName(dao.getName());
        setInitialBalance(dao.getInitialBalance());
        setImporterFormat(dao.getImporterFormat());
        setAccountNumber(dao.getAccountNumber());
    }

    @Override
    public RootDAO newDAO() {
        return new AccountDAO();
    }

    /**
     * Persists the current DTO into the given DAO in order to write it in database?
     * @param dao dao to write into
     */
    public void toDAO(RootDAO dao) {
        super.toDAO(dao);
        AccountDAO accountDAO = (AccountDAO)dao;
        accountDAO.setName(getName());
        accountDAO.setAccountNumber(getAccountNumber());
        accountDAO.setInitialBalance(getInitialBalance());
        accountDAO.setImporterFormat(getImporterFormat());
    }

    public SimpleObjectProperty<Currency> futureBalance() { return futureBalance; }

    public Currency getFutureBalance() { return futureBalance.get(); }

    public void setFutureBalance(Currency futureBalance) { this.futureBalance.set(futureBalance); }

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
        balanceVariationSerie.setName(name);
        balanceHistorySerie.setName(name);
    }

    public Currency getBalance() {
        return balance.get();
    }

    public SimpleObjectProperty<Currency> balanceProperty() {
        return balance;
    }

    public void setBalance(Currency balance) {
        this.balance.set(balance);
    }

    public Currency getInitialBalance() {
        return initialBalance.get();
    }

    public SimpleObjectProperty<Currency> initialBalanceProperty() {
        return initialBalance;
    }

    public void setInitialBalance(Currency initialBalance) {
        this.initialBalance.set(initialBalance);
    }

    public static ObservableList<AccountDTO> getAll() {
        ArrayList<AccountDAO> queryResult = MainApp.getData().getDbAccess().select("SELECT * FROM account", AccountDAO.class);
        ArrayList<AccountDTO> retVal = new ArrayList<>();
        for(AccountDAO accountDAO : queryResult) {
            //System.out.println("Trouvé compte " + accountDAO.getName());
            retVal.add(new AccountDTO(accountDAO));
        }
        return FXCollections.observableArrayList(retVal);
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Loads all transactions attached to this account.
     */
    public void loadTransactions() {
        setBalance(getInitialBalance());
        ArrayList<TransactionDAO> queryResult = MainApp.getData().getDbAccess().select(
                "SELECT * FROM [transaction] WHERE accountId=" + getId().toString() + " ORDER BY date ASC",
                TransactionDAO.class
        );
        for (TransactionDAO dao : queryResult) {
            MainApp.getData().addTransaction(new TransactionDTO(dao), this);
        }
        initialLoadComplete();
    }

    public void initialLoadComplete() {
        initialLoad = false;
        // Final computation of statistics, series and so on
        populateBalanceVariationSerie();
        populateBalanceHistorySerie();
    }

    /**
     * Tells how many entire weeks are between transaction date and software launch (~now)
     * @param transactionDate the date of the transaction
     * @return Long number of weeks. Negative if transaction date is in the past.
     */
    private Long getWeekSlotForDate(LocalDate transactionDate) {
        return ChronoUnit.WEEKS.between(balanceVariationNow, transactionDate);
    }

    /**
     * Rebuild Balance variation serie from the internal hashmap. This automatically updates all displayed graphs.
     */
    private void populateBalanceVariationSerie() {
        assert balanceVariationSerie != null;
        balanceVariationSerie.getData().remove(0, balanceVariationSerie.getData().size());
        balanceVariation.forEach(
                (key, value) -> balanceVariationSerie.getData().add(new XYChart.Data<>(Long.toString(key), value.toDouble()))
        );
    }

    /**
     * Get a serie made of previous variations of balance week by week.
     *
     * @return the serie made of weekly balance variations
     */
    public XYChart.Series<String, Double> getBalanceVariationByWeeks() {
        return balanceVariationSerie;
    }

    /**
     * Update values of balance starting at (now) and going back by steps of 1 week.
     * @param transaction the transaction to add to balance
     */
    private void addTransactionToBalanceVariation(TransactionDTO transaction) {
        Long slot = getWeekSlotForDate(transaction.getDate());
        // Slots are from -23 to 0 (or 1 if working at midnight)
        if (slot < -SLOT_NUMBER_IN_BALANCE_VARIATION)
            return;
        balanceVariation.putIfAbsent(slot, Currency.zero());
        balanceVariation.get(slot).add(transaction.getAmount());
    }

    /**
     * Adds a new transaction. Performs all updates of interval precomputed values.
     * @param transaction the transaction to add.
     */
    public void addTransaction(TransactionDTO transaction) {
        // Add to all transactions list
        allTransactions.add(transaction);
        // Update balance
        getBalance().add(transaction.getAmount());
        // Update balance history
        addTransactionToBalanceVariation(transaction);
        // Update graph series
        if (!initialLoad) {
            populateBalanceVariationSerie();
            populateBalanceHistorySerie();
        }
    }

    private void populateBalanceHistorySerie() {
        Long balance = getInitialBalance().toLong();
        Double xSum = 0.0;
        Double ySum = 0.0;
        Double xxSum = 0.0;
        Double xySum = 0.0;
        int nValue = 0;


        Long values[] = new Long[32];
        Arrays.fill(values, 0L);

        // Compute iteratively, store erasing previous balance
        LocalDate now = LocalDate.now();
        for (TransactionDTO transactionDTO : allTransactions) {
            balance += transactionDTO.getAmount().toLong();
            int age = (int) ChronoUnit.DAYS.between(transactionDTO.getDate(), now);
            if (age < BALANCE_HISTORY_DEPTH) {
                // For linear regression
                xSum += age;
                ySum += balance;
                xySum += age*balance;
                xxSum += age*age;
                nValue++;
                // For balance history
                values[age * 32 / BALANCE_HISTORY_DEPTH] = balance;
            }
        }

        Double divisor = ((nValue * xxSum) - (xSum * xSum));
        Double slope = 0.0;
        if (divisor != 0.0)
            slope = ((nValue * xySum) - (xSum * ySum)) / divisor;
        Double intercept = (ySum - (slope * xSum)) / nValue;
        setFutureBalance( new Currency((long) (intercept - slope * FUTURE_BALANCE_NUMBER_OF_DAYS)) );

        // Now populate the serie
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd");
        balanceHistorySerie.getData().remove(0, balanceHistorySerie.getData().size());
        for(int i=31; i>=0; i--) {
            String date = now.minusDays(BALANCE_HISTORY_DEPTH * i / 32).format(dateTimeFormatter);
            balanceHistorySerie.getData().add(new XYChart.Data<>(date, values[i] / 100.0));
        }
    }

    public XYChart.Series<String, Double> getBalanceHistory() {
        return balanceHistorySerie;
    }
}