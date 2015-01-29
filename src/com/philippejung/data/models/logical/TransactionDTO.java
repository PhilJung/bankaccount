package com.philippejung.data.models.logical;

import com.philippejung.main.MainApp;
import javafx.beans.property.*;
import sun.applet.Main;

import java.time.LocalDate;

/**
 * Created by philippe on 28/01/15.
 */
public class TransactionDTO extends RootDTO {

    private final SimpleBooleanProperty mustBeImported = new SimpleBooleanProperty(true);
    private final SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();
    private final SimpleObjectProperty<TypeOfTransaction> type  = new SimpleObjectProperty<TypeOfTransaction>();
    private final SimpleIntegerProperty otherAccountId= new SimpleIntegerProperty();
    private final SimpleIntegerProperty otherTransactionId= new SimpleIntegerProperty();;
    private final SimpleIntegerProperty wayOfPaymentId= new SimpleIntegerProperty();;
    private final SimpleDoubleProperty amount = new SimpleDoubleProperty();
    private final SimpleStringProperty detail= new SimpleStringProperty();
    private final SimpleStringProperty comment= new SimpleStringProperty();

    public TransactionDTO() {
        super(-1);
        setMustBeImported(true);
        setDate(LocalDate.MIN);
        setType(TypeOfTransaction.NONE);
        setOtherAccountId(-1);
        setOtherTransactionId(-1);
        setWayOfPaymentId(-1);
        setAmount(0.0);
        setDetail(null);
        setComment(null);
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

    public int getOtherAccountId() {
        return otherAccountId.get();
    }

    public SimpleIntegerProperty otherAccountIdProperty() {
        return otherAccountId;
    }

    public void setOtherAccountId(int otherAccountId) {
        this.otherAccountId.set(otherAccountId);
    }

    public int getOtherTransactionId() {
        return otherTransactionId.get();
    }

    public SimpleIntegerProperty otherTransactionIdProperty() {
        return otherTransactionId;
    }

    public void setOtherTransactionId(int otherTransactionId) {
        this.otherTransactionId.set(otherTransactionId);
    }

    public int getWayOfPaymentId() {
        return wayOfPaymentId.get();
    }

    public SimpleIntegerProperty wayOfPaymentIdProperty() {
        return wayOfPaymentId;
    }

    public void setWayOfPaymentId(int wayOfPaymentId) {
        this.wayOfPaymentId.set(wayOfPaymentId);
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

    public void setWayOfPayment(String wayOfPaymentName) {
        Integer wopId = MainApp.getData().getWayOfPaymentByName(wayOfPaymentName);
        if (wopId != -1)
            setWayOfPaymentId(wopId);
    }

    public void setOtherAccount(String otherAccountName) {
        Integer oaId = MainApp.getData().getAccountByName(otherAccountName);
        if (oaId != -1)
            setOtherAccountId(oaId);
    }

    public void setCategory(String categoryName) {
        Integer catId = MainApp.getData().getCategoryByName(categoryName);
        if (catId != -1)
            setOtherAccountId(catId);
    }
}
