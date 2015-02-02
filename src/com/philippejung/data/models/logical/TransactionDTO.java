package com.philippejung.data.models.logical;

import com.philippejung.data.models.dao.TransactionDAO;
import com.philippejung.main.MainApp;
import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Created by philippe on 28/01/15.
 */
public class TransactionDTO extends RootDTO {

    private final SimpleBooleanProperty mustBeImported = new SimpleBooleanProperty(true);
    private final SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<TypeOfTransaction> type  = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<AccountDTO> otherAccount= new SimpleObjectProperty<>();
    private final SimpleObjectProperty<WayOfPaymentDTO> wayOfPayment= new SimpleObjectProperty<>();
    private final SimpleObjectProperty<CategoryDTO> category = new SimpleObjectProperty<>();
    private final SimpleDoubleProperty amount = new SimpleDoubleProperty();
    private final SimpleStringProperty detail= new SimpleStringProperty();
    private final SimpleStringProperty comment= new SimpleStringProperty();

    public TransactionDTO() {
        super();
        setMustBeImported(true);
        setDate(LocalDate.MIN);
        setType(TypeOfTransaction.NONE);
        setOtherAccount(null);
        setWayOfPayment(null);
        setAmount(0.0);
        setDetail(null);
        setComment(null);
        setCategory(null);
    }

    public TransactionDTO(TransactionDAO dao) {
        super(dao);
        setMustBeImported(false);
        setDate(dao.getDate().toLocalDate());
        setType(TypeOfTransaction.fromInt(dao.getType()));
        setOtherAccount(MainApp.getData().getAccountById(dao.getOtherAccountId()));
        setWayOfPayment(MainApp.getData().getWayOfPaymentById(dao.getWayOfPaymentId()));
        setCategory(MainApp.getData().getCategoryById(dao.getCategoryId()));
        setAmount(dao.getAmount());
        setDetail(dao.getDetail());
        setComment(dao.getComment());
    }

    public void toDAO(TransactionDAO dao) {
        super.toDAO(dao);
        dao.setDate(new java.sql.Date(getDate().toEpochDay()));
        dao.setType(getType().toInt());
        dao.setOtherAccountId(idOf(getOtherAccount()));
        dao.setWayOfPaymentId(idOf(getWayOfPayment()));
        dao.setCategoryId(idOf(getCategory()));
        dao.setAmount(getAmount());
        dao.setDetail(getDetail());
        dao.setComment(getComment());
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

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
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
}
