package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.AccountDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * =================================================
 * Bank Account
 * =================================================
 *
 * v0 Created by philippe on 17/02/15.
 */
public class AccountMovementsByWeekController implements Initializable {
    @FXML
    private BarChart<String, Double> chart;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (AccountDTO accountDTO : MainApp.getData().getAllAccounts()) {
            chart.getData().add(accountDTO.getBalanceVariationByWeeks());
        }
    }
}
