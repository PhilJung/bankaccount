package com.philippejung.controller;

import com.philippejung.data.models.logical.ClassifierDTO;
import com.philippejung.main.MainApp;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 02/02/15.
 */
public class ClassifierController implements Initializable {

    @FXML
    private TableView<ClassifierDTO> classifierTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classifierTable.setItems(MainApp.getData().getAllClassifiers());
    }
}
