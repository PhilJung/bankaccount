package com.philippejung.bankaccount.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyTest {

    private final static Double EPSILON = 1E-15;

    @Test
    public void testZero() throws Exception {
        Currency zero = Currency.zero();
        assertEquals(0L, zero.toLong());
    }

    @Test
    public void testFromString() throws Exception {
        Currency c;
        c = Currency.fromString("1");
        assertEquals(100L, c.toLong());
        c = Currency.fromString("1.33");
        assertEquals(133L, c.toLong());
        c = Currency.fromString("-1");
        assertEquals(-100L, c.toLong());
        c = Currency.fromString("-1.33");
        assertEquals(-133L, c.toLong());
        c = Currency.fromString("1.331");
        assertEquals(133L, c.toLong());
        c = Currency.fromString("1.334");
        assertEquals(133L, c.toLong());
        c = Currency.fromString("1.335");
        assertEquals(133L, c.toLong());
        c = Currency.fromString("1.336");
        assertEquals(133L, c.toLong());
        c = Currency.fromString("1.339");
        assertEquals(133L, c.toLong());
        c = Currency.fromString("-1.331");
        assertEquals(-133L, c.toLong());
        c = Currency.fromString("-1.334");
        assertEquals(-133L, c.toLong());
        c = Currency.fromString("-1.335");
        assertEquals(-133L, c.toLong());
        c = Currency.fromString("-1.339");
        assertEquals(-133L, c.toLong());
        c = Currency.fromString("1.03");
        assertEquals(103L, c.toLong());
        c = Currency.fromString("-0.01");
        assertEquals(-1L, c.toLong());
    }

    @Test
    public void testAdd() throws Exception {
        Currency c = Currency.zero();
        assertEquals(0, c.toLong());
        c.add(new Currency(100L));
        assertEquals(100L, c.toLong());
        c.add(new Currency(100L));
        assertEquals(200L, c.toLong());
        c.add(new Currency(-50L));
        assertEquals(150L, c.toLong());
    }

    @Test
    public void testToString() throws Exception {
        Currency c;
        c = Currency.fromString("1");
        assertEquals("1.00", c.toString());
        c = Currency.fromString("1.33");
        assertEquals("1.33", c.toString());
        c = Currency.fromString("-1");
        assertEquals("-1.00", c.toString());
        c = Currency.fromString("-1.33");
        assertEquals("-1.33", c.toString());
        c = Currency.fromString("1.03");
        assertEquals("1.03", c.toString());
        c = Currency.fromString("-0.01");
        assertEquals("-0.01", c.toString());
   }

    @Test
    public void testToDouble() throws Exception {
        Currency c;
        c = Currency.fromString("-1.33");
        assertEquals(-1.33, c.toDouble(), EPSILON);
        c = Currency.fromString("1.33");
        assertEquals(1.33, c.toDouble(), EPSILON);
    }
}