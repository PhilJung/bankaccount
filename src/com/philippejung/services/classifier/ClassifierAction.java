package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.data.models.logical.TypeOfTransaction;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierAction {
    private TypeOfTransaction setTypeAction = TypeOfTransaction.NONE;
    private String setWayOfPayment = null;
    private String setAccount = null;
    private String setCategory = null;

    public ClassifierAction(TypeOfTransaction setTypeAction, String setWayOfPayment, String setAccount, String setCategory) {
        this.setTypeAction = setTypeAction;
        this.setWayOfPayment = setWayOfPayment;
        this.setAccount = setAccount;
        this.setCategory = setCategory;
    }

    public TypeOfTransaction getSetTypeAction() {
        return setTypeAction;
    }

    public void setSetTypeAction(TypeOfTransaction setTypeAction) {
        this.setTypeAction = setTypeAction;
    }

    public String getSetWayOfPayment() {
        return setWayOfPayment;
    }

    public void setSetWayOfPayment(String setWayOfPayment) {
        this.setWayOfPayment = setWayOfPayment;
    }

    public String getSetAccount() {
        return setAccount;
    }

    public void setSetAccount(String setAccount) {
        this.setAccount = setAccount;
    }

    public String getSetCategory() {
        return setCategory;
    }

    public void setSetCategory(String setCategory) {
        this.setCategory = setCategory;
    }

    public void apply(TransactionDTO dto) {
    }
}
