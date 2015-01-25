package com.philippejung.view.panels;

import com.philippejung.view.widgets.AllAccountsSummaryWidget;

import javax.swing.*;

/**
 * Created by philippe on 19/01/15.
 */
public class WelcomePanel {
    private JPanel rootPanel;
    private JPanel allAccountsSummary;
    private JPanel monthBudget;
    private JPanel cashTrends;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        allAccountsSummary = new AllAccountsSummaryWidget().getRootPanel();
    }
}
