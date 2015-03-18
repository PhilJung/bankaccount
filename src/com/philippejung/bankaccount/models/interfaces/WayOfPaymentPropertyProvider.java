package com.philippejung.bankaccount.models.interfaces;

import com.philippejung.bankaccount.models.dto.WayOfPaymentDTO;
import javafx.beans.property.SimpleObjectProperty;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 18/03/15.
 */
public interface WayOfPaymentPropertyProvider {
    public WayOfPaymentDTO getWayOfPayment();
    public SimpleObjectProperty<WayOfPaymentDTO> wayOfPaymentProperty();
    public void setWayOfPayment(WayOfPaymentDTO wayOfPayment);
}
