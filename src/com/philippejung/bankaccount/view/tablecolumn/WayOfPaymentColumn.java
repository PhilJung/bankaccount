package com.philippejung.bankaccount.view.tablecolumn;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.RootDTO;
import com.philippejung.bankaccount.models.dto.WayOfPaymentDTO;
import com.philippejung.bankaccount.models.interfaces.WayOfPaymentPropertyProvider;
import com.philippejung.bankaccount.utils.utils.ObjectStringConverter;
import javafx.event.EventHandler;
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
public final class WayOfPaymentColumn {

    public static <S extends RootDTO & WayOfPaymentPropertyProvider> void inject(TableColumn<S, WayOfPaymentDTO> tc, Boolean autoSave) {
        // Way of payment, based on WayOfPaymentDTO
        tc.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        new ObjectStringConverter<>(), MainApp.getData().getAllWaysOfPayment()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("wayOfPayment")
        );
        tc.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<S, WayOfPaymentDTO>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<S, WayOfPaymentDTO> event) {

            }
        });
        if (autoSave) {
            tc.setOnEditCommit(
                    (TableColumn.CellEditEvent<S, WayOfPaymentDTO> event) -> {
                        S impactedObject = (S) event.getTableView().getItems().get(event.getTablePosition().getRow());
                        WayOfPaymentDTO newValue = event.getNewValue();
                        impactedObject.setWayOfPayment(newValue);
                        impactedObject.writeToDB();
                    }
            );
        }
    }
}
