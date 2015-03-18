package com.philippejung.bankaccount.services.db;

import com.philippejung.bankaccount.models.Currency;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 18/03/15.
 */
public class ResultSetWithNull {
    private final ResultSet rs;

    public ResultSetWithNull(ResultSet rs) {
        this.rs = rs;
    }

    public Long getId(String columnName) throws SQLException {
        Long retVal = rs.getLong(columnName);
        if (rs.wasNull()) retVal = -1L;
        return retVal;
    }

    public Long getLong(String columnName) throws SQLException {
        Long retVal = rs.getLong(columnName);
        if (rs.wasNull()) retVal = null;
        return retVal;
    }

    public String getString(String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    public Date getDate(String columnName) throws SQLException {
        return rs.getDate(columnName);
    }

    public Integer getInt(String columnName) throws SQLException {
        Integer retVal = rs.getInt(columnName);
        if (rs.wasNull()) retVal = null;
        return retVal;
    }

    public Boolean getBoolean(String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }

    public Currency getCurrency(String columnName) throws SQLException {
        return new Currency(rs.getLong(columnName));
    }
}
