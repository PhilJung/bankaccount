package com.philippejung.bankaccount.view.column;

import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.view.utils.ObjectStringConverter;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 07/02/15.
 */
public class AmountConditionValueColumn {
    public static <T> void inject(TableColumn<T, Currency> tc) {
        tc.setCellFactory(
                TextFieldTableCell.forTableColumn(
                        new ObjectStringConverter<>()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("amountConditionValue")
        );
    }
}
