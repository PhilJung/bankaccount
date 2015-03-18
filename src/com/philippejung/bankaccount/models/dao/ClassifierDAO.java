package com.philippejung.bankaccount.models.dao;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.services.db.DatabaseAccess;
import com.philippejung.bankaccount.services.db.ResultSetWithNull;
import com.philippejung.bankaccount.services.file.CSVReader;
import com.philippejung.bankaccount.services.file.CSVWriter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 30/01/15.
 */
public class ClassifierDAO extends RootDAO {
    private final static String TABLE_NAME = "Classifier";

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

    public static ArrayList<ClassifierDAO> getAll() {
        return MainApp.getData().getDbAccess().select("SELECT * FROM " + TABLE_NAME, ClassifierDAO.class);
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

    public void readFromDB(ResultSetWithNull rs) throws SQLException {
        super.readFromDB(rs);
        setDetailConditionTest(rs.getString("detailConditionTest"));
        setDetailConditionValue(rs.getString("detailConditionValue"));
        setAmountConditionTest(rs.getString("amountConditionTest"));
        setAmountConditionValue(rs.getCurrency("amountConditionValue"));
        setNewTypeId(rs.getInt("newTypeId"));
        setNewWayOfPaymentId(rs.getId("newWayOfPaymentId"));
        setNewOtherAccountId(rs.getId("newOtherAccountId"));
        setNewCategoryId(rs.getId("newCategoryId"));
        setStopFurtherClassification(rs.getBoolean("stopFurtherClassification"));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
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

    public static void truncateTable(DatabaseAccess dbAccess) {
        dbAccess.truncateTable(TABLE_NAME);
    }

    @Override
    public void readFromCSV(CSVReader reader) {
        super.readFromCSV(reader);
        setDetailConditionTest(reader.getString(1));
        setDetailConditionValue(reader.getString(2));
        setAmountConditionTest(reader.getString(3));
        setAmountConditionValue(reader.getCurrency(4));
        setNewTypeId(reader.getInt(5));
        setNewWayOfPaymentId(reader.getId(6));
        setNewOtherAccountId(reader.getId(7));
        setNewCategoryId(reader.getId(8));
        setStopFurtherClassification(reader.getBoolean(9));
    }

    @Override
    public void writeToCSV(CSVWriter writer) {
        super.writeToCSV(writer);
        writer.writeString(getDetailConditionTest());
        writer.writeString(getDetailConditionValue());
        writer.writeString(getAmountConditionTest());
        writer.writeCurrency(getAmountConditionValue());
        writer.writeInt(getNewTypeId());
        writer.writeId(getNewWayOfPaymentId());
        writer.writeId(getNewOtherAccountId());
        writer.writeId(getNewCategoryId());
        writer.writeBoolean(getStopFurtherClassification());
    }

    @Override
    public String toString() {
        return "ClassifierDAO{" +
                "detailConditionTest='" + detailConditionTest + '\'' +
                ", detailConditionValue='" + detailConditionValue + '\'' +
                ", amountConditionTest='" + amountConditionTest + '\'' +
                ", amountConditionValue=" + amountConditionValue +
                '}';
    }
}
