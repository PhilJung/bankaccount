package com.philippejung.data.models.logical;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by philippe on 25/01/15.
 */

public class AccountDTO {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty importerFormat;

    public AccountDTO(String name, Double amount, String importerFormat) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleDoubleProperty(amount);
        this.importerFormat = new SimpleStringProperty(importerFormat);
    }

    public String getImporterFormat() {
        return importerFormat.get();
    }

    public void setImporterFormat(String importerFormat) {
        this.importerFormat.set(importerFormat);
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