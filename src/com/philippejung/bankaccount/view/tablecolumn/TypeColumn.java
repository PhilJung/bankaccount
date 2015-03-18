package com.philippejung.bankaccount.view.tablecolumn;

import com.philippejung.bankaccount.models.dto.RootDTO;
import com.philippejung.bankaccount.models.dto.TypeOfTransaction;
import com.philippejung.bankaccount.models.interfaces.TypePropertyProvider;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 07/02/15.
 */
public class TypeColumn {

    public static <S extends RootDTO & TypePropertyProvider> void inject(TableColumn<S, TypeOfTransaction> tc, Boolean autoSave) {

        ArrayList<TypeOfTransaction> typeOfTransactions = new ArrayList<>();
        typeOfTransactions.add(TypeOfTransaction.EXPENSE);
        typeOfTransactions.add(TypeOfTransaction.INCOME);
        typeOfTransactions.add(TypeOfTransaction.TRANSFER_FROM);
        typeOfTransactions.add(TypeOfTransaction.TRANSFER_TO);
        tc.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(typeOfTransactions)));

        tc.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        if (autoSave) {
            tc.setOnEditCommit(
                    (TableColumn.CellEditEvent<S, TypeOfTransaction> event) -> {
                        S impactedObject = (S) event.getTableView().getItems().get(event.getTablePosition().getRow());
                        TypeOfTransaction newValue = event.getNewValue();
                        impactedObject.setType(newValue);
                        impactedObject.writeToDB();
                    }
            );
        }
    }
}
