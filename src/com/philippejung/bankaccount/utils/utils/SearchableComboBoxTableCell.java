package com.philippejung.bankaccount.utils.utils;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class SearchableComboBoxTableCell<S,T> extends ComboBoxTableCell<S,T> {

    private ComboBox<T> comboBox;
    private FilteredList<T> filteredList;
    private String filteringText = "";

    public SearchableComboBoxTableCell(final StringConverter<T> converter, final ObservableList<T> items) {
        super(converter, items);
    }

    @Override
    public void startEdit() {
        if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
            return;
        }

        if (comboBox == null) {
            comboBox = new ComboBox<T>(getItems());
            comboBox.setEditable(true);
            comboBox.setConverter(getConverter());
            comboBox.setMaxWidth(Double.MAX_VALUE);
            comboBox.getSelectionModel().selectedItemProperty().addListener(
                    (ov, oldValue, newValue) -> {
                        System.out.println("Commit edit isEditing?");
                        if (isEditing()) {
                            System.out.println("Commit edit");
                            commitEdit(newValue);
                        }
                    });
            FxUtil.autoCompleteComboBox(comboBox, FxUtil.AutoCompleteMode.STARTS_WITH);
/*
            comboBox.getEditor().textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    final TextField editor = comboBox.getEditor();

                    final T selected = comboBox.getSelectionModel().getSelectedItem();
                    comboBox.hide();

                    if (selected == null || !getConverter().toString(selected).equals(editor.getText())) {
                        filterItems(newValue, comboBox, getItems());
                        comboBox.show();
                        if (comboBox.getItems().size() == 1) {
                            final String onlyOption = getConverter().toString(comboBox.getItems().get(0));
                            final String current = editor.getText();
                            if (onlyOption.length() > current.length()) {
                                editor.setText(onlyOption);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        editor.selectRange(current.length(), onlyOption.length());
                                    }
                                });
                            }
                        }
                    }
                }
            );
*/
//            comboBox.setOnAction(event -> {
//                        System.out.println("Combo -> onAction");
//                    }
//            );
//            comboBox.getEditor().setOnAction(
//                event -> {
//                    System.out.println("editor -> onAction");
//                }
//            );

        }

        T selectedItem = getItem();
        comboBox.getSelectionModel().select(selectedItem);

        super.startEdit();
        setText(null);
        setGraphic(comboBox);
        if (selectedItem == null) {
            comboBox.show();
            comboBox.getEditor().requestFocus();
        }
    }

//    public void handleEvent(KeyEvent keyEvent) {
//        final TextField editor = comboBox.getEditor();
//        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
//            String text = editor.getText();
//            if (!text.isEmpty()) {
//                text = text.substring(0, text.length() - 1);
//                editor.setText(text);
//                filterItems(text.toLowerCase());
//            }
//        } else if (keyEvent.getCode() == KeyCode.DELETE) {
//            editor.setText("");
//            filterItems("");
//        } else if (keyEvent.getCode() == KeyCode.ENTER) {
//            commitEdit(comboBox.getSelectionModel().getSelectedItem());
//            return;
//        } else if (keyEvent.getCode().isDigitKey() || keyEvent.getCode().isLetterKey()) {
//            String text = comboBox.getEditor().getText();
//            text += keyEvent.getText();
//            comboBox.getEditor().setText(text);
//            filterItems(text);
//        } else if (keyEvent.getCode() == KeyCode.DOWN) {
//            int index = comboBox.getSelectionModel().getSelectedIndex();
//            if (comboBox.getItems().size() > index + 1) {
//                comboBox.getSelectionModel().select(index + 1);
//                comboBox.show();
//            }
//        } else if (keyEvent.getCode() == KeyCode.UP) {
//            int index = comboBox.getSelectionModel().getSelectedIndex();
//            if (index > 0) {
//                comboBox.getSelectionModel().select(index - 1);
//                comboBox.show();
//            }
//        }
//    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println("Update item");
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (comboBox != null) {
                    comboBox.getSelectionModel().select(getItem());
                }
                setText(null);
                setGraphic(comboBox);
            } else {
                setText(getConverter().toString(getItem()));
                setGraphic(null);
            }
        }
    }

//    private void filterItems(String filter) {
//        List<T> filteredItems = new ArrayList<>();
//        for (T item : getItems()) {
//            if (item.toString().toLowerCase().startsWith(filter)) {
//                filteredItems.add(item);
//            }
//        }
//        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
//        comboBox.show();
//    }
}




