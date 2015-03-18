package com.philippejung.bankaccount.view.tablecolumn;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 07/02/15.
 */
public class BooleanColumn {
    public static <T> void inject(TableColumn<T, Boolean> tc, String propertyName) {
        tc.setCellFactory(
                (p) -> new CheckBoxTableCell<>()
        );
        tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));

    }
}
