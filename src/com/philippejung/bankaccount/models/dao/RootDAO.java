package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.services.db.DatabaseAccess;
import com.philippejung.bankaccount.services.file.CSVReader;

import java.io.IOException;
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
        return writeToDB(dbAccess, false);
    }

    public Long writeToDB(DatabaseAccess dbAccess, Boolean forceInsertWithId) {
        Map<String, Object> params = new HashMap<>();
        setQueryParams(params);
        if (forceInsertWithId && getId() != -1L) {
            params.put("id", getId());
            dbAccess.insert(getTableName(), params);
            return null;
        }
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

    public static <T extends RootDAO> void restore(String path, DatabaseAccess dbAccess, Class<T> objectClass) {
        T retVal = null;
        CSVReader reader;
        try {
            reader = new CSVReader(path, ",", "\"");
        } catch (IOException e) {
            // File not found, exit
            return;
        }
        while (reader.hasNext()) {
            reader.next();
            try {
                retVal = objectClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            retVal.readFromCSV(reader);
            retVal.writeToDB(dbAccess, true);
        }
    }

    public void readFromCSV(CSVReader reader) {
        Long loadedId = reader.getLong(0);
        setId( loadedId == null ? -1L : loadedId);
    }
}