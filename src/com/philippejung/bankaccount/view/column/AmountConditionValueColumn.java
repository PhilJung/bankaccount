package com.philippejung.bankaccount.view.column;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LongStringConverter;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 07/02/15.
 */
public class AmountConditionValueColumn {
    public static <T> void inject(TableColumn<T, Long> tc) {
        tc.setCellFactory(
                TextFieldTableCell.forTableColumn(
                        new LongStringConverter()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("amountConditionValue")
        );
    }
}
