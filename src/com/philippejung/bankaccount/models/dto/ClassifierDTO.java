package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dao.ClassifierDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 30/01/15.
 */
public class ClassifierDTO extends RootDTO {
    private final SimpleStringProperty detailConditionTest = new SimpleStringProperty ();
    private final SimpleStringProperty detailConditionValue = new SimpleStringProperty ();
    private final SimpleStringProperty amountConditionTest = new SimpleStringProperty ();
    private final SimpleDoubleProperty amountConditionValue = new SimpleDoubleProperty ();
    private final SimpleObjectProperty<TypeOfTransaction> type = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<WayOfPaymentDTO> wayOfPayment = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<AccountDTO> account = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<CategoryDTO> category = new SimpleObjectProperty<>();
    private final SimpleBooleanProperty stopFurtherClassification = new SimpleBooleanProperty ();

    public ClassifierDTO() {
        super();
        setDetailConditionTest(null);
        setDetailConditionValue(null);
        setAmountConditionTest(null);
        setAmountConditionValue(0.0);
        setType(TypeOfTransaction.NONE);
        setWayOfPayment(null);
        setAccount(null);
        setCategory(null);
        setStopFurtherClassification(false);
    }

    private ClassifierDTO(ClassifierDAO dao) {
        super(dao);
        setDetailConditionTest(dao.getDetailConditionTest());
        setDetailConditionValue(dao.getDetailConditionValue());
        setAmountConditionTest(dao.getAmountConditionTest());
        setAmountConditionValue(dao.getAmountConditionValue());
        setType(TypeOfTransaction.fromInt(dao.getNewTypeId()));
        setWayOfPayment(MainApp.getData().getWayOfPaymentById(dao.getNewWayOfPaymentId()));
        setAccount(MainApp.getData().getAccountById(dao.getNewOtherAccountId()));
        setCategory(MainApp.getData().getCategoryById(dao.getNewCategoryId()));
        setStopFurtherClassification(dao.getStopFurtherClassification());
    }

    public void toDAO(ClassifierDAO dao) {
        super.toDAO(dao);
        dao.setDetailConditionTest(getDetailConditionTest());
        dao.setDetailConditionValue(getDetailConditionValue());
        dao.setAmountConditionTest(getAmountConditionTest());
        dao.setAmountConditionValue(getAmountConditionValue());
        dao.setNewTypeId(getType().toInt());
        dao.setNewWayOfPaymentId(idOf(getWayOfPayment()));
        dao.setNewOtherAccountId(idOf(getAccount()));
        dao.setNewCategoryId(idOf(getCategory()));
        dao.setStopFurtherClassification(getStopFurtherClassification());
    }

    public String getDetailConditionTest() {
        return detailConditionTest.get();
    }

    public SimpleStringProperty detailConditionTestProperty() {
        return detailConditionTest;
    }

    public void setDetailConditionTest(String detailConditionTest) {
        this.detailConditionTest.set(detailConditionTest);
    }

    public String getDetailConditionValue() {
        return detailConditionValue.get();
    }

    public SimpleStringProperty detailConditionValueProperty() {
        return detailConditionValue;
    }

    public void setDetailConditionValue(String detailConditionValue) {
        this.detailConditionValue.set(detailConditionValue);
    }

    public String getAmountConditionTest() {
        return amountConditionTest.get();
    }

    public SimpleStringProperty amountConditionTestProperty() {
        return amountConditionTest;
    }

    public void setAmountConditionTest(String amountConditionTest) {
        this.amountConditionTest.set(amountConditionTest);
    }

    public double getAmountConditionValue() {
        return amountConditionValue.get();
    }

    public SimpleDoubleProperty amountConditionValueProperty() {
        return amountConditionValue;
    }

    public void setAmountConditionValue(double amountConditionValue) {
        this.amountConditionValue.set(amountConditionValue);
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

    public WayOfPaymentDTO getWayOfPayment() {
        return wayOfPayment.get();
    }

    public SimpleObjectProperty<WayOfPaymentDTO> wayOfPaymentProperty() {
        return wayOfPayment;
    }

    public void setWayOfPayment(WayOfPaymentDTO wayOfPayment) {
        this.wayOfPayment.set(wayOfPayment);
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

    public CategoryDTO getCategory() {
        return category.get();
    }

    public SimpleObjectProperty<CategoryDTO> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category.set(category);
    }

    public boolean getStopFurtherClassification() {
        return stopFurtherClassification.get();
    }

    public SimpleBooleanProperty stopFurtherClassificationProperty() {
        return stopFurtherClassification;
    }

    public void setStopFurtherClassification(boolean stopFurtherClassification) {
        this.stopFurtherClassification.set(stopFurtherClassification);
    }

    public static ObservableList<ClassifierDTO> getAll() {
        ArrayList<ClassifierDAO> queryResult = MainApp.getData().getDbAccess().select("SELECT * FROM classifier", ClassifierDAO.class);
        ArrayList<ClassifierDTO> retVal = new ArrayList<>();
        for(ClassifierDAO classifierDAO : queryResult) {
            retVal.add(new ClassifierDTO(classifierDAO));
        }
        return FXCollections.observableArrayList(retVal);
    }

    @Override
    public void writeToDB() {
        ClassifierDAO dao = new ClassifierDAO();
        toDAO(dao);
        dao.writeToDB();
    }

    @Override
    public String toString() {
        return "ClassifierDTO{" +
                "detailConditionTest=" + detailConditionTest +
                ", detailConditionValue=" + detailConditionValue +
                ", amountConditionTest=" + amountConditionTest +
                ", amountConditionValue=" + amountConditionValue +
                ", type=" + type.toString() +
                ", wayOfPayment=" + wayOfPayment.toString() +
                ", account=" + account.toString() +
                ", category=" + category.toString() +
                ", stopFurtherClassification=" + stopFurtherClassification.toString() +
                '}';
    }
}