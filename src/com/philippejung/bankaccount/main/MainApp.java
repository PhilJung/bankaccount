package com.philippejung.bankaccount.main;

import com.philippejung.bankaccount.controller.MainFrameController;
import com.philippejung.bankaccount.models.AppData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * =================================================
 *                Bank Account
 * =================================================
 *
 * v0 Created by philippe on 19/01/15.
 */
public class MainApp extends Application {

//    private AppFrame appFrame;
    private AppData appData = null;
    private static MainApp appInstance = null;
    private Stage primaryStage;
    private static MainFrameController mainController;

    public static MainFrameController getMainController() {
        return mainController;
    }

    private static void setMainController(MainFrameController mainController) {
        MainApp.mainController = mainController;
    }

    public MainApp() {
        appInstance = this;
    }

    public static AppData getData() {
        return appInstance.getAppData();
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
        setMainController(rootLoader.getController());
        primaryStage.setScene(new Scene(rootLayout, 1200, 875));
        primaryStage.show();

        // Add the status bar
//        getMainController().addStatusBar(rootLayout);

        // Now load the first tab
        getMainController().selectTabAndCreateItIfRequired("welcome", "/res/fxml/welcome.fxml", "Accueil", false);
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
