package com.philippejung.view.utils;

import com.philippejung.data.models.logical.WayOfPaymentDTO;
import com.philippejung.main.MainApp;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 06/02/15.
 */
public final class WayOfPaymentColumn {

    public static <S> void inject(TableColumn<S, WayOfPaymentDTO> tc) {
        // Way of payment, based on WayOfPaymentDTO
        tc.setCellFactory(
                tableCol -> { return new SearchableComboBoxTableCell<>(new ObjectStringConverter<WayOfPaymentDTO>(), MainApp.getData().getAllWaysOfPayment()); }
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<S, WayOfPaymentDTO>("wayOfPayment")
        );
    }
}
