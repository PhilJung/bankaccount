package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.view.utils.AlertPopup;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 *
 * Generic controller: parent of all controllers. Implements some shared behaviours.
 */
public class GenericController {

    protected Pane loadPane(String templatePath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(templatePath));
        Pane newPane = null;
        try {
            newPane = loader.load();
        } catch (IOException e) {
            AlertPopup.alert(
                    Alert.AlertType.ERROR, "Error", "Resource not found.",
                    "File: " + templatePath + "\nThe application will exit.", e
            );
            Platform.exit();
        }
        return newPane;
    }
}
