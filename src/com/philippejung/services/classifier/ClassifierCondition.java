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

    public boolean isVerified(TransactionDTO dto) {
        return isVerifiedDetail(dto.getDetail()) && isVerifiedAmount(dto.getAmount());
    }

    private boolean isVerifiedDetail(String detail) {
        // When no condition, always true
        if (detailConditionTest == null)
            return true;
        // We do everything case insensitive
        switch(detailConditionTest) {
            case "startsWith": return detail.toUpperCase().startsWith(detailConditionValue.toUpperCase());
            case "endsWith": return detail.toUpperCase().endsWith(detailConditionValue.toUpperCase());
            case "equals": return detail.toUpperCase().equals(detailConditionValue.toUpperCase());
            case "contains": return detail.toUpperCase().contains(detailConditionValue.toUpperCase());
            case "matches": return detail.toUpperCase().matches(detailConditionValue.toUpperCase());
        }
        // Not implemented operator => false
        return false;
    }

    private boolean isVerifiedAmount(double amount) {
        if (amountConditionTest==null) return true;
        switch (amountConditionTest) {
            case "==": return amount == amountConditionValue;
            case "!=": return amount != amountConditionValue;
            case ">": return amount > amountConditionValue;
            case ">=": return amount >= amountConditionValue;
            case "<": return amount < amountConditionValue;
            case "<=": return amount <= amountConditionValue;
        }
        // Not implemented operator => false
        return false;
    }
}
