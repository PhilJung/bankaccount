package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.services.db.DatabaseAccess;
import com.philippejung.bankaccount.services.db.ResultSetWithNull;
import com.philippejung.bankaccount.services.file.CSVReader;
import com.philippejung.bankaccount.services.file.CSVWriter;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public class BudgetDAO extends RootDAO {
    private final static String TABLE_NAME = "budget";

    private Date month; // YYYYMM
    private Long categoryId;
    private Currency budget;

    public BudgetDAO() {
        super();
    }

    public static ArrayList<BudgetDAO> getAll() {
        return MainApp.getData().getDbAccess().select("SELECT * FROM " + TABLE_NAME + " ORDER BY categoryId, month", BudgetDAO.class);
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Currency getBudget() {
        return budget;
    }

    public void setBudget(Currency budget) {
        this.budget = budget;
    }

    public void readFromDB(ResultSetWithNull rs) throws SQLException {
        super.readFromDB(rs);
        this.month = rs.getDate("month");
        this.categoryId = rs.getId("categoryId");
        this.budget = rs.getCurrency("budget");
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("month", getMonth());
        params.put("categoryId", getCategoryId());
        params.put("budget", getBudget());
    }

    public static BudgetDAO byId(long id) {
        return byId(id, MainApp.getData().getDbAccess());
    }

    public static BudgetDAO byId(long id, DatabaseAccess dbAccess) {
        return dbAccess.findById(id, BudgetDAO.class);
    }

    public static void truncateTable(DatabaseAccess dbAccess) {
        dbAccess.truncateTable(TABLE_NAME);
    }

    @Override
    public void readFromCSV(CSVReader reader) {
        super.readFromCSV(reader);
        setMonth(reader.getDate(1));
        setCategoryId(reader.getId(2));
        setBudget(reader.getCurrency(3));
    }

    @Override
    public void writeToCSV(CSVWriter writer) {
        super.writeToCSV(writer);
        writer.writeDate(getMonth());
        writer.writeId(getCategoryId());
        writer.writeCurrency(getBudget());
    }

    @Override
    public String toString() {
        return "BudgetDAO{" +
                "month=" + month +
                ", categoryId=" + categoryId +
                ", budget=" + budget +
                '}';
    }
}
