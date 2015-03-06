package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.models.dto.BudgetDTO;
import com.philippejung.bankaccount.models.dto.CategoryDTO;
import com.philippejung.bankaccount.view.utils.CurrencyTextField;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 05/03/15.
 */
public class BudgetLineController implements ParametrizedController {
    @FXML
    private CurrencyTextField forecast0;
    @FXML
    private CurrencyTextField forecast1;
    @FXML
    private CurrencyTextField forecast2;
    @FXML
    private CurrencyTextField forecast3;
    @FXML
    private CurrencyTextField forecast4;
    @FXML
    private CurrencyTextField forecast5;
    @FXML
    private CurrencyTextField forecast6;
    @FXML
    private CurrencyTextField forecast7;
    @FXML
    private CurrencyTextField forecast8;
    @FXML
    private CurrencyTextField forecast9;
    @FXML
    private CurrencyTextField forecast10;
    @FXML
    private CurrencyTextField forecast11;
    @FXML
    private Label actual0;
    @FXML
    private Label actual1;
    @FXML
    private Label actual2;
    @FXML
    private Label actual3;
    @FXML
    private Label actual4;
    @FXML
    private Label actual5;
    @FXML
    private Label actual6;
    @FXML
    private Label actual7;
    @FXML
    private Label actual8;
    @FXML
    private Label actual9;
    @FXML
    private Label actual10;
    @FXML
    private Label actual11;
    @FXML
    private Label categoryLabel;

    private CategoryDTO categoryDTO;
    private BudgetController mainController;
    private final Label[] allActuals = new Label[12];
    private final CurrencyTextField[] allForecasts = new CurrencyTextField[12];

    private final static Image expenseIcon = new Image(BudgetLineController.class.getResourceAsStream("/res/icons/expense.png"));
    private final static Image incomeIcon = new Image(BudgetLineController.class.getResourceAsStream("/res/icons/income.png"));

    @Override
    public void setControllerParam(Object param1, Object param2) {
        // Save category
        categoryDTO = (CategoryDTO)param1;
        mainController = (BudgetController)param2;
        // Update MMI
        allActuals[0] = actual0;
        allActuals[1] = actual1;
        allActuals[2] = actual2;
        allActuals[3] = actual3;
        allActuals[4] = actual4;
        allActuals[5] = actual5;
        allActuals[6] = actual6;
        allActuals[7] = actual7;
        allActuals[8] = actual8;
        allActuals[9] = actual9;
        allActuals[10] = actual10;
        allActuals[11] = actual11;

        allForecasts[0] = forecast0;
        allForecasts[1] = forecast1;
        allForecasts[2] = forecast2;
        allForecasts[3] = forecast3;
        allForecasts[4] = forecast4;
        allForecasts[5] = forecast5;
        allForecasts[6] = forecast6;
        allForecasts[7] = forecast7;
        allForecasts[8] = forecast8;
        allForecasts[9] = forecast9;
        allForecasts[10] = forecast10;
        allForecasts[11] = forecast11;

        mainController.startDateProperty().addListener(
                (ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
                    setValues(newValue);
                }
        );
    }

    private void setValues(LocalDate startDate) {
        categoryLabel.setText(categoryDTO.getName());
        categoryLabel.setGraphic(new ImageView(categoryDTO.getExpense() ? expenseIcon : incomeIcon));
        for (int i=0; i<12; i++) {
            setValuesForColumn(startDate, i);
        }
    }

    private void setValuesForColumn(LocalDate startDate, int i) {
        BudgetDTO dto;
        Currency budget;
        Currency realised;
        dto = MainApp.getData().getBudgetByDateAndCategory(startDate.plusMonths(i), categoryDTO);
        budget = dto.getBudget();
        realised = dto.getRealisedValue();
        allActuals[i].setText(realised.toString());
        allForecasts[i].setAmount(budget);
        long b = Math.abs(budget.toLong());
        long r = Math.abs(realised.toLong());
        Boolean ok =  categoryDTO.getExpense() && (b>=r)
                ||   !categoryDTO.getExpense() && (r>=b);
        if (ok) {
            allActuals[i].getStyleClass().remove("overBudget");
            allActuals[i].getStyleClass().add("underBudget");
        } else {
            allActuals[i].getStyleClass().remove("underBudget");
            allActuals[i].getStyleClass().add("overBudget");
        }
    }
}
