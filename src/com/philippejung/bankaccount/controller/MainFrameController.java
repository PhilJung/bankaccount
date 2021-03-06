package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.view.popup.AlertPopup;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;

import java.io.File;
import java.util.HashMap;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 21/01/15.
 */
public class MainFrameController extends GenericController {
    @FXML
    private TabPane tabPane;

    @FXML
    private StatusBar statusBar;

    // All already open tabs
    private final HashMap<String, Tab> allExistingTabs = new HashMap<>();

    @SuppressWarnings("UnusedParameters")
    public void onMenuFileExit(ActionEvent event) {
        Platform.exit();
    }

    @SuppressWarnings("UnusedParameters")
    public void onMenuHelpAbout(ActionEvent event) {
        AlertPopup.alert(
                Alert.AlertType.INFORMATION, "About", "About BankAccount",
                "BankAccount v0\n\u00A9 Philippe Jung - 2015"
        );
        Platform.exit();
    }

    public void selectTabAndCreateItIfRequired(String tabKey, String templatePath, String title, Boolean canClose) {
        Tab theTab = allExistingTabs.get(tabKey);
        if (theTab==null) {
            Pane newPane = loadPane(templatePath);
            theTab = new Tab(title);
            theTab.setClosable(canClose);
            theTab.setContent(newPane);
            theTab.setOnClosed(event -> allExistingTabs.remove(tabKey));
            allExistingTabs.put(tabKey, theTab);
            tabPane.getTabs().add(theTab);
        }
        tabPane.getSelectionModel().select(theTab);
    }

    @SuppressWarnings("SameParameterValue")
    private void closeTabAndDestroy(String tabKey) {
        Tab theTab = allExistingTabs.get(tabKey);
        if (theTab!=null) {
            tabPane.getTabs().removeAll(theTab);
            allExistingTabs.remove(tabKey);
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void onToolbarBudget(ActionEvent event) {
        selectTabAndCreateItIfRequired("budget", "/res/fxml/budget.fxml", "Budget", true);
    }

    @SuppressWarnings("UnusedParameters")
    public void onToolbarImport(ActionEvent actionEvent) {
        selectTabAndCreateItIfRequired("import", "/res/fxml/import.fxml", "Importer", true);
    }

    @SuppressWarnings("UnusedParameters")
    public void onToolbarWelcome(ActionEvent actionEvent) {
        selectTabAndCreateItIfRequired("welcome", "/res/fxml/welcome.fxml", "Accueil", false);
    }

    @SuppressWarnings("UnusedParameters")
    public void onToolbarClassifier(ActionEvent actionEvent) {
        selectTabAndCreateItIfRequired("classifier", "/res/fxml/classifier.fxml", "Classement automatique", true);
    }

    @SuppressWarnings("UnusedParameters")
    public void onToolbarCategory(ActionEvent actionEvent) {
        selectTabAndCreateItIfRequired("category", "/res/fxml/category.fxml", "Catégories", true);
    }

    public void closeImportTab() {
        closeTabAndDestroy("import");
    }

    public void setProgress(double progress) {
        if (statusBar != null)
            statusBar.setProgress(progress);
    }

    @SuppressWarnings("UnusedParameters")
    public void onMenuFileBackup(ActionEvent actionEvent) {
        // Open backup folder
        String backupPath = MainApp.getData().getPreferences().getBackupPath();
        File dir = new File(backupPath);
        if (!dir.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
        MainApp.getData().backup(backupPath);
    }

    @SuppressWarnings("UnusedParameters")
    public void onMenuFileRestore(ActionEvent actionEvent) {
        // Open backup folder
        String backupPath = MainApp.getData().getPreferences().getBackupPath();
        File dir = new File(backupPath);
        if (!dir.isDirectory()) {
            // Folder does not exists so nothing to restore
        }

        MainApp.getData().restore(backupPath);
    }
}
