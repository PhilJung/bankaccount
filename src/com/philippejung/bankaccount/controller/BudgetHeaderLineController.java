package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.view.utils.CurrencyTextField;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 06/03/15.
 */
public class BudgetHeaderLineController implements ParametrizedController {
    @FXML
    private Label date0;
    @FXML
    private Label date1;
    @FXML
    private Label date2;
    @FXML
    private Label date3;
    @FXML
    private Label date4;
    @FXML
    private Label date5;
    @FXML
    private Label date6;
    @FXML
    private Label date7;
    @FXML
    private Label date8;
    @FXML
    private Label date9;
    @FXML
    private Label date10;
    @FXML
    private Label date11;
    @FXML
    private Label categoryLabel;

    private final Label[] allDates = new Label[12];
    private final CurrencyTextField[] allForecasts = new CurrencyTextField[12];

    @Override
    public void setControllerParam(Object param1, Object param2) {
        // Update MMI
        allDates[0] = date0;
        allDates[1] = date1;
        allDates[2] = date2;
        allDates[3] = date3;
        allDates[4] = date4;
        allDates[5] = date5;
        allDates[6] = date6;
        allDates[7] = date7;
        allDates[8] = date8;
        allDates[9] = date9;
        allDates[10] = date10;
        allDates[11] = date11;

        ((BudgetController) param1).startDateProperty().addListener(
                (ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
                    setValues(newValue);
                }
        );
    }

    private void setValues(LocalDate startDate) {
        categoryLabel.setText("");
        for (int i = 0; i < 12; i++) {
            LocalDate theDate = startDate.plusMonths(i);
            String label = Integer.toString(theDate.getMonthValue()) + "/" + Integer.toString(theDate.getYear());
            allDates[i].setText(label);
        }
    }
}

