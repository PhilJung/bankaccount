package com.philippejung.bankaccount.models;

import java.math.BigDecimal;

/**
 * Created by philippe on 05/03/15.
 */
public class Currency {

    private Long value;

    public Currency(Currency other) {
        this.value = other.toLong();
    }

    public Currency(Long value) {
        this.value = value;
    }

    public static Currency zero() {
        return new Currency(0L);
    }

    public static Currency fromString(String realValue) {
        BigDecimal bigDecimalAmount = new BigDecimal(realValue.replace(',', '.'));
        return new Currency(bigDecimalAmount.multiply(new BigDecimal(100)).longValue());
    }

    public void add(Currency other) {
        this.value += other.value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (value<0) sb.append("-");
        sb.append(Long.toString(Math.abs(value) / 100));
        sb.append('.');
        long cent = Math.abs(value) % 100;
        if (cent < 10) sb.append(0);
        sb.append(Long.toString(cent));
        return sb.toString();
    }

    public long toLong() {
        return value;
    }

    public double toDouble() {
        return (double)value / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (!value.equals(currency.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
