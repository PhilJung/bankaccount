package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.services.db.DatabaseAccess;

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
public class CategoryDAO extends RootDAO {
    private String name;
    // True if the category is an expense, false if it is incoming money
    private Boolean expense;

    public CategoryDAO() {
        super();
        setName("");
        setExpense(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isExpense() {
        return expense;
    }

    public void setExpense(Boolean expense) {
        this.expense = expense;
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.name = rs.getString("name");
        this.expense = rs.getBoolean("isexpense");
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("name", getName());
        params.put("isexpense", isExpense());
    }

    @Override
    public String getTableName() {
        return "Category";
    }

    public static CategoryDAO byId(long id) {
        return byId(id, MainApp.getData().getDbAccess());
    }

    public static CategoryDAO byId(long id, DatabaseAccess dbAccess) {
        return dbAccess.findById(id, CategoryDAO.class);
    }
}
