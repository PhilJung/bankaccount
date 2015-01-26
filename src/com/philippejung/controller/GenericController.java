package com.philippejung.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by philippe on 25/01/15.
 */
public class GenericController {

    protected void alerte(Alert.AlertType type, String title, String header, String content) {
        alerte(type, title, header, content, null);
    }
    protected void alerte(Alert.AlertType type, String title, String header, String content, Exception ex) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        if (ex != null) {
            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);
        }
        alert.showAndWait();
    }

    protected Pane loadPane(String templatePath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(templatePath));
        Pane newPane = null;
        try {
            newPane = loader.load();
        } catch (IOException e) {
            alerte(
                    Alert.AlertType.ERROR, "Error", "Resource not found.",
                    "File: " + templatePath + "\nThe application will exit.", e
            );
            Platform.exit();
        }
        return newPane;
    }
}
