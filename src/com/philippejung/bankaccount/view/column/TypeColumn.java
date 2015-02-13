package com.philippejung.bankaccount.view.column;

import com.philippejung.bankaccount.models.dto.TypeOfTransaction;
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

    public static <S> void inject(TableColumn<S, TypeOfTransaction> tc) {

        ArrayList<TypeOfTransaction> typeOfTransactions = new ArrayList<>();
        typeOfTransactions.add(TypeOfTransaction.EXPENSE);
        typeOfTransactions.add(TypeOfTransaction.INCOME);
        typeOfTransactions.add(TypeOfTransaction.TRANSFER_FROM);
        typeOfTransactions.add(TypeOfTransaction.TRANSFER_TO);
        tc.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(typeOfTransactions)));

        tc.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
    }
}
