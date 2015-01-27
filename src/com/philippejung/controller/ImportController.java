package com.philippejung.controller;

import com.philippejung.data.models.dao.MovementDAO;
import com.philippejung.main.MainApp;
import com.philippejung.services.FileImporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by philippe on 26/01/15.
 */
public class ImportController implements Initializable {
    @FXML
    private TextField filePath;
    @FXML
    private Button fileBrowse;
    @FXML
    private TableView importTable;
    @FXML
    private ComboBox accountList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onButtonFileBrowseClicked(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez le fichier à importer...");
        fileChooser.setInitialDirectory(
                new File(MainApp.getData().getPreferences().getImportDefaultPath())
        );
        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Images", "*.*"),
//                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(fileBrowse.getScene().getWindow());
        if (file != null) {
            try {
                filePath.setText(file.getCanonicalPath());
            } catch (IOException e) {
                filePath.setText("Erreur");
            }
        }
    }

    public void onButtonReadFileClicked(ActionEvent actionEvent) {
        //String targetAccount = accountList.getSelectionModel().getSelectedItem();
        FileImporter fileImporter = new FileImporter("lbp");
        fileImporter.importFile(filePath.getText());
        importTable.setItems(FXCollections.observableArrayList(fileImporter.getAllImportedMovements()));
    }

    public void onButtonImportDataClicked(ActionEvent actionEvent) {
    }
}
