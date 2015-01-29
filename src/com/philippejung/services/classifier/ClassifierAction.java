package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.data.models.logical.TypeOfTransaction;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierAction {
    private TypeOfTransaction newTypeValue = TypeOfTransaction.NONE;
    private String newWayOfPaymentValue = null;
    private String newOtherAccountValue = null;
    private String newCategoryValue = null;
    private boolean stopFurtherClassification = false;

    public ClassifierAction(TypeOfTransaction newTypeValue, String newWayOfPaymentValue, String newOtherAccountValue,
                            String setCategory, boolean stopFurtherClassification) {
        this.newTypeValue = newTypeValue;
        this.newWayOfPaymentValue = newWayOfPaymentValue;
        this.newOtherAccountValue = newOtherAccountValue;
        this.newCategoryValue = setCategory;
        this.stopFurtherClassification = stopFurtherClassification;
    }

    public boolean isStopFurtherClassification() {
        return stopFurtherClassification;
    }

    public void setStopFurtherClassification(boolean stopFurtherClassification) {
        this.stopFurtherClassification = stopFurtherClassification;
    }

    public TypeOfTransaction getNewTypeValue() {
        return newTypeValue;
    }

    public void setNewTypeValue(TypeOfTransaction newTypeValue) {
        this.newTypeValue = newTypeValue;
    }

    public String getNewWayOfPaymentValue() {
        return newWayOfPaymentValue;
    }

    public void setNewWayOfPaymentValue(String newWayOfPaymentValue) {
        this.newWayOfPaymentValue = newWayOfPaymentValue;
    }

    public String getNewOtherAccountValue() {
        return newOtherAccountValue;
    }

    public void setNewOtherAccountValue(String newOtherAccountValue) {
        this.newOtherAccountValue = newOtherAccountValue;
    }

    public String getNewCategoryValue() {
        return newCategoryValue;
    }

    public void setNewCategoryValue(String newCategoryValue) {
        this.newCategoryValue = newCategoryValue;
    }

    public void apply(TransactionDTO dto) {
        if (newTypeValue !=TypeOfTransaction.NONE)
            dto.setType(newTypeValue);
        if (newWayOfPaymentValue != null)
            dto.setWayOfPayment(newWayOfPaymentValue);
        if (newOtherAccountValue != null)
            dto.setOtherAccount(newOtherAccountValue);
        if (newCategoryValue != null)
            dto.setCategory(newCategoryValue);
    }
}
