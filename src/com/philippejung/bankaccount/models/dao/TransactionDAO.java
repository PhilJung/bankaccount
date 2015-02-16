package com.philippejung.bankaccount.models.dao;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public class TransactionDAO extends RootDAO {
    private Date date;
    private Integer type;
    private Long accountId;
    private Long otherAccountId;
    private Long otherTransactionId;
    private Long wayOfPaymentId;
    private Double amount;
    private String detail;
    private String comment;
    private Long categoryId;

    public TransactionDAO() {
        super();
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.date = rs.getDate("date");
        this.type = rs.getInt("type");
        this.accountId = rs.getLong("accountId");
        this.otherAccountId = rs.getLong("otherAccountId");
        this.otherTransactionId = rs.getLong("otherTransactionId");
        this.wayOfPaymentId = rs.getLong("wayOfPaymentId");
        this.amount = rs.getDouble("amount");
        this.detail = rs.getString("detail");
        this.comment = rs.getString("comment");
        this.categoryId = rs.getLong("categoryId");
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
    protected String getTableName() {
        return "[transaction]";
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


}
