package com.philippejung.bankaccount.view.tablecolumn;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 06/02/15.
 */
public class DetailConditionValueColumn {
    public static <T> void inject(TableColumn<T, String> tc) {
        tc.setCellFactory(
                TextFieldTableCell.forTableColumn(
                        new DefaultStringConverter()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("detailConditionValue")
        );
    }
}
