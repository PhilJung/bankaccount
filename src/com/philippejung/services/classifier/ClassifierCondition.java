package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.TransactionDTO;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierCondition {

    private String detailCondition = null;
    private String amountCondition = null;

    public ClassifierCondition(String detailCondition, String amountCondition) {
        this.detailCondition = detailCondition;
        this.amountCondition = amountCondition;
    }

    public String getDetailCondition() {
        return detailCondition;
    }

    public void setDetailCondition(String detailCondition) {
        this.detailCondition = detailCondition;
    }

    public String getAmountCondition() {
        return amountCondition;
    }

    public void setAmountCondition(String amountCondition) {
        this.amountCondition = amountCondition;
    }

    public boolean isVerified(TransactionDTO dto) {
        return false;
    }
}
