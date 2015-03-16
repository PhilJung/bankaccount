package com.philippejung.bankaccount.models;

import javafx.beans.property.SimpleLongProperty;

import java.math.BigDecimal;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 05/03/15.
 */
public class Currency {

    private SimpleLongProperty value = new SimpleLongProperty();

    public Currency(Currency other) {
        setValue(other);
    }

    public Currency(Long value) {
        setValue(value);
    }

    public static Currency zero() {
        return new Currency(0L);
    }

    public static Currency fromString(String realValue) {
        if (realValue==null || realValue.isEmpty())
            return Currency.zero();
        BigDecimal bigDecimalAmount = new BigDecimal(realValue.replace(',', '.'));
        return new Currency(bigDecimalAmount.multiply(new BigDecimal(100)).longValue());
    }

    public Currency plus(Currency other) {
        return new Currency(this.toLong() + other.toLong());
    }

    public Long getValue() {
        return value.get();
    }

    public SimpleLongProperty valueProperty() {
        return value;
    }

    private void setValue(long value) {
        this.value.set(value);
    }

    public void setValue(Currency other) {
        setValue(other.toLong());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        long value = getValue();
        if (value<0) sb.append("-");
        sb.append(Long.toString(Math.abs(value) / 100));
        sb.append('.');
        long cent = Math.abs(value) % 100;
        if (cent < 10) sb.append(0);
        sb.append(Long.toString(cent));
        return sb.toString();
    }

    public long toLong() {
        return getValue();
    }

    public double toDouble() {
        return (double)getValue() / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //noinspection SimplifiableIfStatement
        if (o == null || getClass() != o.getClass()) return false;
        return getValue().equals(((Currency) o).getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
