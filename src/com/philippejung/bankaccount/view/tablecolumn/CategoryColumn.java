package com.philippejung.bankaccount.view.tablecolumn;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.CategoryDTO;
import com.philippejung.bankaccount.models.dto.RootDTO;
import com.philippejung.bankaccount.models.interfaces.CategoryPropertyProvider;
import com.philippejung.bankaccount.utils.utils.ObjectStringConverter;
import com.philippejung.bankaccount.utils.utils.SearchableComboBoxTableCell;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 06/02/15.
 */
public final class CategoryColumn {

    public static <S extends RootDTO & CategoryPropertyProvider> void inject(TableColumn<S, CategoryDTO> tc, Boolean autoSave) {
        tc.setCellFactory(
                param -> new SearchableComboBoxTableCell<>(new ObjectStringConverter<>(), MainApp.getData().getAllCategories())
        );
        tc.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );
//        tc.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<S, CategoryDTO>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<S, CategoryDTO> event) {
//
//            }
//        });
        if (autoSave) {
            tc.setOnEditCommit(
                    (TableColumn.CellEditEvent<S, CategoryDTO> event) -> {
                        S impactedObject = (S) event.getTableView().getItems().get(event.getTablePosition().getRow());
                        CategoryDTO newValue = event.getNewValue();
                        impactedObject.setCategory(newValue);
                        impactedObject.writeToDB();
                    }
            );
        }
    }


}

