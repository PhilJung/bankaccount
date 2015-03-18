package com.philippejung.bankaccount.view.tablecolumn;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.AccountDTO;
import com.philippejung.bankaccount.models.dto.RootDTO;
import com.philippejung.bankaccount.models.interfaces.AccountPropertyProvider;
import com.philippejung.bankaccount.utils.utils.ObjectStringConverter;
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
    public static <S extends RootDTO & AccountPropertyProvider> void inject(TableColumn<S, AccountDTO> tc, Boolean autoSave) {
        // Way of payment, based on WayOfPaymentDTO
        tc.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        new ObjectStringConverter<>(), MainApp.getData().getAllAccounts()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("account")
        );
        if (autoSave) {
            tc.setOnEditCommit(
                    (TableColumn.CellEditEvent<S, AccountDTO> event) -> {
                        S impactedObject = (S) event.getTableView().getItems().get(event.getTablePosition().getRow());
                        AccountDTO newValue = event.getNewValue();
                        impactedObject.setAccount(newValue);
                        impactedObject.writeToDB();
                    }
            );
        }
    }
}
