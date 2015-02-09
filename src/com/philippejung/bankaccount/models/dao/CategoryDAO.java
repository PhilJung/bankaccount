package com.philippejung.bankaccount.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by philippe on 29/01/15.
 */
public class CategoryDAO extends RootDAO {
    private String name;
    // True if the category is an expense, false if it is incoming money
    private boolean expense;

    public CategoryDAO() {
        setName("");
        setExpense(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExpense() {
        return expense;
    }

    public void setExpense(boolean expense) {
        this.expense = expense;
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.name = rs.getString("name");
        this.expense = rs.getBoolean("isexpense");
    }
}
