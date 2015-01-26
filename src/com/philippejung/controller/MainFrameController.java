package com.philippejung.controller;

import com.philippejung.main.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by philippe on 21/01/15.
 */
public class MainFrameController extends GenericController {
    @FXML
    private TabPane tabPane;

    // All already open tabs
    private HashMap<String, Tab> allExistingTabs = new HashMap<String, Tab>();

    public void onMenuFileExit(ActionEvent event) {
        Platform.exit();
    }

    public void onMenuHelpAbout(ActionEvent event) {
        alerte(
                Alert.AlertType.INFORMATION, "About", "About BankAccount",
                "BankAccount v0\n\u00A9 Philippe Jung - 2015"
        );
        Platform.exit();
    }

    public void selectTabAndCreateItIfRequired(String key, String templatePath, String title, Boolean canClose) {
        Tab theTab = allExistingTabs.get(key);
        if (theTab==null) {
            Pane newPane = loadPane(templatePath);
            theTab = new Tab();
            theTab.setText(title);
            theTab.setClosable(canClose);
            theTab.setContent(newPane);
            allExistingTabs.put(key, theTab);
            tabPane.getTabs().add(theTab);
        }
        tabPane.getSelectionModel().select(theTab);
    }

    public void onToolbarBudget(ActionEvent event) {
        selectTabAndCreateItIfRequired("budget", "/res/fxml/budget.fxml", "Budget", true);
    }

    public void onToolbarImport(ActionEvent actionEvent) {
        selectTabAndCreateItIfRequired("import", "/res/fxml/import.fxml", "Importer", true);
    }
}
