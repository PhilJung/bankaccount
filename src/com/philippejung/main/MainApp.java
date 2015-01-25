package com.philippejung.main;

import com.philippejung.controller.MainFrameController;
import com.philippejung.data.AppData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Created by philippe on 19/01/15.
 */
public class MainApp extends Application {

//    private AppFrame appFrame;
    private AppData appData;
    private static MainApp appInstance = null;
    private Stage primaryStage;
    private MainFrameController mainController = null;


    public MainApp() {
        appInstance = this;
//        appFrame = new AppFrame();
    }

    public static MainApp getInstance() {
        return appInstance;
    }

//    public AppFrame getAppFrame() {
//        return appFrame;
//    }

    public AppData getData() {
        return appData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("BankAccount");
        initRootLayout();
    }

    private void initRootLayout() throws java.io.IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/res/fxml/appframe.fxml"));
        VBox rootLayout = rootLoader.load();
        mainController = rootLoader.getController();
        primaryStage.setScene(new Scene(rootLayout, 300, 275));
        primaryStage.show();

        // Now load the first tab
        mainController.selectTabAndCreateItIfRequired("welcome", "/res/fxml/welcome.fxml", "Welcome", false);
    }

    @Override
    public void init() throws Exception {
        super.init();
        try {
            appData = new AppData();
        } catch (ClassNotFoundException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something prevent the program to continue");
            alert.setContentText("Impossible to load JDBC - Sqlite driver. Program will exit.");
            alert.showAndWait();
            Platform.exit();
        }

    }

    @Override
    public void stop() throws Exception {
        appData.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    public void run() {
//        getAppFrame().prepareIHM();
//    }

}
