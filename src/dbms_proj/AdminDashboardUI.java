package dbms_proj;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardUI extends JFrame {

    private JButton reportButton;
    private JButton viewButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton historyButton;
    private JButton registerChildButton;
    private JButton registerParentButton;
    private JButton locationButton;
    private JButton foundChildButton;
    private JButton matchButton;

    public AdminDashboardUI() {

        setTitle("Missing Child Identification System");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(10, 1, 10, 10));

        reportButton = new JButton("Report Missing Child");
        viewButton = new JButton("View Missing Cases");
        searchButton = new JButton("Search Missing Child");
        updateButton = new JButton("Update Case Status");
        historyButton = new JButton("View Status History");
        registerChildButton = new JButton("Register Child");
        registerParentButton = new JButton("Register Parent");
        locationButton = new JButton("Manage Locations");
        foundChildButton = new JButton("Register Found Child");
        matchButton = new JButton("Match Found Child");

        panel.add(reportButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(updateButton);
        panel.add(historyButton);
        panel.add(registerChildButton);
        panel.add(registerParentButton);
        panel.add(locationButton);
        panel.add(foundChildButton);
        panel.add(matchButton);

        add(panel);

        reportButton.addActionListener(
                e -> new ReportMissingUI().setVisible(true));

        viewButton.addActionListener(
                e -> new ViewMissingCasesUI().setVisible(true));

        searchButton.addActionListener(
                e -> new SearchMissingChildUI().setVisible(true));

        updateButton.addActionListener(
                e -> new UpdateCaseStatusUI().setVisible(true));

        historyButton.addActionListener(
                e -> new StatusHistoryUI().setVisible(true));

        registerChildButton.addActionListener(
                e -> new RegisterChildUI().setVisible(true));

        registerParentButton.addActionListener(
                e -> new RegisterParentUI().setVisible(true));

        locationButton.addActionListener(
                e -> new ManageLocationsUI().setVisible(true));
        
        foundChildButton.addActionListener(
                e -> new RegisterFoundChildUI().setVisible(true));

        matchButton.addActionListener(
                e -> new MatchCaseUI().setVisible(true));
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new AdminDashboardUI().setVisible(true));
    }
}