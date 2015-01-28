package com.philippejung.data.models.logical;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Created by philippe on 28/01/15.
 */
public class TransactionDTO {

    public final static Integer INCOME = 1;
    public final static Integer EXPENSE = 2;
    public final static Integer MOVE_TO = 3;
    public final static Integer MOVE_FROM = 4;

    private final SimpleBooleanProperty mustBeImported;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleIntegerProperty type;
    private final SimpleIntegerProperty otherAccountId;
    private final SimpleIntegerProperty otherTransactionId;
    private final SimpleIntegerProperty wayOfPaymentId;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty detail;
    private final SimpleStringProperty comment;

    public TransactionDTO() {
        mustBeImported = new SimpleBooleanProperty(true);
        date = new SimpleObjectProperty<LocalDate>();
        type = new SimpleIntegerProperty();
        otherAccountId = new SimpleIntegerProperty();
        otherTransactionId = new SimpleIntegerProperty();
        wayOfPaymentId = new SimpleIntegerProperty();
        amount = new SimpleDoubleProperty();
        detail = new SimpleStringProperty();
        comment = new SimpleStringProperty();
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

    public int getType() {
        return type.get();
    }

    public SimpleIntegerProperty typeProperty() {
        return type;
    }

    public void setType(int type) {
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
}
