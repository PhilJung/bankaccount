package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.TransactionDTO;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierCondition {

    private String detailConditionTest = null;
    private String detailConditionValue = null;
    private String amountConditionTest = null;
    private Double amountConditionValue = null;

    public ClassifierCondition(String detailConditionTest, String detailConditionValue, String amountConditionTest, Double amountConditionValue) {
        this.detailConditionTest = detailConditionTest;
        this.amountConditionTest = amountConditionTest;
        this.detailConditionValue = detailConditionValue;
        this.amountConditionValue = amountConditionValue;
    }

    public String getDetailConditionTest() {
        return detailConditionTest;
    }

    public void setDetailConditionTest(String detailConditionTest) {
        this.detailConditionTest = detailConditionTest;
    }

    public String getDetailConditionValue() {
        return detailConditionValue;
    }

    public void setDetailConditionValue(String detailConditionValue) {
        this.detailConditionValue = detailConditionValue;
    }

    public String getAmountConditionTest() {
        return amountConditionTest;
    }

    public void setAmountConditionTest(String amountConditionTest) {
        this.amountConditionTest = amountConditionTest;
    }

    public Double getAmountConditionValue() {
        return amountConditionValue;
    }

    public void setAmountConditionValue(Double amountConditionValue) {
        this.amountConditionValue = amountConditionValue;
    }

}
