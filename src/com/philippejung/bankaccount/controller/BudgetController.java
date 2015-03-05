package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.CategoryDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 24/02/15.
 */
public class BudgetController extends GenericController implements Initializable {
    @FXML
    private VBox budgetVBox;
    private LocalDate startDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startDate = LocalDate.now().withDayOfMonth(1).minusMonths(12);
        for(CategoryDTO categoryDTO : MainApp.getData().getAllCategories()) {
            Pane newPane = loadPane("/res/fxml/budgetline.fxml", categoryDTO, this);
            budgetVBox.getChildren().add(newPane);
            VBox.setVgrow(newPane, Priority.NEVER);
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
