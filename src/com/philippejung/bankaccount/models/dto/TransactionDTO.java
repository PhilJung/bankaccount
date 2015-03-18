package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.models.dao.RootDAO;
import com.philippejung.bankaccount.models.dao.TransactionDAO;
import com.philippejung.bankaccount.models.interfaces.CategoryPropertyProvider;
import com.philippejung.bankaccount.models.interfaces.TypePropertyProvider;
import com.philippejung.bankaccount.models.interfaces.WayOfPaymentPropertyProvider;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 28/01/15.
 */
public class TransactionDTO extends RootDTO implements CategoryPropertyProvider, TypePropertyProvider, WayOfPaymentPropertyProvider {

    private final SimpleBooleanProperty mustBeImported = new SimpleBooleanProperty(true);
    private final SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<TypeOfTransaction> type  = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<AccountDTO> account= new SimpleObjectProperty<>();
    private final SimpleObjectProperty<AccountDTO> otherAccount= new SimpleObjectProperty<>();
    private final SimpleObjectProperty<WayOfPaymentDTO> wayOfPayment= new SimpleObjectProperty<>();
    private final SimpleObjectProperty<CategoryDTO> category = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Currency> amount = new SimpleObjectProperty<>();
    private final SimpleStringProperty detail= new SimpleStringProperty();
    private final SimpleStringProperty comment= new SimpleStringProperty();

    public TransactionDTO() {
        super();
        setMustBeImported(true);
        setDate(LocalDate.MIN);
        setType(TypeOfTransaction.NONE);
        setAccount(null);
        setOtherAccount(null);
        setWayOfPayment(null);
        setAmount(Currency.zero());
        setDetail(null);
        setComment(null);
        setCategory(null);
    }

    public TransactionDTO(TransactionDAO dao) {
        super(dao);
        setMustBeImported(false);
        setDate(dao.getDate().toLocalDate());
        setType(TypeOfTransaction.fromInt(dao.getType()));
        setAccount(MainApp.getData().getAccountById(dao.getAccountId()));
        setOtherAccount(MainApp.getData().getAccountById(dao.getOtherAccountId()));
        setWayOfPayment(MainApp.getData().getWayOfPaymentById(dao.getWayOfPaymentId()));
        setCategory(MainApp.getData().getCategoryById(dao.getCategoryId()));
        setAmount(dao.getAmount());
        setDetail(dao.getDetail());
        setComment(dao.getComment());
    }

    public void toDAO(RootDAO dao) {
        super.toDAO(dao);
        TransactionDAO transactionDAO = (TransactionDAO)dao;
        transactionDAO.setDate(java.sql.Date.valueOf(getDate()));
        transactionDAO.setType(getType().toInt());
        transactionDAO.setAccountId(idOf(getAccount()));
        transactionDAO.setOtherAccountId(idOf(getOtherAccount()));
        transactionDAO.setWayOfPaymentId(idOf(getWayOfPayment()));
        transactionDAO.setCategoryId(idOf(getCategory()));
        transactionDAO.setAmount(getAmount());
        transactionDAO.setDetail(getDetail());
        transactionDAO.setComment(getComment());
    }

    public boolean getMustBeImported() {
        return mustBeImported.get();
    }

    public SimpleBooleanProperty mustBeImportedProperty() {
        return mustBeImported;
    }

    public void setMustBeImported(boolean mustBeImported) {
        this.mustBeImported.set(mustBeImported);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public TypeOfTransaction getType() {
        return type.get();
    }

    public SimpleObjectProperty<TypeOfTransaction> typeProperty() {
        return type;
    }

    public void setType(TypeOfTransaction type) {
        this.type.set(type);
    }

    public AccountDTO getAccount() {
        return account.get();
    }

    public SimpleObjectProperty<AccountDTO> accountProperty() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account.set(account);
    }

    public AccountDTO getOtherAccount() {
        return otherAccount.get();
    }

    public SimpleObjectProperty<AccountDTO> otherAccountProperty() {
        return otherAccount;
    }

    public void setOtherAccount(AccountDTO otherAccount) {
        this.otherAccount.set(otherAccount);
    }

    public WayOfPaymentDTO getWayOfPayment() {
        return wayOfPayment.get();
    }

    public SimpleObjectProperty<WayOfPaymentDTO> wayOfPaymentProperty() {
        return wayOfPayment;
    }

    public void setWayOfPayment(WayOfPaymentDTO wayOfPayment) {
        this.wayOfPayment.set(wayOfPayment);
    }

    public Currency getAmount() {
        return amount.get();
    }

    public SimpleObjectProperty<Currency> amountProperty() {
        return amount;
    }

    public void setAmount(Currency amount) {
        this.amount.set(amount);
    }

    public String getDetail() {
        return detail.get();
    }

    public SimpleStringProperty detailProperty() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail.set(detail);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public CategoryDTO getCategory() {
        return category.get();
    }

    public SimpleObjectProperty<CategoryDTO> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category.set(category);
    }

    @Override
    public RootDAO newDAO() {
        return new TransactionDAO();
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "mustBeImported=" + mustBeImported +
                ", date=" + date +
                ", type=" + type +
                ", account=" + account +
                ", otherAccount=" + otherAccount +
                ", wayOfPayment=" + wayOfPayment +
                ", category=" + category +
                ", amount=" + amount +
                ", detail=" + detail +
                ", comment=" + comment +
                '}';
    }
}
