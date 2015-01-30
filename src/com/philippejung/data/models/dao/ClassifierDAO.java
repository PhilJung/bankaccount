package com.philippejung.data.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by philippe on 30/01/15.
 */
public class ClassifierDAO extends RootDAO {
    private String detailConditionTest;
    private String detailConditionValue;
    private String amountConditionTest;
    private Double amountConditionValue;
    private Integer newTypeId;
    private Integer newWayOfPaymentId;
    private Integer newOtherAccountId;
    private Integer newCategoryId;
    private Boolean stopFurtherClassification;

    public ClassifierDAO() {
    }

    public String getDetailConditionTest() {
        return detailConditionTest;
    }

    public void setDetailConditionTest(String detailConditionTest) {
        this.detailConditionTest = detailConditionTest;
    }

    public String getDetailConditionValue() {
        return detailConditionValue;
    }

    public void setDetailConditionValue(String detailConditionValue) {
        this.detailConditionValue = detailConditionValue;
    }

    public String getAmountConditionTest() {
        return amountConditionTest;
    }

    public void setAmountConditionTest(String amountConditionTest) {
        this.amountConditionTest = amountConditionTest;
    }

    public Double getAmountConditionValue() {
        return amountConditionValue;
    }

    public void setAmountConditionValue(Double amountConditionValue) {
        this.amountConditionValue = amountConditionValue;
    }

    public Integer getNewTypeId() {
        return newTypeId;
    }

    public void setNewTypeId(Integer newTypeId) {
        this.newTypeId = newTypeId;
    }

    public Integer getNewWayOfPaymentId() {
        return newWayOfPaymentId;
    }

    public void setNewWayOfPaymentId(Integer newWayOfPaymentId) {
        this.newWayOfPaymentId = newWayOfPaymentId;
    }

    public Integer getNewOtherAccountId() {
        return newOtherAccountId;
    }

    public void setNewOtherAccountId(Integer newOtherAccountId) {
        this.newOtherAccountId = newOtherAccountId;
    }

    public Integer getNewCategoryId() {
        return newCategoryId;
    }

    public void setNewCategoryId(Integer newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public Boolean getStopFurtherClassification() {
        return stopFurtherClassification;
    }

    public void setStopFurtherClassification(Boolean stopFurtherClassification) {
        this.stopFurtherClassification = stopFurtherClassification;
    }

    public void readFromDB(ResultSet rs) throws SQLException {
        super.readFromDB(rs);
        this.setDetailConditionTest(rs.getString("detailConditionTest"));
        this.setDetailConditionValue(rs.getString("detailConditionValue"));
        this.setAmountConditionTest(rs.getString("amountConditionTest"));
        this.setAmountConditionValue(rs.getDouble("amountConditionValue"));
        this.setNewTypeId(rs.getInt("newTypeId"));
        this.setNewWayOfPaymentId(rs.getInt("newWayOfPaymentId"));
        this.setNewOtherAccountId(rs.getInt("newOtherAccountId"));
        this.setNewCategoryId(rs.getInt("newCategoryId"));
        this.setStopFurtherClassification(rs.getBoolean("stopFurtherClassification"));
    }

}
