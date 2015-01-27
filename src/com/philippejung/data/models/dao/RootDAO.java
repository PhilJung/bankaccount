package com.philippejung.data.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by philippe on 25/01/15.
 */
public class RootDAO {
    private Integer id;

    public RootDAO() {
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
