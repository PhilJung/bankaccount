package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.CategoryDTO;
import com.philippejung.bankaccount.view.tablecolumn.BooleanColumn;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * =================================================
 * Bank Account
 * =================================================
 * <p>
 * v0 Created by philippe on 16/03/15.
 */
public class CategoryController implements Initializable {
    @FXML
    private TableView<CategoryDTO> categoryTable;
    @FXML
    private TableColumn<CategoryDTO, Boolean> isExpenseColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanColumn.inject(isExpenseColumn, "expense");
        categoryTable.setItems(MainApp.getData().getAllCategories());
    }
}
