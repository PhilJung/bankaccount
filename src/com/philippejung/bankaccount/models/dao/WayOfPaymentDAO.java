package com.philippejung.bankaccount.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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

    @Override
    protected String getTableName() {
        return "WayOfPayment";
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("name", getName());
    }
}
