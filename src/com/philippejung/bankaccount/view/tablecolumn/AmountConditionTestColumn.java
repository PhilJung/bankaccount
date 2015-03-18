package com.philippejung.bankaccount.view.tablecolumn;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 07/02/15.
 */
public class AmountConditionTestColumn {

    private static final String[] possibleValues = {"<", ">", "<=", ">=", "==", "!="};

    public static <T> void inject(TableColumn<T, String> tc) {
        tc.setCellFactory(ComboBoxTableCell.forTableColumn(possibleValues));
        tc.setCellValueFactory(new PropertyValueFactory<>("amountConditionTest"));
    }
}
