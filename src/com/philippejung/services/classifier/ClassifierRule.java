package com.philippejung.services.classifier;

import com.philippejung.data.models.logical.ClassifierDTO;
import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.data.models.logical.TypeOfTransaction;

/**
 * Created by philippe on 29/01/15.
 */
public class ClassifierRule extends ClassifierDTO {

    public ClassifierRule(Integer id) {
        super(id);
    }

    public void apply(TransactionDTO dto) {
        if (getNewType() != TypeOfTransaction.NONE)
            dto.setType(getNewType());
        if (getNewWayOfPayment() != null)
            dto.setWayOfPayment(getNewWayOfPayment());
        if (getNewOtherAccount() != null)
            dto.setOtherAccount(getNewOtherAccount());
        if (getNewCategory() != null)
            dto.setCategory(getNewCategory());
    }

    public boolean isVerified(TransactionDTO dto) {
        return isVerifiedDetail(dto.getDetail()) && isVerifiedAmount(dto.getAmount());
    }

    private boolean isVerifiedDetail(String detail) {
        // When no condition, always true
        if (getDetailConditionTest() == null)
            return true;
        // We do everything case insensitive
        switch(getDetailConditionTest()) {
            case "startsWith": return detail.toUpperCase().startsWith(getDetailConditionValue().toUpperCase());
            case "endsWith": return detail.toUpperCase().endsWith(getDetailConditionValue().toUpperCase());
            case "equals": return detail.toUpperCase().equals(getDetailConditionValue().toUpperCase());
            case "contains": return detail.toUpperCase().contains(getDetailConditionValue().toUpperCase());
            case "matches": return detail.toUpperCase().matches(getDetailConditionValue().toUpperCase());
        }
        // Not implemented operator => false
        return false;
    }

    private boolean isVerifiedAmount(double amount) {
        if (getAmountConditionTest()==null) return true;
        switch (getAmountConditionTest()) {
            case "==": return amount == getAmountConditionValue();
            case "!=": return amount != getAmountConditionValue();
            case ">": return amount > getAmountConditionValue();
            case ">=": return amount >= getAmountConditionValue();
            case "<": return amount < getAmountConditionValue();
            case "<=": return amount <= getAmountConditionValue();
        }
        // Not implemented operator => false
        return false;
    }

}
