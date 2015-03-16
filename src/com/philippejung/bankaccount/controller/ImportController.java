package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.*;
import com.philippejung.bankaccount.services.file.FileImporter;
import com.philippejung.bankaccount.services.classifier.TransactionClassifier;
import com.philippejung.bankaccount.view.column.BooleanColumn;
import com.philippejung.bankaccount.view.column.CategoryColumn;
import com.philippejung.bankaccount.view.column.TypeColumn;
import com.philippejung.bankaccount.view.column.WayOfPaymentColumn;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 26/01/15.
 *
 * Import controller: controller for the screen in charge of importing one file with transactions
 * into an account.
 */
public class ImportController implements Initializable {
    @FXML
    private TextField filePath;
    @FXML
    private Button fileBrowse;
    @FXML
    private Button importData;
    @FXML
    private TableView<TransactionDTO> importTable;
    @FXML
    private ComboBox<AccountDTO> accountList;
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
        accountList.setItems(MainApp.getData().getAllAccounts());
        TypeColumn.inject(typeColumn);
        // Must be imported : checkbox
        BooleanColumn.inject(mustBeImportedColumn, "mustBeImported");
        WayOfPaymentColumn.inject(wayOfPaymentColumn);
        CategoryColumn.inject(categoryColumn);
        commentColumn.setCellFactory(
                TextFieldTableCell.forTableColumn(new DefaultStringConverter())
        );
        fileBrowse.setDisable(true);
        filePath.setDisable(true);
        importData.setDisable(true);
    }

    @SuppressWarnings("UnusedParameters")
    public void onButtonFileBrowseClicked(ActionEvent actionEvent) {
        if (chooseOneFile()) {
            onButtonReadFileClicked(null);
            onAnalyzeDataButtonClicked(null);
        }
    }

    /**
     * Open file chooser dialog.
     * @return true if a file has really been chosen.
     */
    private boolean chooseOneFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez le fichier à importer...");
        fileChooser.setInitialDirectory(
                new File(MainApp.getData().getPreferences().getImportDefaultPath())
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("QIF", "*.qif"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
        File file = fileChooser.showOpenDialog(fileBrowse.getScene().getWindow());
        if (file != null) {
            try {
                filePath.setText(file.getCanonicalPath());
                return true;
            } catch (IOException e) {
                filePath.setText("Erreur");
            }
        }
        return false;
    }

    @SuppressWarnings({"UnusedParameters", "SameParameterValue"})
    public void onButtonReadFileClicked(ActionEvent actionEvent) {
        AccountDTO intoAccount = accountList.getSelectionModel().getSelectedItem();
        FileImporter fileImporter = new FileImporter(intoAccount.getImporterFormat());
        FileInputStream file = null;
        try {
            file = new FileInputStream(filePath.getText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (file!=null) {
            fileImporter.importFile(file);
            importTable.setItems(FXCollections.observableArrayList(fileImporter.getAllImportedMovements()));
            try {
                file.close();
            } catch (IOException ignored) {}
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void onButtonImportDataClicked(ActionEvent actionEvent) {
        AccountDTO intoAccount = accountList.getSelectionModel().getSelectedItem();
        MainApp.getData().getDbAccess().beginTransaction();
        for (TransactionDTO dto : importTable.getItems()) {
            dto.setAccount(intoAccount);
            dto.writeToDB();
            intoAccount.addTransaction(dto);
        }
        MainApp.getData().getDbAccess().commitTransaction();
        MainApp.getMainController().closeImportTab();
    }

    @SuppressWarnings({"SameParameterValue", "UnusedParameters"})
    public void onAnalyzeDataButtonClicked(ActionEvent actionEvent) {
        TransactionClassifier transactionClassifier = new TransactionClassifier();
        transactionClassifier.setItems(importTable.getItems());
        transactionClassifier.setClassifiers(MainApp.getData().getAllClassifiers());
        transactionClassifier.classify();
    }

    @SuppressWarnings("UnusedParameters")
    public void onAccountListAction(ActionEvent actionEvent) {
        Boolean disabled = accountList.getSelectionModel().isEmpty();
        fileBrowse.setDisable(disabled);
        filePath.setDisable(disabled);
        importData.setDisable(disabled);
    }
}

