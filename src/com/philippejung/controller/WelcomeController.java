package com.philippejung.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 25/01/15.
 */
public class WelcomeController extends GenericController implements Initializable {
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane accountSummaryPane = loadPane("/res/fxml/accountsummary.fxml");
        gridPane.add(accountSummaryPane, 0, 0);
        Pane pane1 = new Pane();
        pane1.setStyle("-fx-background-color: RED;");
        gridPane.add(pane1, 0, 1);
        Pane pane2 = new Pane();
        pane2.setStyle("-fx-background-color: BLUE;");
        gridPane.add(pane2, 1, 1);
        Pane pane3 = new Pane();
        pane3.setStyle("-fx-background-color: GREEN;");
        gridPane.add(pane3, 1, 0);

    }
}
