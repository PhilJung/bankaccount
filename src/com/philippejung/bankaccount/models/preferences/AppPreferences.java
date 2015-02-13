package com.philippejung.bankaccount.models.preferences;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 26/01/15.
 */
public class AppPreferences {

    private final static String K_DEFAULT_IMPORT_PATH = "DefaultImportPath";
    private final static String V_DEFAULT_IMPORT_PATH = "/home/philippe/Téléchargements/";

    private final static String K_DATABASE_PATH = "DatabasePath";
    private final static String V_DATABASE_PATH = "/home/philippe/";

    private Properties propBackend;

    public String getImportDefaultPath() {
        return propBackend.getProperty(K_DEFAULT_IMPORT_PATH, V_DEFAULT_IMPORT_PATH);
    }

    public String getDatabasePath() {
        return propBackend.getProperty(K_DATABASE_PATH, V_DATABASE_PATH);
    }

    public void loadPreferences() {
        propBackend = new Properties();
        try {
            InputStream is = getClass().getResourceAsStream("/etc/user.properties");
            if (is!=null)
                propBackend.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
