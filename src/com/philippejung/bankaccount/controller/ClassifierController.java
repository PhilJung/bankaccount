package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.models.dto.*;
import com.philippejung.bankaccount.view.tablecolumn.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 02/02/15.
 *
 * Controller for the classifiers administration screen.
 */
public class ClassifierController implements Initializable {

    @FXML
    private TableColumn<ClassifierDTO, String> detailConditionTestColumn;
    @FXML
    private TableColumn<ClassifierDTO, String> detailConditionValueColumn;
    @FXML
    private TableColumn<ClassifierDTO, String> amountConditionTestColumn;
    @FXML
    private TableColumn<ClassifierDTO, Currency> amountConditionValueColumn;
    @FXML
    private TableColumn<ClassifierDTO, TypeOfTransaction> typeColumn;
    @FXML
    private TableColumn<ClassifierDTO, CategoryDTO> categoryColumn;
    @FXML
    private TableColumn<ClassifierDTO, WayOfPaymentDTO> wayOfPaymentColumn;
    @FXML
    private TableColumn<ClassifierDTO, AccountDTO> accountColumn;
    @FXML
    private TableColumn<ClassifierDTO, Boolean> stopFurtherClassificationColumn;
    @FXML
    private TableView<ClassifierDTO> classifierTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classifierTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DetailConditionTestColumn.inject(detailConditionTestColumn);
        DetailConditionValueColumn.inject(detailConditionValueColumn);
        AmountConditionTestColumn.inject(amountConditionTestColumn);
        AmountConditionValueColumn.inject(amountConditionValueColumn);
        TypeColumn.inject(typeColumn, true);
        CategoryColumn.inject(categoryColumn, true);
        WayOfPaymentColumn.inject(wayOfPaymentColumn, true);
        AccountColumn.inject(accountColumn, true);
        BooleanColumn.inject(stopFurtherClassificationColumn, "stopFurtherClassification");
        classifierTable.setItems(MainApp.getData().getAllClassifiers());
    }

    @SuppressWarnings("UnusedParameters")
    public void onNewClassifierRule(ActionEvent actionEvent) {
        ClassifierDTO newDTO = new ClassifierDTO();
        MainApp.getData().getAllClassifiers().add(newDTO);
    }

    @SuppressWarnings("UnusedParameters")
    public void onDeleteClassierRule(ActionEvent actionEvent) {
        MainApp.getData().getAllClassifiers().removeAll(classifierTable.getSelectionModel().getSelectedItems());
    }
}
