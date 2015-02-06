package com.philippejung.view.utils;

import com.philippejung.data.models.logical.CategoryDTO;
import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.main.MainApp;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by philippe on 06/02/15.
 */
public final class CategoryColumn {

    public static <S> void inject(TableColumn<S, CategoryDTO> tc) {
        tc.setCellFactory(
                tableCol -> { return new SearchableComboBoxTableCell<>(new ObjectStringConverter<CategoryDTO>(), MainApp.getData().getAllCategories()); }
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<S, CategoryDTO>("category")
        );
    }


}

