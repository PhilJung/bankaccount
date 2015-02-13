package com.philippejung.bankaccount.controller;

import com.philippejung.bankaccount.main.MainApp;
import com.philippejung.bankaccount.models.dto.AccountDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 25/01/15.
 */
public class AccountSummaryController implements Initializable {
    @FXML
    private TableView<AccountDTO> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(MainApp.getData().getAllAccounts());
    }
}
