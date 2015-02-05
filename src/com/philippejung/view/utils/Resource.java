package com.philippejung.view.utils;

/**
 * Code from JideFX that I don't succeed in building. Will need to include the Open source JAR when stable
 */

import java.util.Locale;
import java.util.ResourceBundle;

class Resource {
    static final String BASENAME = "jidefx.scene.control.searchable.searchable"; //NON-NLS

    static final ResourceBundle RB = ResourceBundle.getBundle(BASENAME);

    public static ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(BASENAME, locale);
    }
}

