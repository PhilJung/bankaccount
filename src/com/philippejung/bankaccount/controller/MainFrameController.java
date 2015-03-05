package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.view.utils.AlertPopup;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

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

    public void closeImportTab() {
        closeTabAndDestroy("import");
    }
}
