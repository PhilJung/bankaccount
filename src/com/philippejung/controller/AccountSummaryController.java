package com.philippejung.controller;

import com.philippejung.data.models.logical.AccountDTO;
import com.philippejung.main.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by philippe on 25/01/15.
 */
public class AccountSummaryController implements Initializable {
    @FXML
    private TableView<AccountDTO> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(MainApp.getData().getAllAccounts());
    }
}
