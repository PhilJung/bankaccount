package com.philippejung.bankaccount.view.utils;

import com.philippejung.bankaccount.models.Currency;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 06/03/15.
 */
public class CurrencyTextField extends TextField {
    private SimpleObjectProperty<Currency> amount = new SimpleObjectProperty<>();

    public Currency getAmount() {
        return amount.get();
    }

    public void setAmount(Currency amount) {
        this.amount.set(amount);
        amount.valueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    displayAmount();
                }
        );
    }

    public SimpleObjectProperty<Currency> amountProperty() {
        return amount;
    }

    public CurrencyTextField() {
        super();
        setOnAction(actionEvent -> {
            parseAndFormatInput();
        });
        focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue.booleanValue()) {
                        System.out.println("Focus lost");
                        parseAndFormatInput();
                    }
                }
        );
        amount.addListener(
                (ObservableValue<? extends Currency> observable, Currency oldValue, Currency newValue) -> {
                    displayAmount();
                }
        );
        displayAmount();
    }

    private void displayAmount() {
        if (getAmount()!=null)
            setText(getAmount().toString());
        else
            setText("");
    }

    private void parseAndFormatInput() {
        String input = getText();
        if (input == null || input.length() == 0) {
            getAmount().setValue(Currency.zero());
        } else {
            getAmount().setValue(Currency.fromString(input));
        }
        displayAmount();
        selectAll();
    }

}
