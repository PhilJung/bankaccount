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
public class TransactionDAO extends RootDAO {
    private final static String TABLE_NAME = "[transaction]";

    private Date date;
    private Integer type;
    private Long accountId;
    private Long otherAccountId;
    private Long otherTransactionId;
    private Long wayOfPaymentId;
    private Currency amount;
    private String detail;
    private String comment;
    private Long categoryId;

    public TransactionDAO() {
        super();
    }

    public void readFromDB(ResultSetWithNull rs) throws SQLException {
        super.readFromDB(rs);
        setDate(rs.getDate("date"));
        setType(rs.getInt("type"));
        setAccountId(rs.getId("accountId"));
        setOtherAccountId(rs.getId("otherAccountId"));
        setOtherTransactionId(rs.getId("otherTransactionId"));
        setWayOfPaymentId(rs.getId("wayOfPaymentId"));
        setAmount(rs.getCurrency("amount"));
        setDetail(rs.getString("detail"));
        setComment(rs.getString("comment"));
        setCategoryId(rs.getId("categoryId"));
    }

    public static ArrayList<TransactionDAO> getAll() {
        return MainApp.getData().getDbAccess().select("SELECT * FROM " + TABLE_NAME + " ORDER BY date, accountId", TransactionDAO.class);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOtherAccountId() {
        return otherAccountId;
    }

    public void setOtherAccountId(Long otherAccountId) {
        this.otherAccountId = otherAccountId;
    }

    public Long getOtherTransactionId() {
        return otherTransactionId;
    }

    public void setOtherTransactionId(Long otherTransactionId) {
        this.otherTransactionId = otherTransactionId;
    }

    public Long getWayOfPaymentId() {
        return wayOfPaymentId;
    }

    public void setWayOfPaymentId(Long wayOfPaymentId) {
        this.wayOfPaymentId = wayOfPaymentId;
    }

    public Currency getAmount() {
        return amount;
    }

    public void setAmount(Currency amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("date", getDate());
        params.put("type", getType());
        params.put("accountId", getAccountId());
        params.put("otherAccountId", getOtherAccountId());
        params.put("otherTransactionId", getOtherTransactionId());
        params.put("wayOfPaymentId", getWayOfPaymentId());
        params.put("amount", getAmount());
        params.put("detail", getDetail());
        params.put("comment", getComment());
        params.put("categoryId", getCategoryId());
    }

    public static TransactionDAO byId(long id) {
        return byId(id, MainApp.getData().getDbAccess());
    }

    public static TransactionDAO byId(long id, DatabaseAccess dbAccess) {
        return dbAccess.findById(id, TransactionDAO.class);
    }

    public static void truncateTable(DatabaseAccess dbAccess) {
        dbAccess.truncateTable(TABLE_NAME);
    }

    @Override
    public void readFromCSV(CSVReader reader) {
        super.readFromCSV(reader);
        setDate(reader.getDate(1));
        setType(reader.getInt(2));
        setAccountId(reader.getId(3));
        setOtherAccountId(reader.getId(4));
        setOtherTransactionId(reader.getId(5));
        setWayOfPaymentId(reader.getId(6));
        setAmount(reader.getCurrency(7));
        setDetail(reader.getString(8));
        setComment(reader.getString(9));
        setCategoryId(reader.getId(10));
    }

    @Override
    public void writeToCSV(CSVWriter writer) {
        super.writeToCSV(writer);
        writer.writeDate(getDate());
        writer.writeInt(getType());
        writer.writeId(getAccountId());
        writer.writeId(getOtherAccountId());
        writer.writeId(getOtherTransactionId());
        writer.writeId(getWayOfPaymentId());
        writer.writeCurrency(getAmount());
        writer.writeString(getDetail());
        writer.writeString(getComment());
        writer.writeId(getCategoryId());
    }

    @Override
    public String toString() {
        return "TransactionDAO{" +
                "date=" + date +
                ", detail='" + detail + '\'' +
                ", amount=" + amount +
                '}';
    }
}
