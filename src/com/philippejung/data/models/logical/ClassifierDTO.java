package com.philippejung.data.models.logical;

import com.philippejung.data.models.dao.ClassifierDAO;
import com.philippejung.data.models.dao.RootDAO;
import com.philippejung.main.MainApp;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by philippe on 30/01/15.
 */
public class ClassifierDTO extends RootDTO {
    private final SimpleStringProperty detailConditionTest = new SimpleStringProperty ();
    private final SimpleStringProperty detailConditionValue = new SimpleStringProperty ();
    private final SimpleStringProperty amountConditionTest = new SimpleStringProperty ();
    private final SimpleDoubleProperty amountConditionValue = new SimpleDoubleProperty ();
    private final SimpleObjectProperty<TypeOfTransaction> newType = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<WayOfPaymentDTO> newWayOfPayment = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<AccountDTO> newOtherAccount = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<CategoryDTO> newCategory = new SimpleObjectProperty<>();
    private final SimpleBooleanProperty stopFurtherClassification = new SimpleBooleanProperty ();

    public ClassifierDTO() {
        super();
        setDetailConditionTest(null);
        setDetailConditionValue(null);
        setAmountConditionTest(null);
        setAmountConditionValue(0.0);
        setNewType(TypeOfTransaction.NONE);
        setNewWayOfPayment(null);
        setNewOtherAccount(null);
        setNewCategory(null);
        setStopFurtherClassification(false);
    }

    private ClassifierDTO(ClassifierDAO dao) {
        super(dao);
        setDetailConditionTest(dao.getDetailConditionTest());
        setDetailConditionValue(dao.getDetailConditionValue());
        setAmountConditionTest(dao.getAmountConditionTest());
        setAmountConditionValue(dao.getAmountConditionValue());
        setNewType(TypeOfTransaction.fromInt(dao.getNewTypeId()));
        setNewWayOfPayment(MainApp.getData().getWayOfPaymentById(dao.getNewWayOfPaymentId()));
        setNewOtherAccount(MainApp.getData().getAccountById(dao.getNewOtherAccountId()));
        setNewCategory(MainApp.getData().getCategoryById(dao.getNewCategoryId()));
        setStopFurtherClassification(dao.getStopFurtherClassification());
    }

    public void toDAO(ClassifierDAO dao) {
        super.toDAO(dao);
        dao.setDetailConditionTest(getDetailConditionTest());
        dao.setDetailConditionValue(getDetailConditionValue());
        dao.setAmountConditionTest(getAmountConditionTest());
        dao.setAmountConditionValue(getAmountConditionValue());
        dao.setNewTypeId(getNewType().toInt());
        dao.setNewWayOfPaymentId(idOf(getNewWayOfPayment()));
        dao.setNewOtherAccountId(idOf(getNewOtherAccount()));
        dao.setNewCategoryId(idOf(getNewCategory()));
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

    public TypeOfTransaction getNewType() {
        return newType.get();
    }

    public SimpleObjectProperty<TypeOfTransaction> newTypeProperty() {
        return newType;
    }

    public void setNewType(TypeOfTransaction newType) {
        this.newType.set(newType);
    }

    public WayOfPaymentDTO getNewWayOfPayment() {
        return newWayOfPayment.get();
    }

    public SimpleObjectProperty<WayOfPaymentDTO> newWayOfPaymentProperty() {
        return newWayOfPayment;
    }

    public void setNewWayOfPayment(WayOfPaymentDTO newWayOfPayment) {
        this.newWayOfPayment.set(newWayOfPayment);
    }

    public AccountDTO getNewOtherAccount() {
        return newOtherAccount.get();
    }

    public SimpleObjectProperty<AccountDTO> newOtherAccountProperty() {
        return newOtherAccount;
    }

    public void setNewOtherAccount(AccountDTO newOtherAccount) {
        this.newOtherAccount.set(newOtherAccount);
    }

    public CategoryDTO getNewCategory() {
        return newCategory.get();
    }

    public SimpleObjectProperty<CategoryDTO> newCategoryProperty() {
        return newCategory;
    }

    public void setNewCategory(CategoryDTO newCategory) {
        this.newCategory.set(newCategory);
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

}
