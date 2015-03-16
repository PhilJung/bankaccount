package com.philippejung.bankaccount.services.file;

import junit.framework.TestCase;

public class CSVReaderTest extends TestCase {

    public void testSeparator() {
        String testString = "a,b,c";
        CSVReader reader = new CSVReader(null, ",", "\"", testString);
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals("a", reader.getString(0));
        assertEquals("b", reader.getString(1));
        assertEquals("c", reader.getString(2));
        assertFalse(reader.hasNext());
    }

    public void testDelimitorSimple() {
        String testString = "a,\"b\",c";
        CSVReader reader = new CSVReader(null, ",", "\"", testString);
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals(reader.getString(0), "a");
        assertEquals(reader.getString(1), "b");
        assertEquals(reader.getString(2), "c");
    }

    public void testDelimitorDouble() {
        String testString = "a,b\"\",c";
        CSVReader reader = new CSVReader(null, ",", "\"", testString);
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals(reader.getString(0), "a");
        assertEquals(reader.getString(1), "b\"");
        assertEquals(reader.getString(2), "c");
    }

    public void testDelimitorContainingSeparator() {
        String testString = "a,\"b,c\"";
        CSVReader reader = new CSVReader(null, ",", "\"", testString);
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals(reader.getString(0), "a");
        assertEquals(reader.getString(1), "b,c");
    }

    public void testMultiline() {
        String testString = "a,\"b,\nc\"";
        CSVReader reader = new CSVReader(null, ",", "\"", testString);
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals(reader.getString(0), "a");
        assertEquals(reader.getString(1), "b,\nc");
    }

    public void testMultilineCRLF() {
        String testString = "a,\"b,\r\nc\"\nd\r\ne,f\n";
        CSVReader reader = new CSVReader(null, ",", "\"", testString);
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals(reader.getString(0), "a");
        assertEquals(reader.getString(1), "b,\r\nc");
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals(reader.getString(0), "d");
        assertTrue(reader.hasNext());
        reader.next();
        assertEquals(reader.getString(0), "e");
        assertEquals(reader.getString(1), "f");
    }

}