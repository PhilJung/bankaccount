package com.philippejung.data.models.logical;

import com.philippejung.data.models.dao.RootDAO;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by philippe on 29/01/15.
 */
public class RootDTO {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(-1);

    public RootDTO(Integer id) {
        setId(id);
    }

    public RootDTO(RootDAO dao) {
        setId(dao.getId());
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
