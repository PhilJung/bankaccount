package com.philippejung.bankaccount.models.interfaces;

import com.philippejung.bankaccount.models.dto.TypeOfTransaction;
import javafx.beans.property.SimpleObjectProperty;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 18/03/15.
 */
public interface TypePropertyProvider {
    public TypeOfTransaction getType();
    public SimpleObjectProperty<TypeOfTransaction> typeProperty();
    public void setType(TypeOfTransaction type);
}
