package com.philippejung.controller;

import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.main.MainApp;
import com.philippejung.services.FileImporter;
import com.philippejung.services.MovementClassifier;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 26/01/15.
 */
public class ImportController implements Initializable {
    @FXML
    private TextField filePath;
    @FXML
    private Button fileBrowse;
    @FXML
    private TableView<TransactionDTO> importTable;
    @FXML
    private ComboBox accountList;
    @FXML
    private TableColumn<TransactionDTO, Boolean> mustBeImportedColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mustBeImportedColumn.setCellFactory(
                (p) -> { return new CheckBoxTableCell<TransactionDTO, Boolean>(); }
        );
    }

    public void onButtonFileBrowseClicked(ActionEvent actionEvent) {
        chooseOneFile();
        onButtonReadFileClicked(null);
        onAnalyzeDataButtonClicked(null);
    }

    private void chooseOneFile() {
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

    public void onAnalyzeDataButtonClicked(ActionEvent actionEvent) {
        MovementClassifier movementClassifier = new MovementClassifier();
        movementClassifier.registerAllClassifiers();
        movementClassifier.setItems(importTable.getItems());
        movementClassifier.classify();

    }
}
