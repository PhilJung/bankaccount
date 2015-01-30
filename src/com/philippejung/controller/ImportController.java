package com.philippejung.controller;

import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.data.models.logical.WayOfPaymentDTO;
import com.philippejung.main.MainApp;
import com.philippejung.services.FileImporter;
import com.philippejung.services.classifier.TransactionClassifier;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

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
    @FXML
    private TableColumn<TransactionDTO, String> typeColumn;
    @FXML
    private TableColumn<TransactionDTO, WayOfPaymentDTO> wayOfPaymentColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mustBeImportedColumn.setCellFactory(
                (p) -> new CheckBoxTableCell<>()
        );
        final StringConverter<WayOfPaymentDTO> wayOfPaymentStringConverter = new StringConverter<WayOfPaymentDTO>() {
            @Override
            public String toString(WayOfPaymentDTO object) {
                return (object == null) ? "" : object.getName();
            }

            @Override
            public WayOfPaymentDTO fromString(String string) {
                return null;
            }
        };
        wayOfPaymentColumn.setCellValueFactory(
                (param) -> new ReadOnlyObjectWrapper<>(param.getValue().getWayOfPayment())
        );
        wayOfPaymentColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(wayOfPaymentStringConverter, MainApp.getData().getAllWaysOfPayment())
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
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
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
        TransactionClassifier transactionClassifier = new TransactionClassifier();
        transactionClassifier.registerAllClassifiers();
        transactionClassifier.setItems(importTable.getItems());
        transactionClassifier.classify();
    }
}
