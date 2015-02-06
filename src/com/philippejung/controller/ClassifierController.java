package com.philippejung.controller;

import com.philippejung.data.models.logical.CategoryDTO;
import com.philippejung.data.models.logical.ClassifierDTO;
import com.philippejung.data.models.logical.WayOfPaymentDTO;
import com.philippejung.main.MainApp;
import com.philippejung.view.utils.CategoryColumn;
import com.philippejung.view.utils.DetailConditionTestColumn;
import com.philippejung.view.utils.WayOfPaymentColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 02/02/15.
 */
public class ClassifierController implements Initializable {

    @FXML
    private TableColumn<ClassifierDTO, String> detailConditionTestColumn;
    @FXML
    private TableColumn<ClassifierDTO, String> detailConditionValueColumn;
    @FXML
    private TableColumn<ClassifierDTO, String> amountConditionTestColumn;
    @FXML
    private TableColumn<ClassifierDTO, Double> amountConditionValueColumn;
    @FXML
    private TableColumn<ClassifierDTO, CategoryDTO> categoryColumn;
    @FXML
    private TableColumn<ClassifierDTO, WayOfPaymentDTO> wayOfPaymentColumn;
    @FXML
    private TableView<ClassifierDTO> classifierTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classifierTable.setItems(MainApp.getData().getAllClassifiers());
        classifierTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DetailConditionTestColumn.inject(detailConditionTestColumn);
        WayOfPaymentColumn.inject(wayOfPaymentColumn);
        CategoryColumn.inject(categoryColumn);
    }

    public void onNewClassifierRule(ActionEvent actionEvent) {
        ClassifierDTO newDTO = new ClassifierDTO();
        MainApp.getData().getAllClassifiers().add(newDTO);
    }

    public void onDeleteClassierRule(ActionEvent actionEvent) {
        MainApp.getData().getAllClassifiers().removeAll(classifierTable.getSelectionModel().getSelectedItems());
    }
}
