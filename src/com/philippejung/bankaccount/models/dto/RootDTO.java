package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dao.RootDAO;
import com.philippejung.bankaccount.models.preferences.AppPreferences;
import com.philippejung.bankaccount.services.db.DatabaseAccess;
import javafx.beans.property.SimpleLongProperty;
import sun.applet.Main;

import java.io.File;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 29/01/15.
 */
public abstract class RootDTO {
    private final SimpleLongProperty id = new SimpleLongProperty(-1L);

    public RootDTO() {
        setId(-1L);
    }

    public RootDTO(RootDAO dao) {
        setId(dao.getId());
    }

    public void toDAO(RootDAO dao) {
        dao.setId(getId());
    }

    public Long getId() {
        return id.get();
    }

    public static Long idOf(RootDTO object) {
        if (object == null)
            return -1L;
        else
            return object.getId();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public void writeToDB() {
        writeToDB(MainApp.getData().getDbAccess());
    }

    public void writeToDB(DatabaseAccess dbAccess) {
        RootDAO dao = newDAO();
        toDAO(dao);
        Long newId = dao.writeToDB(dbAccess);
        if (newId != null) {
            setId(newId);
        }
    }

    public abstract RootDAO newDAO();
}
