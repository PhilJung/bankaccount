package com.philippejung.data.models.dao;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by philippe on 25/01/15.
 */
public class TransactionDAO extends RootDAO {
    private Date date;
    private Integer type;
    private Integer otherAccountId;
    private Integer otherTransactionId;
    private Integer wayOfPaymentId;
    private Double amount;
    private String detail;
    private String comment;

    public TransactionDAO() {
        super();
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.date = rs.getDate("date");
        this.type = rs.getInt("type");
        this.otherAccountId = rs.getInt("otherAccountId");
        this.otherTransactionId = rs.getInt("otherTransactionId");
        this.wayOfPaymentId = rs.getInt("wayOfPayment");
        this.amount = rs.getDouble("amount");
        this.detail = rs.getString("detail");
        this.comment = rs.getString("comment");
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

    public Integer getOtherAccountId() {
        return otherAccountId;
    }

    public void setOtherAccountId(Integer otherAccountId) {
        this.otherAccountId = otherAccountId;
    }

    public Integer getOtherTransactionId() {
        return otherTransactionId;
    }

    public void setOtherTransactionId(Integer otherTransactionId) {
        this.otherTransactionId = otherTransactionId;
    }

    public Integer getWayOfPaymentId() {
        return wayOfPaymentId;
    }

    public void setWayOfPaymentId(Integer wayOfPaymentId) {
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

}
