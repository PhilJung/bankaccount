package com.philippejung.bankaccount.view.column;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by philippe on 07/02/15.
 */
public class AmountConditionTestColumn {

    private static String[] possibleValues = {"<", ">", "<=", ">=", "==", "!="};

    public static <T> void inject(TableColumn<T, String> tc) {
        tc.setCellFactory(ComboBoxTableCell.forTableColumn(possibleValues));
        tc.setCellValueFactory(new PropertyValueFactory<T, String>("amountConditionTest"));
    }
}
