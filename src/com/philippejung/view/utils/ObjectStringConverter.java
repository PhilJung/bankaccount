package com.philippejung.view.utils;

import javafx.util.StringConverter;

/**
 * Created by philippe on 06/02/15.
 */
public final class ObjectStringConverter<T> extends StringConverter<T> {
    @Override
    public String toString(T object) {
        return (object == null) ? "" : object.toString();
    }

    @Override
    public T fromString(String string) {
        return null;
    }
}