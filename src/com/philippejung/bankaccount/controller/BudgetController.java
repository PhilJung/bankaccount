package com.philippejung.bankaccount.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 24/02/15.
 */
public class BudgetController implements Initializable {
    @FXML
    private StackPane stackPane;

    private SpreadsheetView spreadsheetView = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int rowCount = 18;
        int columnCount = 14;
        GridBase grid = new GridBase(rowCount, columnCount);

        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                list.add(SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "[" + column + "," + row + "]"));
            }
            rows.add(list);
        }
        grid.setRows(rows);

        spreadsheetView = new SpreadsheetView(grid);
        spreadsheetView.setPrefHeight(10);
        spreadsheetView.setPrefWidth(10);
        spreadsheetView.setShowRowHeader(false);
        spreadsheetView.setShowColumnHeader(false);
        spreadsheetView.setEditable(false);
        spreadsheetView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        spreadsheetView.getFixedRows().add(0);
        spreadsheetView.getColumns().get(12).setFixed(true);
        spreadsheetView.getColumns().get(13).setFixed(true);
        stackPane.getChildren().setAll(spreadsheetView);
    }
}
