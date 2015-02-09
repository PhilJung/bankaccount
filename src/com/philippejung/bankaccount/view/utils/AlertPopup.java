package com.philippejung.bankaccount.view.utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by philippe on 27/01/15.
 */
public final class AlertPopup {
    /**
     * Show a standard message dialog
     * @param type  type of alert (one of AlertType.XXXX)
     * @param title title of the dialog
     * @param header top panel of the dialog
     * @param content detail of the dialog
     */
    public static void alert(javafx.scene.control.Alert.AlertType type, String title, String header, String content) {
        alert(type, title, header, content, null);
    }

    /**
     * Show a standard message dialog with possible exception details
     * @param type  type of alert (one of AlertType.XXXX)
     * @param title title of the dialog
     * @param header top panel of the dialog
     * @param content detail of the dialog
     * @param ex exception to include in the dialog
     */
    public static void alert(javafx.scene.control.Alert.AlertType type, String title, String header, String content, Exception ex) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
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
}
