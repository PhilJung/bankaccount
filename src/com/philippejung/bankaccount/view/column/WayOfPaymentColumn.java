package com.philippejung.bankaccount.view.column;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.WayOfPaymentDTO;
import com.philippejung.bankaccount.view.utils.ObjectStringConverter;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by philippe on 06/02/15.
 */
public final class WayOfPaymentColumn {

    public static <S> void inject(TableColumn<S, WayOfPaymentDTO> tc) {
        // Way of payment, based on WayOfPaymentDTO
        tc.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        new ObjectStringConverter<WayOfPaymentDTO>(), MainApp.getData().getAllWaysOfPayment()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<S, WayOfPaymentDTO>("wayOfPayment")
        );
    }
}
