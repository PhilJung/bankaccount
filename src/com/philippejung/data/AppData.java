package com.philippejung.data;

import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by philippe on 19/01/15.
 */
public class AppData {
    HashMap<String, JPanel> panelsByName = new HashMap<String,JPanel>();
    DatabaseAccess dbAccess = null;

    public AppData() throws ClassNotFoundException {
        dbAccess = new DatabaseAccess("/home/philippe/IdeaProjects/bankaccount/");
    }

    public JPanel getPanelByName(String title) {
        return panelsByName.getOrDefault(title, null);
    }

    public void setPanelByName(String name, JPanel panel) {
        panelsByName.put(name, panel);
    }

    public void close() {
        dbAccess.closeDB();
        dbAccess = null;
    }
}
