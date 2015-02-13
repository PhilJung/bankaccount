package com.philippejung.bankaccount.view.column;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.CategoryDTO;
import com.philippejung.bankaccount.view.utils.ObjectStringConverter;
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
public final class CategoryColumn {

    public static <S> void inject(TableColumn<S, CategoryDTO> tc) {
        tc.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        new ObjectStringConverter<>(), MainApp.getData().getAllCategories()
                )
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );
    }


}

