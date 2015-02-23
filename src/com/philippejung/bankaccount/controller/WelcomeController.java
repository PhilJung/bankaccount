package com.philippejung.bankaccount.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public class WelcomeController extends GenericController implements Initializable {
    // The following constants allow to choose which sub-pane goes into which place. For
    // the moment these are constants. Could be configurable.
    private final static String NAME_OF_PANE_0_0 = "/res/fxml/accountsummary.fxml";
    private final static String NAME_OF_PANE_1_0 = "/res/fxml/accountbalance.fxml";
    private final static String NAME_OF_PANE_1_1 = "/res/fxml/accountmovementsbyweek.fxml";

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane pane00 = loadPane(NAME_OF_PANE_0_0);
        gridPane.add(pane00, 0, 0);
        Pane pane10 = loadPane(NAME_OF_PANE_1_0 );
        gridPane.add(pane10, 1, 0);
        Pane pane11 = loadPane(NAME_OF_PANE_1_1 );
        gridPane.add(pane11, 1, 1);

        Pane pane3 = new Pane();
        pane3.setStyle("-fx-background-color: GREEN;");
        gridPane.add(pane3, 0, 1);

    }
}
