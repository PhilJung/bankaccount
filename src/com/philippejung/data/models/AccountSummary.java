package com.philippejung.data.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by philippe on 25/01/15.
 */

public class AccountSummary {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty amount;

    public AccountSummary(String name, Double amount) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
}