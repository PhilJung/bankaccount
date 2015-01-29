package com.philippejung.main;

import com.philippejung.controller.MainFrameController;
import com.philippejung.data.AppData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Created by philippe on 19/01/15.
 */
public class MainApp extends Application {

//    private AppFrame appFrame;
    private AppData appData = null;
    private static MainApp appInstance = null;
    private Stage primaryStage;
    private MainFrameController mainController = null;


    public MainApp() {
        appInstance = this;
    }

    public static MainApp getInstance() {
        return appInstance;
    }

    public static AppData getData() {
        return getInstance().getAppData();
    }

    private AppData getAppData() {
        if (appData == null) {
            appData = new AppData();
            appData.init();
        }
        return appData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);
        this.primaryStage = primaryStage;
        primaryStage.setTitle("BankAccount");
        initRootLayout();
    }

    private void initRootLayout() throws java.io.IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/res/fxml/appframe.fxml"));
        VBox rootLayout = rootLoader.load();
        mainController = rootLoader.getController();
        primaryStage.setScene(new Scene(rootLayout, 900, 875));
        primaryStage.show();

        // Now load the first tab
        mainController.selectTabAndCreateItIfRequired("welcome", "/res/fxml/welcome.fxml", "Accueil", false);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        appData.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
