package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.services.db.DatabaseAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 30/01/15.
 */
public class ClassifierDAO extends RootDAO {
    private String detailConditionTest;
    private String detailConditionValue;
    private String amountConditionTest;
    private Currency amountConditionValue;
    private Integer newTypeId;
    private Long newWayOfPaymentId;
    private Long newOtherAccountId;
    private Long newCategoryId;
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

    public Currency getAmountConditionValue() {
        return amountConditionValue;
    }

    public void setAmountConditionValue(Currency amountConditionValue) {
        this.amountConditionValue = amountConditionValue;
    }

    public Integer getNewTypeId() {
        return newTypeId;
    }

    public void setNewTypeId(Integer newTypeId) {
        this.newTypeId = newTypeId;
    }

    public Long getNewWayOfPaymentId() {
        return newWayOfPaymentId;
    }

    public void setNewWayOfPaymentId(Long newWayOfPaymentId) {
        this.newWayOfPaymentId = newWayOfPaymentId;
    }

    public Long getNewOtherAccountId() {
        return newOtherAccountId;
    }

    public void setNewOtherAccountId(Long newOtherAccountId) {
        this.newOtherAccountId = newOtherAccountId;
    }

    public Long getNewCategoryId() {
        return newCategoryId;
    }

    public void setNewCategoryId(Long newCategoryId) {
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
        this.setAmountConditionValue(new Currency(rs.getLong("amountConditionValue")));
        this.setNewTypeId(rs.getInt("newTypeId"));
        this.setNewWayOfPaymentId(rs.getLong("newWayOfPaymentId"));
        this.setNewOtherAccountId(rs.getLong("newOtherAccountId"));
        this.setNewCategoryId(rs.getLong("newCategoryId"));
        this.setStopFurtherClassification(rs.getBoolean("stopFurtherClassification"));
    }

    @Override
    public String getTableName() {
        return "Classifier";
    }

    @Override
    protected void setQueryParams(Map<String, Object> params) {
        params.put("detailConditionTest", getDetailConditionTest());
        params.put("detailConditionValue", getDetailConditionValue());
        params.put("amountConditionTest", getAmountConditionTest());
        params.put("amountConditionValue", getAmountConditionValue());
        params.put("newTypeId", getNewTypeId());
        params.put("newWayOfPaymentId", getNewWayOfPaymentId());
        params.put("newOtherAccountId", getNewOtherAccountId());
        params.put("newCategoryId", getNewCategoryId());
        params.put("stopFurtherClassification", getStopFurtherClassification());
    }

    public static ClassifierDAO byId(long id) {
        return byId(id, MainApp.getData().getDbAccess());
    }

    public static ClassifierDAO byId(long id, DatabaseAccess dbAccess) {
        return dbAccess.findById(id, ClassifierDAO.class);
    }
}
