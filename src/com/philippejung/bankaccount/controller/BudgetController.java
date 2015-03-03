package com.philippejung.bankaccount.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 24/02/15.
 */
public class BudgetController implements Initializable {
    @FXML
    private TableView budgetTable = null;

    private TableColumn[] valueColumns = new TableColumn[12];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTableStructure();
    }

    private void createTableStructure() {
        for (int month= 0; month<12; month++) {
            valueColumns[month] = new TableColumn("M" + Integer.toString(month));
        }
        budgetTable.getColumns().addAll(valueColumns);
    }
}
