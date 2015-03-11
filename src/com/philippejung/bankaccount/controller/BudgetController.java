package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.CategoryDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
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
    private SimpleObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();

    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        // When setting startDate, listeners may generate new objects in database. Do it inside a single transaction
        MainApp.getData().getDbAccess().beginTransaction();
        this.startDate.set(startDate);
        // Framework is allowed to write new objects into database
        MainApp.getData().getDbAccess().commitTransaction();
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainApp.getMainController().setProgress(0);
        // In case we need to create new objects in database, all will be written at once
        MainApp.getData().getDbAccess().beginTransaction();
        int nbTotal = MainApp.getData().getAllCategories().size() + 1;
        int cpteur = 0;
        Pane newPane = loadPane("/res/fxml/budgetheaderline.fxml", this);
        budgetVBox.getChildren().add(newPane);
        VBox.setVgrow(newPane, Priority.NEVER);
        cpteur++;
        MainApp.getMainController().setProgress( (double) cpteur / (double) nbTotal);
        for(CategoryDTO categoryDTO : MainApp.getData().getAllCategories()) {
            newPane = loadPane("/res/fxml/budgetline.fxml", categoryDTO, this);
            budgetVBox.getChildren().add(newPane);
            VBox.setVgrow(newPane, Priority.NEVER);
            cpteur++;
            MainApp.getMainController().setProgress( (double) cpteur / (double) nbTotal);
        }
        // In case we need to create new objects in database, all will be written now
        MainApp.getData().getDbAccess().commitTransaction();
        // In case we need to create new objects in database, all will be written at once
        setStartDate(LocalDate.now().withDayOfMonth(1).minusMonths(12));
        MainApp.getMainController().setProgress( (double) cpteur / (double) nbTotal);
    }

    @SuppressWarnings("UnusedParameters")
    public void onPreviousYear(ActionEvent actionEvent) {
        setStartDate(getStartDate().minusYears(1));
    }

    @SuppressWarnings("UnusedParameters")
    public void onPreviousMonth(ActionEvent actionEvent) {
        setStartDate(getStartDate().minusMonths(1));
    }

    @SuppressWarnings("UnusedParameters")
    public void onNextYear(ActionEvent actionEvent) {
        setStartDate(getStartDate().plusYears(1));
    }

    @SuppressWarnings("UnusedParameters")
    public void onNextMonth(ActionEvent actionEvent) {
        setStartDate(getStartDate().plusMonths(1));
    }

}
