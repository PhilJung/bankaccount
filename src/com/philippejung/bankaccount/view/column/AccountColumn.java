package com.philippejung.bankaccount.view.column;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.AccountDTO;
import com.philippejung.bankaccount.view.utils.ObjectStringConverter;
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
public class AccountColumn {
    public static <S> void inject(TableColumn<S, AccountDTO> tc) {
        // Way of payment, based on WayOfPaymentDTO
        tc.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        new ObjectStringConverter<>(), MainApp.getData().getAllAccounts()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("account")
        );
    }
}
