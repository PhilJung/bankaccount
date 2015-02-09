package com.philippejung.bankaccount.view.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.List;

/**
 * Created by philippe on 07/02/15.
 */
public class Joiner {
    public static <T> String join(String separator, Iterable<T> items, Boolean surroundStringWithQuotes) {
        Boolean first = true;
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : items) {
            if (!first)
                stringBuilder.append(separator);
            else
                first = false;
            if ("String".equals(items.getClass().getName())) {
                stringBuilder.append('"');
                stringBuilder.append(item.toString());
                stringBuilder.append('"');
            } else {
                stringBuilder.append(item.toString());
            }
        }
        return stringBuilder.toString();
    }

    public static <T> String join(String separator, Iterable<T> items, String substituteContentWith) {
        Boolean first = true;
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : items) {
            if (!first)
                stringBuilder.append(separator);
            else
                first = false;
            stringBuilder.append(substituteContentWith);
        }
        return stringBuilder.toString();
    }
}