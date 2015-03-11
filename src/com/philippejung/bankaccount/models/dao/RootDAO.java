package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.services.db.DatabaseAccess;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public abstract class RootDAO {
    private Long id = -1L;

    public RootDAO() {
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long writeToDB(DatabaseAccess dbAccess) {
        Map<String, Object> params = new HashMap<>();
        setQueryParams(params);
        if (getId() == -1L) {
            Long id = dbAccess.insert(getTableName(), params);
            setId(id);
            return id;
        } else {
            dbAccess.update(getTableName(), getId(), params);
            return null;
        }
    }

    public abstract String getTableName();

    protected abstract void setQueryParams(Map<String, Object> params);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //noinspection SimplifiableIfStatement
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((RootDAO) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void restore(String path, DatabaseAccess dbAccess) {
        File file = new File(path + getTableName() + ".csv");
        dbAccess.truncateTable(DatabaseAccess dbAccess);


    }

    private void truncateTable() {

    }

}
