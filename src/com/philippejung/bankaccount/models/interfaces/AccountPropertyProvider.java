package com.philippejung.bankaccount.models.interfaces;

import com.philippejung.bankaccount.models.dto.AccountDTO;
import javafx.beans.property.SimpleObjectProperty;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 18/03/15.
 */
public interface AccountPropertyProvider {
    public AccountDTO getAccount();
    public SimpleObjectProperty<AccountDTO> accountProperty();
    public void setAccount(AccountDTO account);
}
