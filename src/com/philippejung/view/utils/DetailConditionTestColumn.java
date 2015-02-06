package com.philippejung.view.utils;

import com.philippejung.data.models.logical.ClassifierDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;

/**
 * Created by philippe on 06/02/15.
 */
public class DetailConditionTestColumn {

    private static String[] possibleValues = {"contains", "endsWith", "equals", "matches", "startsWith"};

    public static <T> void inject(TableColumn<T, String> detailConditionTestColumn) {
        ComboBoxTableCell.forTableColumn(possibleValues);
    }
}
