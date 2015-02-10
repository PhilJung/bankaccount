package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.services.db.DatabaseAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by philippe on 25/01/15.
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

    public void writeToDB(DatabaseAccess dbAccess) {
        Map<String, Object> params = new HashMap<>();
        setQueryParams(params);
        if (getId() == -1L) {
            Long id = dbAccess.insert(getTableName(), params);
            setId(id);
        } else {
            dbAccess.update(getTableName(), getId(), params);
        }
    }

    public void writeToDB() {
        writeToDB(MainApp.getData().getDbAccess());
    }

    protected abstract String getTableName();

    protected abstract void setQueryParams(Map<String, Object> params);

}
