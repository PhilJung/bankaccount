package com.philippejung.controller;

import com.philippejung.data.models.logical.CategoryDTO;
import com.philippejung.data.models.logical.TransactionDTO;
import com.philippejung.data.models.logical.TypeOfTransaction;
import com.philippejung.data.models.logical.WayOfPaymentDTO;
import com.philippejung.main.MainApp;
import com.philippejung.services.FileImporter;
import com.philippejung.services.classifier.TransactionClassifier;
import com.philippejung.view.utils.CategoryColumn;
import com.philippejung.view.utils.ObjectStringConverter;
import com.philippejung.view.utils.SearchableComboBoxTableCell;
import com.philippejung.view.utils.WayOfPaymentColumn;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private TableColumn<TransactionDTO, TypeOfTransaction> typeColumn;
    @FXML
    private TableColumn<TransactionDTO, CategoryDTO> categoryColumn;
    @FXML
    private TableColumn<TransactionDTO, WayOfPaymentDTO> wayOfPaymentColumn;
    @FXML
    private TableColumn<TransactionDTO, String> commentColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<TypeOfTransaction> typeOfTransactions = new ArrayList<>();
        typeOfTransactions.add(TypeOfTransaction.EXPENSE);
        typeOfTransactions.add(TypeOfTransaction.INCOME);
        typeOfTransactions.add(TypeOfTransaction.TRANSFER_FROM);
        typeOfTransactions.add(TypeOfTransaction.TRANSFER_TO);
        typeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(typeOfTransactions)));
        // Must be imported : checkbox
        mustBeImportedColumn.setCellFactory(
                (p) -> new CheckBoxTableCell<>()
        );
        WayOfPaymentColumn.inject(wayOfPaymentColumn);
        CategoryColumn.inject(categoryColumn);
        commentColumn.setCellFactory(
                TextFieldTableCell.forTableColumn(new DefaultStringConverter())
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
        transactionClassifier.setItems(importTable.getItems());
        transactionClassifier.setClassifiers(MainApp.getData().getAllClassifiers());
        transactionClassifier.classify();
    }
}
