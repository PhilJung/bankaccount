package com.philippejung.bankaccount.view.column;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 06/02/15.
 */
public class DetailConditionTestColumn {

    private static final String[] possibleValues = {"contains", "endsWith", "equals", "matches", "startsWith"};

    public static <T> void inject(TableColumn<T, String> tc) {
        tc.setCellFactory(ComboBoxTableCell.forTableColumn(possibleValues));
        tc.setCellValueFactory(new PropertyValueFactory<>("detailConditionTest"));
    }
}
