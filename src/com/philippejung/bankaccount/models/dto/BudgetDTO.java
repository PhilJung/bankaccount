package com.philippejung.bankaccount.models.dto;


import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.models.dao.BudgetDAO;
import com.philippejung.bankaccount.models.dao.RootDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

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
    private final SimpleObjectProperty<Currency> budget = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Currency> realisedValue = new SimpleObjectProperty<>();

    public BudgetDTO() {
        super();
        setMonth(LocalDate.MIN);
        setBudget(Currency.zero());
        setCategory(null);
        setRealisedValue(Currency.zero());
    }

    private BudgetDTO(BudgetDAO dao) {
        super(dao);
        setMonth(dao.getMonth().toLocalDate());
        setCategory(MainApp.getData().getCategoryById(dao.getCategoryId()));
        setBudget(dao.getBudget());
        setRealisedValue(Currency.zero());
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

    public Currency getBudget() {
        return budget.get();
    }

    public SimpleObjectProperty<Currency> budgetProperty() {
        return budget;
    }

    public void setBudget(Currency budget) {
        this.budget.set(budget);
        if (budget!=null) {
            budget.valueProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                        if (oldValue != newValue) {
                            System.out.println("Saving " + this.toString());
                            writeToDB();
                        }
                    }
            );
        }
    }

    public Currency getRealisedValue() {
        return realisedValue.get();
    }

    public SimpleObjectProperty<Currency> realisedValueProperty() {
        return realisedValue;
    }

    public void setRealisedValue(Currency realisedValue) {
        this.realisedValue.set(realisedValue);
    }

    public static ObservableList<BudgetDTO> getAll() {
        ArrayList<BudgetDAO> queryResult = BudgetDAO.getAll();
        ArrayList<BudgetDTO> retVal = new ArrayList<>();
        for(BudgetDAO budgetDAO : queryResult) {
            // Budget DTO are autosaved
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
        getRealisedValue().setValue(getRealisedValue().plus(transaction.getAmount()));
    }
}
