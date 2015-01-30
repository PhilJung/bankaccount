package com.philippejung.data.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by philippe on 29/01/15.
 */
public class WayOfPaymentDAO extends RootDAO {
    private String name = null;

    public WayOfPaymentDAO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.name = rs.getString("name");
    }
}
