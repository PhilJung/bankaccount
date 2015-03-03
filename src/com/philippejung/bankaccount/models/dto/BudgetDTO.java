package com.philippejung.bankaccount.models.dto;


import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dao.BudgetDAO;
import com.philippejung.bankaccount.models.dao.RootDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 29/01/15.
 */
public class BudgetDTO extends RootDTO {
    private final SimpleObjectProperty<LocalDate> month = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<CategoryDTO> category = new SimpleObjectProperty<>();
    private final SimpleLongProperty budget = new SimpleLongProperty();
    private final SimpleLongProperty realisedValue = new SimpleLongProperty();

    public BudgetDTO() {
        super();
        setMonth(LocalDate.MIN);
        setBudget(0);
        setCategory(null);
        setRealisedValue(0);
    }

    private BudgetDTO(BudgetDAO dao) {
        super(dao);
        setMonth(dao.getMonth().toLocalDate());
        setCategory(MainApp.getData().getCategoryById(dao.getCategoryId()));
        setBudget(dao.getBudget());
    }

    public void toDAO(RootDAO dao) {
        super.toDAO(dao);
        BudgetDAO budgetDAO = (BudgetDAO)dao;
        budgetDAO.setMonth(java.sql.Date.valueOf(getMonth()));
        budgetDAO.setBudget(getBudget());
        budgetDAO.setCategoryId(idOf(getCategory()));
    }

    public LocalDate getMonth() {
        return month.get();
    }

    public SimpleObjectProperty<LocalDate> monthProperty() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month.set(month);
    }

    public CategoryDTO getCategory() {
        return category.get();
    }

    public SimpleObjectProperty<CategoryDTO> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category.set(category);
    }

    public long getBudget() {
        return budget.get();
    }

    public SimpleLongProperty budgetProperty() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget.set(budget);
    }

    public long getRealisedValue() {
        return realisedValue.get();
    }

    public SimpleLongProperty realisedValueProperty() {
        return realisedValue;
    }

    public void setRealisedValue(long realisedValue) {
        this.realisedValue.set(realisedValue);
    }

    public static ObservableList<BudgetDTO> getAll() {
        ArrayList<BudgetDAO> queryResult = MainApp.getData().getDbAccess().select("SELECT * FROM budget", BudgetDAO.class);
        ArrayList<BudgetDTO> retVal = new ArrayList<>();
        for(BudgetDAO budgetDAO : queryResult) {
            retVal.add(new BudgetDTO(budgetDAO));
        }
        return FXCollections.observableArrayList(retVal);
    }

    @Override
    public RootDAO newDAO() {
        return new BudgetDAO();
    }

    @Override
    public String toString() {
        return getCategory().toString() + "@" + getMonth().toString() + ":" + getBudget();
    }

    public static LocalDate monthForDate(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    public void addTransaction(TransactionDTO transaction) {
        setRealisedValue(getRealisedValue() + transaction.getAmount());
    }
}
