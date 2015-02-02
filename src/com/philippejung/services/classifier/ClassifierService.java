package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.ClassifierDTO;
import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.data.models.logical.TypeOfTransaction;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierService {

    private ClassifierDTO classifierDTO;
    private TransactionDTO transactionDTO;

    public ClassifierDTO getClassifierDTO() {
        return classifierDTO;
    }

    public void setClassifierDTO(ClassifierDTO classifierDTO) {
        this.classifierDTO = classifierDTO;
    }

    public TransactionDTO getTransactionDTO() {
        return transactionDTO;
    }

    public void setTransactionDTO(TransactionDTO transactionDTO) {
        this.transactionDTO = transactionDTO;
    }

    private void apply() {
        if (classifierDTO.getNewType() != TypeOfTransaction.NONE)
            transactionDTO.setType(classifierDTO.getNewType());
        if (classifierDTO.getNewWayOfPayment() != null)
            transactionDTO.setWayOfPayment(classifierDTO.getNewWayOfPayment());
        if (classifierDTO.getNewOtherAccount() != null)
            transactionDTO.setOtherAccount(classifierDTO.getNewOtherAccount());
        if (classifierDTO.getNewCategory() != null)
            transactionDTO.setCategory(classifierDTO.getNewCategory());
    }

    public boolean isVerified() {
        return isVerifiedDetail(classifierDTO.getDetailConditionTest(), classifierDTO.getDetailConditionValue(), transactionDTO.getDetail())
                && isVerifiedAmount(classifierDTO.getAmountConditionTest(), classifierDTO.getAmountConditionValue(), transactionDTO.getAmount());
    }

    private boolean isVerifiedDetail(String detailConditionTest, String detailConditionValue, String detail) {
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

    private boolean isVerifiedAmount(String amountConditionTest, double amountConditionValue, double amount) {
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

    public boolean classifyAndTellIfWeShouldStop(ClassifierDTO classifierDTO, TransactionDTO transactionDTO) {
        setTransactionDTO(transactionDTO);
        setClassifierDTO(classifierDTO);
        if (isVerified()) {
            apply();
            return classifierDTO.getStopFurtherClassification();
        }
        return false;
    }

}
