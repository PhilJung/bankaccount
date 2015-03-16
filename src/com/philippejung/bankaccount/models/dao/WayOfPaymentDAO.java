package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.services.db.DatabaseAccess;
import com.philippejung.bankaccount.services.file.CSVReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 29/01/15.
 */
public class WayOfPaymentDAO extends RootDAO {
    private final static String TABLE_NAME = "WayOfPayment";

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
        setName(rs.getString("name"));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("name", getName());
    }

    public static WayOfPaymentDAO byId(long id) {
        return byId(id, MainApp.getData().getDbAccess());
    }

    public static WayOfPaymentDAO byId(long id, DatabaseAccess dbAccess) {
        return dbAccess.findById(id, WayOfPaymentDAO.class);
    }

    public static void truncateTable(DatabaseAccess dbAccess) {
        dbAccess.truncateTable(TABLE_NAME);
    }

    @Override
    public void readFromCSV(CSVReader reader) {
        super.readFromCSV(reader);
        setName(reader.getString(1));
    }
}
