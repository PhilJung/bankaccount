package com.philippejung.bankaccount.models.dto;

import com.philippejung.bankaccount.models.dao.RootDAO;
import javafx.beans.property.SimpleLongProperty;

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

    public abstract void writeToDB();
}
